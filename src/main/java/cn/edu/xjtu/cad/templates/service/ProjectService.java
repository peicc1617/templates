package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.annotation.UserRoleFilter;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.Result;
import cn.edu.xjtu.cad.templates.model.ResultCode;
import cn.edu.xjtu.cad.templates.model.project.*;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("projectService")
public class ProjectService {

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

    public List<Project> getMyProjectList(User user) {
        return projectMapper.getAllProjectListByUserID(user.getUserID());
    }

    public List<Project> getOwnedProjectList(User user) {
        List<Long> projectIDList = getProjectIDList(user,false,ProjectRoleType.CREATOR);
        return getProjectListByProjectIDList(projectIDList);
    }

    public List<Project> getJoinedProjectList(User user) {
        return getProjectListByProjectIDList(getProjectIDList(user,false,ProjectRoleType.MEMBER,ProjectRoleType.SUPER_MANAGER));
    }



    public List<Project> getApplyProjectList(User user) {
        return getProjectListByProjectIDList(getProjectIDList(user,false,ProjectRoleType.APPLY));
    }

    public List<Project> getOpenProjectList() {
        return projectMapper.getOpenProject();
    }
    private List<Long> getProjectIDList(User user,boolean and, ProjectRoleType... projectRoleTypes) {
        return user.getProjectRoleTypeMap()
                .entrySet()
                .stream()
                .filter(e -> {
                    boolean flag = and;
                    for (ProjectRoleType projectRoleType : projectRoleTypes) {
                        if(and) flag= flag && e.getValue()==projectRoleType;
                        else flag = flag || e.getValue()==projectRoleType;
                    }
                    return flag;
                })
                .map(e -> e.getKey()).collect(Collectors.toList());
    }

    public Result newProject(Project project,long userID,long referID){
        long projectID = 0;
        project.setCreatorID(userID);
        if(referID>0){
            projectID = createProject(project,referID);
        }else {
            projectID = createProject(project);
        }
        if(projectID>0){
            return Result.success(projectID);
        }else {
            return Result.failure(ResultCode.DATA_ALREADY_EXISTED);
        }
    }
    public long createProject(Project project,long referID) {
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
            if (nodeMap.get(nodeI).getNextNodeIndexList() == null) {
                nodeMap.get(nodeI).setNextNodeIndexList(new ArrayList<>());
            }
            if (nodeMap.get(nodeJ).getNextNodeIndexList() == null) {
                nodeMap.get(nodeJ).setPreNodeIndexList(new ArrayList<>());
            }
            nodeMap.get(nodeI).getNextNodeIndexList().add(nodeJ);
            nodeMap.get(nodeJ).getPreNodeIndexList().add(nodeI);
        }
        //填充项目的节点
        project.setNodeMap(nodeMap);
        //获取当前项目的用户
        project.setMembers(projectRoleMapper.getRoleOfProject(projectID));
        return project;
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
     * @param user         当前用户
     * @param projectID      项目ID
     * @param userID         待修改的用户名
     * @param newProjectRole 新权限
     * @return
     */
    @UserRoleFilter(allowedProjectRoleTypes = {ProjectRoleType.CREATOR, ProjectRoleType.SUPER_MANAGER})
    public Result updateRoleOfMemberInProject(User user, long projectID, long userID, ProjectRoleType newProjectRole) {
        switch (user.getProjectRoleTypeMap().get(projectID)){
            case SUPER_MANAGER:
                if(newProjectRole!=ProjectRoleType.CREATOR&&newProjectRole!=ProjectRoleType.SUPER_MANAGER){
                    break;
                }
            case APPLY:
            case MEMBER:
                return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
        }
        projectRoleMapper.updateProjectRole(projectID,userID,newProjectRole);
        return Result.success();
    }

    public List<Project> getProjectListByProjectIDList(List<Long> projectIDList) {
        return projectMapper.getProjectListByProjectIDList(projectIDList);
    }

    public Project getProjectByCode(String code) {
        return projectMapper.getProjectByCode(code);
    }

    public Result getOwnedProjectListResult(User user) {
        List<Project> projects = getMyProjectList(user);
        return Result.success(projects);
    }

    public Result getJoinedProjectListResult(User user) {
        return Result.success(getJoinedProjectList(user));
    }

    public Result getMyProjectListResult(User user) {
        return Result.success(getMyProjectList(user));
    }

    public Result getOpenProjectListResult() {
        return Result.success(getOpenProjectList());
    }


    public Result getProjectByCodeResult(String code) {
        return Result.success(getProjectByCode(code));
    }

    public Result getProjectByIDResult(User user, long projectID) {
        Project project = getProjectByID(projectID);
        return Result.success(project);
    }

    public Result getProjectInfoByIDResult(User user, long projectID) {
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        switch (user.getProjectRoleTypeMap().get(projectID)) {
            case CREATOR:
            case MEMBER:
            case SUPER_MANAGER:
                break;
            default:
                if (!project.isOpenState()) {
                    return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
                }
        }
        return Result.success(project);
    }

    public Result getProjectInfoByCodeResult(String invitationCode) {
        Project project = projectMapper.getProjectByCode(invitationCode);
        if (project == null) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        return Result.success(project);
    }

    public Result updateProjectInfo(@CurUser User user, long projectID, String projectName, String projectDesc, String projectTags) {
        Project project = projectMapper.getProjectByID(projectID);
        if(project==null){
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        boolean flag = false;
        if(projectName!=null){
            flag=true;
            project.setProjectName(projectName);
        }
        if(projectDesc!=null){
            flag=true;
            project.setProjectDesc(projectDesc);
        }
        if(projectTags!=null){
            flag=true;
            project.setProjectTags(projectTags);
        }
        projectMapper.updateProjectInfo(project);
        if(flag){
            return Result.success();
        }else {
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
    }

    public Result deleteProject(@CurUser User user,long projectID) {
        projectMapper.deleteProject(projectID);
        return Result.success();
    }

    public Result getUserListInProject(User user, long projectID) {
        List<ProjectRole> projectRoleList = projectRoleMapper.getRoleOfProject(projectID);
        for (int i = 0; i < projectRoleList.size(); i++) {
            if(projectRoleList.get(i).getUserID()==user.getUserID()){
                projectRoleList.remove(i);
                break;
            }
        }
        return Result.success(projectRoleList);
    }

    public Result appendUser2Project(User curUser, long projectID, long userID) {
        ProjectRole projectRole  = new ProjectRole(projectID,userID,ProjectRoleType.MEMBER);
        projectRoleMapper.addProjectRole(projectRole);
        return Result.success();
    }

    public Result deleteUserFromProject(User user,long projectID,long userID){
        projectRoleMapper.deleteProjectRole(projectID,userID);
        return Result.success();
    }
}
