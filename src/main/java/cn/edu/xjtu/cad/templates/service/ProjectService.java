package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.annotation.UserRoleFilter;
import cn.edu.xjtu.cad.templates.aop.MyException;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.feign.UserRemote;
import cn.edu.xjtu.cad.templates.model.CAIUser;
import cn.edu.xjtu.cad.templates.model.Result;
import cn.edu.xjtu.cad.templates.model.ResultCode;
import cn.edu.xjtu.cad.templates.model.project.*;
import cn.edu.xjtu.cad.templates.model.project.node.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;
import java.util.concurrent.Delayed;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("projectService")
public class ProjectService {
    static Map<String, double[]> indexMaxAndMin = null;


    @Autowired
    ReferMapper referMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    StepMapper stepMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    EdgeMapper edgeMapper;

    @Autowired
    NodeResultMapper nodeResultMapper;

    @Autowired
    ProjectRoleMapper projectRoleMapper;

    @Autowired
    NodeRoleMapper nodeRoleMapper;

    @Autowired
    UserRemote userRemote;

    public List<Project> getOwnedProjectList(User user) {
        return projectMapper.getProjectListByUserAndRole(user.getUserID(), ProjectRoleType.CREATOR);
    }

    public List<Project> getMyProjectList(long userID) {
        return projectMapper.getAllProjectListByUserID(userID);
    }


    public List<Project> getOpenProjectList(long userID) {
        List<Project> publicProjectList = projectMapper.getOpenProject(userID);
        for (Project project : publicProjectList) {
            project.setProjectRole(projectRoleMapper.getProjectRoleType(project.getProjectID(), userID));
        }
        return publicProjectList;
    }

    public long newProject(Project project, long userID, long referID,String problemID) throws MyException {
        if(problemID!=null&&problemID.trim().length()>0){
            Project old = projectMapper.getProjectByProblemID(problemID);
            if(old!=null){
                projectMapper.unbindProblemID(old.getProjectID());
            }
            project.setProblemID(problemID);
        }
        long projectID = 0;
        project.setCreatorID(userID);
        project.setInvitationCode(generateInvitationCode());
        if (referID > 0) {
            projectID = createProject(project, referID);
        } else {
            projectID = createProject(project);
        }
        if (projectID == 0) {
            throw new MyException(ResultCode.DATA_ALREADY_EXISTED);
        }
        return projectID;
    }

    public long createProject(Project project, long referID) {
        //获取柔性模板信息
        Refer refer = referMapper.getReferByID(referID);
        List<Step> stepList = JSONArray.parseArray(refer.getSteps(), Step.class);
        //解析模板中的阶段
        project.setStepMap(stepList.stream().collect(Collectors.toMap(Step::getStepIndex, Function.identity())));
        //解析模板中的节点
        project.setNodeMap(JSONArray.parseArray(refer.getNodes(), Node.class).stream().collect(Collectors.toMap(Node::getStepIndex, Function.identity())));
        //解析模板的中的边
        project.setEdges(JSONArray.parseArray(refer.getEdges(), Edge.class));
        return addProject(project);
    }

    public long createProject(Project project) {
        Step step = new Step("新阶段");
        project.setStepMap(new HashMap<String, Step>() {{
            put(step.getStepIndex(), step);
        }});
        Node node = new Node(step.getStepIndex(), "新的工作节点");
        project.setNodeMap(new HashMap<String, Node>() {{
            put(node.getNodeIndex(), node);
        }});
        return addProject(project);
    }

