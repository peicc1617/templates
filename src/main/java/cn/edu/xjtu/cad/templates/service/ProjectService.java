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

    public boolean wrapProject(Project project,String username){
        //获取柔性模板信息
        Refer refer = referMapper.getReferByID(project.getReferID());
        //解析模板中的阶段
        project.setStepMap(JSONArray.parseArray(refer.getSteps(), Step.class).stream().collect(Collectors.toMap(Step::getStepIndex,Function.identity())));
        //解析模板中的节点
        project.setNodeMap(JSONArray.parseArray(refer.getNodes(), Node.class).stream().collect(Collectors.toMap(Node::getStepIndex,Function.identity())));
        //解析模板的中的边
        project.setEdges(JSONArray.parseArray(refer.getNodes(), Edge.class));
        //设置创建者
        project.setCreator(username);
        createProjectWithRefer(project);
        return true;
    }

    public Project getProjectByID(int projectID){
        //查询项目的基础信息
        Project project = projectMapper.getProjectByID(projectID);
        //获取项目创建者
        project.setCreator(projectRoleMapper.getCreatorOfProject(projectID,ProjectRoleType.CREATOR));
        //获取项目的阶段
        project.setStepMap(stepMapper.getStepsOfProject(projectID).stream().collect(Collectors.toMap(Step::getStepIndex,Function.identity())));
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
    public int createProjectWithRefer(Project project) {
         projectMapper.addProject(project);
        int projectID = project.getId();
        //新建项目权限
        ProjectRole projectRole = new ProjectRole(projectID,project.getCreator(),ProjectRoleType.CREATOR);
        projectRoleMapper.addProjectRole(projectRole);

        //向数据库中添加阶段
        project.getStepMap().values().forEach(step -> step.setProjectID(projectID));
        stepMapper.addAllSteps(project.getStepMap().values().stream().collect(Collectors.toList()));

        //向数据库中添加节点
        project.getNodeMap().values().forEach(node -> node.setProjectID(projectID));
        nodeMapper.addAllNodes(project.getNodeMap().values().stream().collect(Collectors.toList()));

        //构建节点-结果
        List<NodeResult> resultList = project.getNodeMap().values().stream().map(node->new NodeResult(projectID,node.getNodeIndex(),project.getCreator())).collect(Collectors.toList());
        //向数据库中添加节点结果
        nodeResultMapper.addAllNodeResultList(resultList);

        //构建节点对应权限
        List<NodeRole> nodeRoleList = project.getNodeMap().values().stream().map(node -> new NodeRole(projectID,node.getNodeIndex(),project.getCreator(),NodeRoleType.MANAGER)).collect(Collectors.toList());
        nodeRoleMapper.addAllNodeRole(nodeRoleList);

        //向数据库中添加边
        project.getEdges().forEach(edge -> edge.setProjectID(projectID));
        edgeMapper.addAllEdges(project.getEdges());
        return 0;
    }


    /**
     * 更新项目内的用户权限
     * @param member 当前用户
     * @param projectID 项目ID
     * @param username 待修改的用户名
     * @param newProjectRole 新权限
     * @return
     */
    @UserRoleFilter(allowedProjectRoleTypes = {ProjectRoleType.CREATOR,ProjectRoleType.SUPER_MANAGER})
    public Result updateRoleOfMemberInProject(User member, int projectID, String username, ProjectRoleType newProjectRole) {
//        Map<Integer,ProjectRoleType> map = member.getProjectRoleMap();
//        if(map.containsKey(projectID)){
//            ProjectRoleType curUserRole = map.get(projectID);
//            if(curUserRole==ProjectRoleType.CREATOR){
//
//            }else if(curUserRole==ProjectRoleType.SUPER_MANAGER){
//
//            }else return Result.getNoAuth();
//            //用户在项目内
//        }else {
//            return Result.getNoAuth();
//        }
//        int changeLineNumber = projectRoleMapper.updateProjectRole(projectID,username,newProjectRole);
//        ProjectRole role = projectRoleMapper.getRoleOfMemberInProject(projectID, username);
//        if (role != null) {
////            role.setProjectRole(projectRole);
////            projectRoleMapper.updateProjectRole(role);
//        }
//        return null;
        return null;
    }
}
