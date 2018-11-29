package cn.edu.xjtu.cad.templates;

import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.project.*;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
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
import java.util.List;
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
        Refer refer = referMapper.getReferByID(project.getReferID());
        //解析模板中的阶段
        project.setSteps(JSONArray.parseArray(refer.getSteps(), Step.class));
        project.setNodes(JSONArray.parseArray(refer.getNodes(),Node.class));
        project.setEdges(JSONArray.parseArray(refer.getEdges(),Edge.class));
        project.setCreator(username);
        createProjectWithRefer(project);
        return true;
    }

    public Project getProjectByID(int projectID){
        Project project = projectMapper.getProjectByID(projectID);
        project.setCreator(projectRoleMapper.getCreatorOfProject(projectID,ProjectRole.CREATOR));
        project.setSteps(stepMapper.getStepsOfProject(projectID));
        project.setNodes(nodeMapper.getNodesOfProject(projectID));
        project.setEdges(edgeMapper.getEdgesOfProject(projectID));
        project.setMembers(projectRoleMapper.getRoleOfProject(projectID));
        return project;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public int createProjectWithRefer(Project project) {
         projectMapper.addProject(project);
        int projectID = project.getId();
        //新建项目权限
        ProjectRole projectRole = new ProjectRole(projectID,project.getCreator(),ProjectRole.CREATOR,1);
        projectRoleMapper.addProjectRole(projectRole);

        //向数据库中添加阶段
        project.getSteps().forEach(step -> step.setProjectID(projectID));
        stepMapper.addAllSteps(project.getSteps());

        //向数据库中添加节点
        project.getNodes().forEach(node -> node.setProjectID(projectID));
        nodeMapper.addAllNodes(project.getNodes());

        //构建节点-结果
        List<NodeResult> resultList = project.getNodes().stream().map(node->new NodeResult(projectID,node.getNodeIndex(),project.getCreator())).collect(Collectors.toList());
        //向数据库中添加节点结果
        nodeResultMapper.addAllNodeResultList(resultList);

        //构建节点对应权限
        List<NodeRole> nodeRoleList = project.getNodes().stream().map(node -> new NodeRole(projectID,node.getNodeIndex(),project.getCreator(),NodeRole.MANAGER)).collect(Collectors.toList());
        nodeRoleMapper.addAllNodeRole(nodeRoleList);

        //向数据库中添加边
        project.getEdges().forEach(edge -> edge.setProjectID(projectID));
        edgeMapper.addAllEdges(project.getEdges());
        return 0;
    }



}