    /**
     * 2019年6月6日16:02:52 新添加了计算项目甘特图的功能，该功能没有经过验证。
     *
     * @param projectID
     * @return
     */
    public Project getProjectByID(long projectID) {
        //查询项目的基础信息
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null)
            return null;
        //获取项目创建者
        project.setCreatorID(projectRoleMapper.getUserIDListInProjectByProjectRoleType(projectID, ProjectRoleType.CREATOR).get(0));
        //获取项目的阶段
        stepMapper.getStepsOfProject(projectID).forEach(step -> {
            project.getStepMap().put(step.getStepIndex(), step);
        });
        //组织项目的节点和节点之间的关系
        Map<String, Node> nodeMap = nodeMapper.getNodesOfProject(projectID).stream().collect(Collectors.toMap(Node::getNodeIndex, Function.identity()));
        for (Edge edge : edgeMapper.getEdgesOfProject(projectID)) {
            String nodeI = edge.getNodeI();
            String nodeJ = edge.getNodeJ();
            //如果边的两个点有失效的，那么删除该边
            if (!nodeMap.containsKey(edge.getNodeI())) {
                edgeMapper.deleteEdgeOfNode(projectID, edge.getNodeI());
                break;
            }
            if (!nodeMap.containsKey(edge.getNodeJ())) {
                edgeMapper.deleteEdgeOfNode(projectID, edge.getNodeJ());
                break;
            }

            if (nodeMap.get(nodeI).getNextNodeIndexList() == null) {
                nodeMap.get(nodeI).setNextNodeIndexList(new ArrayList<>());
            }
            if (nodeMap.get(nodeJ).getNextNodeIndexList() == null) {
                nodeMap.get(nodeJ).setPreNodeIndexList(new ArrayList<>());
            }
            nodeMap.get(nodeI).getNextNodeIndexList().add(nodeJ);
            nodeMap.get(nodeJ).getPreNodeIndexList().add(nodeI);
        }
        //首先项目监控是否开始
        if (project.getStartTime() != null) {
            List<Node> startNodeList = new ArrayList<>();//开始节点数组
            List<Node> endNodeList = new ArrayList<>();//尾节点数组
            //寻找开始节点
            for (Node value : nodeMap.values()) {
                if (value.getPreNodeIndexList().size() == 0) {
                    startNodeList.add(value);
                }
                if (value.getNextNodeIndexList().size() == 0) {
                    endNodeList.add(value);
                }
            }
            for (Node node : startNodeList) {
                //将开始节点的计划开始时间都设置为项目监控开始的时间
                node.setStartTime(project.getStartTime());
                setPlanTimeForward(nodeMap, project.getStartTime(), node);
            }
            for (Node node : endNodeList) {
                setPlanTimeBackward(nodeMap, node.getPlanEndTime(), node);
            }
            for (Node node : startNodeList) {
                setNodeTime(nodeMap, project.getStartTime(), node);
            }
        }

        project.setNodeMap(nodeMap);
        //获取当前项目的用户
        List<ProjectRole> members = projectRoleMapper.getRoleOfProject(projectID);
        List<Long> userIDs = members.stream().map(ProjectRole::getUserID).collect(Collectors.toList());
        Map<Long, CAIUser> map = userRemote.listIn(userIDs)
                .stream().collect(Collectors.toMap(u->u.getId(),Function.identity()));

