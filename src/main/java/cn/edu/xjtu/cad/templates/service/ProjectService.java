package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.UserRoleFilter;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.Result;
import cn.edu.xjtu.cad.templates.model.project.*;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileFilter;
import java.sql.Ref;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Filter;
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

    public List<Project> getAllProjectList(User user){
        return projectMapper.getAllProjectListByUserID(user.getUserID());
    }

    public List<Project> getOwnedProjectList(User user){

        return getProjectListByProjectIDList(new ArrayList<>(user.getProjectRoleTypeSetMap().get(ProjectRoleType.CREATOR)));
    }

    public List<Project> getJoinedProjectList(User user){
        List<Long> projectIDList = new ArrayList<>(user.getProjectRoleTypeSetMap().get(ProjectRoleType.SUPER_MANAGER));
        projectIDList.addAll(user.getProjectRoleTypeSetMap().get(ProjectRoleType.MEMBER));
        return getProjectListByProjectIDList(projectIDList);
    }

    public List<Project> getApplyProjectList(User user){
        return getProjectListByProjectIDList(new ArrayList<>(user.getProjectRoleTypeSetMap().get(ProjectRoleType.APPLY)));
    }

    public long createProject(Project project,long userID,long referID){
        //获取柔性模板信息
        Refer refer = referMapper.getReferByID(referID);
        List<Step> stepList = JSONArray.parseArray(refer.getSteps(), Step.class);
        //解析模板中的阶段
        project.setStepMap(stepList.stream().collect(Collectors.toMap(Step::getStepIndex,Function.identity())));
        //解析模板中的节点
        project.setNodeMap(JSONArray.parseArray(refer.getNodes(), Node.class).stream().collect(Collectors.toMap(Node::getStepIndex,Function.identity())));
        //解析模板的中的边
        project.setEdges(JSONArray.parseArray(refer.getEdges(), Edge.class));
        //设置创建者
        project.setCreatorID(userID);
        return addProject(project);
    }

    public long createProject(Project project,long userID){
        Step step = new Step("新阶段");
        project.setStepMap(new HashMap<String,Step>(){{
            put(step.getStepIndex(),step);
        }});
        Node node = new Node(step.getStepIndex(),"新的工作节点");
        project.setNodeMap(new HashMap<String,Node>(){{
            put(node.getNodeIndex(),node);
        }});
        project.setCreatorID(userID);
        return addProject(project);
    }

    public Project getProjectByID(long projectID){
        //查询项目的基础信息
        Project project = projectMapper.getProjectByID(projectID);
        //获取项目创建者
        project.setCreatorID(projectRoleMapper.getUserIDListInProjectByProjectRoleType(projectID,ProjectRoleType.CREATOR).get(0));
        //获取项目的阶段
        stepMapper.getStepsOfProject(projectID).forEach(step->{
            project.getStepMap().put(step.getStepIndex(),step);
        });
        //组织项目的节点和节点之间的关系
        Map<String,Node> nodeMap =nodeMapper.getNodesOfProject(projectID).stream().collect(Collectors.toMap(Node::getNodeIndex,Function.identity()));
        for (Edge edge : edgeMapper.getEdgesOfProject(projectID)) {
            String nodeI = edge.getNodeI();
            String nodeJ = edge.getNodeJ();
            if(nodeMap.get(nodeI).getNextNodeIndexList()==null){
                nodeMap.get(nodeI).setNextNodeIndexList(new ArrayList<>());
            }
            if(nodeMap.get(nodeJ).getNextNodeIndexList()==null){
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
        long projectID = project.getId();
        //新建项目权限
        ProjectRole projectRole = new ProjectRole(projectID,project.getCreatorID(),ProjectRoleType.CREATOR);
        projectRoleMapper.addProjectRole(projectRole);

        //向数据库中添加阶段
        project.getStepMap().values().forEach(step -> step.setProjectID(projectID));
        stepMapper.addAllSteps(project.getStepMap().values().stream().collect(Collectors.toList()));

        //向数据库中添加节点
        project.getNodeMap().values().forEach(node -> node.setProjectID(projectID));
        nodeMapper.addAllNodes(project.getNodeMap().values().stream().collect(Collectors.toList()));

        //构建节点-结果
        List<NodeResult> resultList = project.getNodeMap().values().stream().map(node->new NodeResult(projectID,node.getNodeIndex(),project.getCreatorID())).collect(Collectors.toList());
        //向数据库中添加节点结果
        nodeResultMapper.addAllNodeResultList(resultList);

        //构建节点对应权限
        List<NodeRole> nodeRoleList = project.getNodeMap().values().stream().map(node -> new NodeRole(projectID,node.getNodeIndex(),project.getCreatorID(),NodeRoleType.MANAGER)).collect(Collectors.toList());
        nodeRoleMapper.addAllNodeRole(nodeRoleList);

        //向数据库中添加边
        project.getEdges().forEach(edge -> edge.setProjectID(projectID));
        if(project.getEdges()!=null&&project.getEdges().size()!=0){
            edgeMapper.addAllEdges(project.getEdges());
        }
        return projectID;
    }




    /**
     * 更新项目内的用户权限
     * @param member 当前用户
     * @param projectID 项目ID
     * @param userID 待修改的用户名
     * @param newProjectRole 新权限
     * @return
     */
    @UserRoleFilter(allowedProjectRoleTypes = {ProjectRoleType.CREATOR,ProjectRoleType.SUPER_MANAGER})
    public Result updateRoleOfMemberInProject(User member, long projectID, long userID, ProjectRoleType newProjectRole) {
        return null;
    }

    public List<Project> getProjectListByProjectIDList(List<Long> projectIDList){
        return projectMapper.getProjectListByProjectIDList(projectIDList);
    }

    public Project getProjectByCode(String code) {
        return projectMapper.getProjectByCode(code);
    }
}