        members.forEach(projectRole -> {
            String name = map.get(projectRole.getUserID()).getNickName();
            projectRole.setNickName(name);
        });
        project.setMembers(members);
        return project;
    }



    /**
     * 从前至后计算计划时间
     *
     * @param nodeMap
     * @param planStartTime
     * @param node
     */
    private void setPlanTimeForward(Map<String, Node> nodeMap, Date planStartTime, Node node) {
        //设置开始时间
        if (node.getPlanStartTime() != null) {
            //如果开始时间不为空，需要判断开始时间的先后顺序
            if (node.getPlanStartTime().getTime() >= planStartTime.getTime()) {
                //不作处理
                return;
            }
        }
        node.setPlanStartTime(planStartTime);
        Calendar planEndTimeCal = Calendar.getInstance();
        planEndTimeCal.setTime(planStartTime);
        planEndTimeCal.add(Calendar.DAY_OF_YEAR, node.getWorkTime());
        //设置结束时间
        node.setPlanEndTime(planEndTimeCal.getTime());
        //处理所有的后续节点
        for (String nodeIndex : node.getNextNodeIndexList()) {
            setPlanTimeForward(nodeMap, node.getPlanEndTime(), nodeMap.get(nodeIndex));
        }
    }

    /**
     * 从后到前计算计划时间，主要目的是调整计划时间的空余
     *
     * @param nodeMap
     * @param planEndTime
     * @param node
     */
    private void setPlanTimeBackward(Map<String, Node> nodeMap, Date planEndTime, Node node) {
        //重新计算计划结束时间
        if (node.getPlanEndTime().before(planEndTime)) {
            //如果原来的计划结束时间小于原来的时间，那么重新调整
            node.setPlanEndTime(planEndTime);
        }
        for (String nodeIndex : node.getNextNodeIndexList()) {
            setPlanTimeBackward(nodeMap, node.getPlanStartTime(), nodeMap.get(nodeIndex));
        }
    }

    private void setNodeTime(Map<String, Node> nodeMap, Date startTime, Node node) {
        //设置开始时间
        if (node.getStartTime() != null) {
            //如果开始时间不为空，需要判断开始时间的先后顺序
            if (node.getStartTime().after(startTime)) {
                //如果当前节点的实际开始时间已经存在并且在前序节点的实际结束时间之后，那么不作处理
                return;
            }
        }
        //设置当前节点的实际开始时间为前序节点的实际结束时间
        node.setStartTime(startTime);
        //计算到当前节点的一个延误情况
        Calendar endCalendar = Calendar.getInstance();
        if (node.getEndTime() != null) {
            //如果当前节点设置了结束时间，那么对比的是以实际结束时间与预期时间的对比，否则为当前系统时间与预期时间的对比
            endCalendar.setTime(node.getEndTime());
        }
        Calendar planEndCalendar = Calendar.getInstance();
        planEndCalendar.setTime(node.getPlanEndTime());
        //计算日期差
        int dayDelta = planEndCalendar.get(Calendar.DAY_OF_YEAR) - endCalendar.get(Calendar.DAY_OF_YEAR);
        if (dayDelta < node.getWorkTime() / 6) {
            //如果小于1/3，这里还考虑了日期差是负的，也就是已经延期了的
            node.setDelay(DateDelay.D);
        } else if (dayDelta < node.getWorkTime() * 1 / 3) {
            //如果小于2/3
            node.setDelay(DateDelay.W);
        } else {
            node.setDelay(DateDelay.S);
        }
        if (node.getEndTime() == null) {
            //如果当前节点没有结束，那么后续节点无需计算。
            return;
        }
        //将当前节点的实际结束时间作为后续节点的实际开始时间
        for (String nodeIndex : node.getNextNodeIndexList()) {
            setNodeTime(nodeMap, node.getEndTime(), nodeMap.get(nodeIndex));
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public long addProject(Project project) {
        projectMapper.addProject(project);
        long projectID = project.getProjectID();
        //新建项目权限
        ProjectRole projectRole = new ProjectRole(projectID, project.getCreatorID(), ProjectRoleType.CREATOR);
        projectRoleMapper.addProjectRole(projectRole);

        //向数据库中添加阶段
        project.getStepMap().values().forEach(step -> step.setProjectID(projectID));
        stepMapper.addAllSteps(project.getStepMap().values().stream().collect(Collectors.toList()));

        //向数据库中添加节点
        project.getNodeMap().values().forEach(node -> node.setProjectID(projectID));
        nodeMapper.addAllNodes(project.getNodeMap().values().stream().collect(Collectors.toList()));

        //构建节点-结果
        List<NodeResult> resultList = project.getNodeMap().values().stream().map(node -> new NodeResult(projectID, node.getNodeIndex(), project.getCreatorID())).collect(Collectors.toList());
        //向数据库中添加节点结果
        nodeResultMapper.addAllNodeResultList(resultList);

        //构建节点对应权限
        List<NodeRole> nodeRoleList = project.getNodeMap().values().stream().map(node -> new NodeRole(projectID, node.getNodeIndex(), project.getCreatorID(), NodeRoleType.MANAGER)).collect(Collectors.toList());
        nodeRoleMapper.addAllNodeRole(nodeRoleList);

        //向数据库中添加边
        project.getEdges().forEach(edge -> edge.setProjectID(projectID));
        if (project.getEdges() != null && project.getEdges().size() != 0) {
            edgeMapper.addAllEdges(project.getEdges());
        }
        return projectID;
    }


    /**
     * 更新项目内的用户权限
     *
     * @param user           当前用户
     * @param projectID      项目ID
     * @param userID         待修改的用户名
     * @param newProjectRole 新权限
     * @return
     */
    @UserRoleFilter(allowedProjectRoleTypes = {ProjectRoleType.CREATOR, ProjectRoleType.SUPER_MANAGER})
    public void updateRoleOfMemberInProject(User user, long projectID, long userID, ProjectRoleType newProjectRole) throws MyException {
        switch (user.getProjectRoleType()) {
            case SUPER_MANAGER:
                if (newProjectRole != ProjectRoleType.CREATOR && newProjectRole != ProjectRoleType.SUPER_MANAGER) {
                    break;
                }
            case APPLY:
            case MEMBER:
                throw new MyException(ResultCode.PERMISSION_NO_ACCESS);
        }
        projectRoleMapper.updateProjectRole(projectID, userID, newProjectRole);
    }

    public List<Project> getProjectListByProjectIDList(List<Long> projectIDList) {
        return projectMapper.getProjectListByProjectIDList(projectIDList);
    }


    public Project getProjectByCode(String code) {
        return projectMapper.getProjectByCode(code);
    }


    public Result getMyProjectListResult(User user) {
        return Result.success(getMyProjectList(user.getUserID()));
    }

    public Result getOpenProjectListResult(long userID) {
        return Result.success(getOpenProjectList(userID));
    }


    public Result getProjectByCodeResult(String code) {
        return Result.success(getProjectByCode(code));
    }

    public Result getProjectByIDResult(User user, long projectID) {
        Project project = getProjectByID(projectID);
        return Result.success(project);
    }


    public void updateProjectInfo(@CurUser User user, long projectID, String projectName, String projectDesc, String projectTags) throws MyException {
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        }
        boolean flag = false;
        if (projectName != null) {
            flag = true;
            project.setProjectName(projectName);
        }
        if (projectDesc != null) {
            flag = true;
            project.setProjectDesc(projectDesc);
        }
        if (projectTags != null) {
            flag = true;
            project.setProjectTags(projectTags);
        }
        projectMapper.updateProjectInfo(project);
        if (!flag) {
            throw new MyException(ResultCode.PARAM_NOT_COMPLETE);
        }
    }


    public void deleteProject(@CurUser User user, long projectID) {
        projectMapper.deleteProject(projectID);
    }

    /**
     * 获取项目内的成员
     *
     * @param user
     * @param projectID
     * @return
     */
    public List<ProjectRole> getUserListInProject(User user, long projectID) {
        List<ProjectRole> projectRoleList = projectRoleMapper.getRoleOfProject(projectID);
        for (int i = 0; i < projectRoleList.size(); i++) {
            if (projectRoleList.get(i).getUserID() == user.getUserID()) {
                projectRoleList.remove(i);
                break;
            }
        }
        //对结果根据在项目内的权限进行排序
        projectRoleList.sort(Comparator.comparing(ProjectRole::getProjectRole));
        List<Long> userIDList = projectRoleList.stream().map(ProjectRole::getUserID).collect(Collectors.toList());
        Map<Long,CAIUser> map = userRemote.listIn(userIDList)
                .stream().collect(Collectors.toMap(u->u.getId(),Function.identity()));
        projectRoleList.forEach(projectRole -> {
            String name = map.get(projectRole.getUserID()).getNickName();
            projectRole.setNickName(name);
        });

        return projectRoleList;
    }

    /**
     * 添加用户到项目内
     *
     * @param curUserProjectRoleType 操作用户
     * @param projectID              项目ID
     * @param userID                 被操作用户ID
     * @param projectRoleType        新用户的Type
     * @return
     */
    public void appendUser2Project(ProjectRoleType curUserProjectRoleType, long projectID, long userID, ProjectRoleType projectRoleType) throws MyException {
        if (projectRoleType == null
                || projectRoleType == ProjectRoleType.CREATOR
                || projectRoleType == ProjectRoleType.SUPER_MANAGER
                || curUserProjectRoleType == null
                || curUserProjectRoleType == ProjectRoleType.APPLY
                || curUserProjectRoleType == ProjectRoleType.MEMBER) {
            throw new MyException(ResultCode.PERMISSION_NO_ACCESS);
        }
//        组织新的权限
        ProjectRole newProjectRole = new ProjectRole(projectID, userID, projectRoleType);
//        获取旧权限
        ProjectRoleType oldProjectRole = projectRoleMapper.getProjectRoleType(projectID, userID);
        if (oldProjectRole != null) {
            throw new MyException(ResultCode.DATA_ALREADY_EXISTED);
        }
        projectRoleMapper.addProjectRole(newProjectRole);
    }

    /**
     * 从项目内移除用户
     *
     * @param user      操作用户
     * @param projectID 项目ID
     * @param userID    被操作用户ID
     * @return
     */
    public void deleteUserFromProject(User user, long projectID, long userID) throws MyException {
        if (user.getUserID() == userID) {
            if (user.getProjectRoleType() == ProjectRoleType.CREATOR) {
                //创建者自己不可以退出
                throw new MyException(ResultCode.PERMISSION_NO_ACCESS);
            }
        } else {
            ProjectRoleType type1 = user.getProjectRoleType();
            ProjectRoleType type2 = projectRoleMapper.getProjectRoleType(projectID, userID);
            if (type1 == ProjectRoleType.CREATOR || (type1 == ProjectRoleType.SUPER_MANAGER && (type2 != ProjectRoleType.CREATOR && type2 != ProjectRoleType.SUPER_MANAGER))) {
            } else {
                throw new MyException(ResultCode.PERMISSION_NO_ACCESS);
            }
        }
        projectRoleMapper.deleteProjectRole(projectID, userID);
    }

    /**
     * 根据ID获取项目信息
     *
     * @param projectID
     * @return
     */
    public Project getProjectInfoByProjectID(long userID, long projectID) throws MyException {
        //首先获取用户信息
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        }
        ProjectRoleType projectRoleType = projectRoleMapper.getProjectRoleType(projectID, userID);
        if (!project.isOpenState() && (projectRoleType == null || projectRoleType == ProjectRoleType.APPLY)) {
            throw new MyException(ResultCode.PERMISSION_NO_ACCESS);
        }
        project.setProjectRole(projectRoleType);
        return project;
    }

    /**
     * 根据项目邀请码获获取项目信息
     *
     * @param invitationCode
     * @return
     */
    public Project getProjectInfoByCode(long userID, String invitationCode) throws MyException {
        Project project = projectMapper.getProjectByCode(invitationCode);
        if (project == null)
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        ProjectRoleType projectRole = projectRoleMapper.getProjectRoleType(project.getProjectID(), userID);
        if (projectRole != null) {
            project.setProjectRole(projectRole);
        }
        return project;
    }

    /**
     * 根据邀请码加入项目
     *
     * @param userID         当前用户ID
     * @param invitationCode 邀请码
     * @return
     */
    public void joinProjectByCode(long userID, String invitationCode) throws MyException {
        Project project = getProjectByCode(invitationCode);
        if (project == null)
            //如果根据code没有找到项目，那么返回错误信息
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        //将用户添加到项目内，这里因为使用的是邀请码，所以直接成为普通用户
        appendUser2Project(ProjectRoleType.SUPER_MANAGER, project.getProjectID(), userID, ProjectRoleType.MEMBER);
    }

    /**
     * 申请加入项目
     *
     * @param userID    当前用户ID
     * @param projectID 申请的项目ID
     * @return
     */
    public void applyToProject(long userID, long projectID) throws MyException {
        Project project = getProjectByID(projectID);
        if (project == null)
            //如果根据用户ID没有找到项目，那么返回错误信息
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        appendUser2Project(ProjectRoleType.SUPER_MANAGER, projectID, userID, ProjectRoleType.APPLY);
    }

    private String generateInvitationCode() {
        return UUID.randomUUID().toString();
    }

    public String updateProjectInCode(User user, long projectID) throws MyException {
        String newCode = generateInvitationCode();
        long lineN = projectMapper.updateProjectInCode(projectID, newCode);
        if (lineN == 0) {
            throw new MyException(ResultCode.RESUTL_DATA_NONE);
        }
        return newCode;
    }

    /**
     * 函数存在的问题，没有考虑权限，没有验证projectID
     *
     * @param user
     * @param projectID
     */
    public void startProject(User user, long projectID) {
        projectMapper.updateProjectStartTime(projectID);
    }


    /**
     * 根据最大值和最小值将指标值进行标准化
     *
     * @param maxAndMin
     * @param d
     * @return
     */
    private double normalizeIndex(double[] maxAndMin, double d) {
        if (maxAndMin[0] == maxAndMin[1])
            return 0;
        return (d - maxAndMin[1]) / (maxAndMin[0] - maxAndMin[1]);
    }


    /**
     * 保存序列化后的指标值
     * @param projectID
     * @param inputIndexMap
     * @param outputIndexMap
     */
    private void saveNormalizeProjectIndex(long projectID, Map<String, Double> inputIndexMap, Map<String, Double> outputIndexMap) {
        projectMapper.saveNormalizeIndex(projectID, inputIndexMap);
        projectMapper.saveNormalizeIndex(projectID, outputIndexMap);
    }


    public Project getProjectByProblemID(String problemID) {
        return projectMapper.getProjectByProblemID(problemID);
    }
}
