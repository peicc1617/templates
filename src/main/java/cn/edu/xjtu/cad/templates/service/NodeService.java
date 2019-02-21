package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.Edge;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    NodeResultMapper nodeResultMapper;

    @Autowired
    NodeRoleMapper nodeRoleMapper;

    @Autowired
    ProjectRoleMapper projectRoleMapper;

    @Autowired
    EdgeMapper edgeMapper;

    /**
     * 删除节点
     * @param user 当前操作用户
     * @param projectID 项目ID
     * @param nodeIndex 节点index
     */
    public void deleteNode(User user,long projectID,String nodeIndex){
        //首先删除边
        edgeMapper.deleteEdgeOfNode(projectID,nodeIndex);
        //首先删除节点
        nodeMapper.deleteNode(projectID,nodeIndex);
    }

    public void updateNodeResult(@CurUser User user, NodeResult nodeResult) {
        updateNodeResultList(user, Collections.singletonList(nodeResult));
    }

    public void updateNodeResultList(@CurUser User user, List<NodeResult> nodeResultList) {
        for (NodeResult nodeResult : nodeResultList) {
            nodeResultMapper.updateNodeResult(nodeResult);
        }
    }

    public NodeResult getNodeResultByUserAndNodeIndex(@CurUser User user, long projectID, String nodeIndex) {
        return nodeResultMapper.getResultListByNodeIndexAndUserIDList(projectID, nodeIndex, Collections.singletonList(user.getUserID())).get(0);
    }

    public List<NodeRole> getNodeRoleList(User user, long projectID, String nodeIndex) {
        List<NodeRole> nodeRoleList = nodeRoleMapper.getNodeRoleList(projectID, nodeIndex);
        Set<Long> userIDSet = nodeRoleList.stream().map(nodeRole -> nodeRole.getUserID()).collect(Collectors.toSet());
        projectRoleMapper.getRoleOfProject(projectID).stream()
                .filter(projectRole -> !userIDSet.contains(projectRole.getUserID()))
                .forEach(projectRole ->
                        nodeRoleList.add(new NodeRole(projectID, nodeIndex, projectRole.getUserID(), NodeRoleType.STRANGER)));
        return nodeRoleList;
    }

    public void updateNodeRole(User user, NodeRole nodeRole) {
        boolean shouldHasRole = false,shouldHasResult =false;
        switch (nodeRole.getRoleType()){
            case VIEWER:
                shouldHasRole=true;
            case STRANGER:
                shouldHasResult=false;
                break;
            case OPERATOR:
            case MANAGER:
                shouldHasRole=true;
                shouldHasResult=true;
                break;
              default:
                  break;
        }
        NodeRoleType oldRole = nodeRoleMapper.getNodeRole(nodeRole.getProjectID(),nodeRole.getNodeIndex(),nodeRole.getUserID());
        if(shouldHasRole){
            //如果该用户需要有nodeRole记录,那么首先需要判断数据库中有没有nodeRole记录
            if(oldRole==null){
                //如果没有该用户的节点权限,那么就添加一个
                nodeRoleMapper.addNodeRole(nodeRole);
            }else {
                //如果有该用户的节点权限,那么就修改
                nodeRoleMapper.updateNodeRole(nodeRole);
            }
        }else {
            //如果该用户不需要有nodeRole记录,那么直接删除
            nodeRoleMapper.deleteNodeRole(nodeRole.getProjectID(),nodeRole.getNodeIndex(),nodeRole.getUserID());
        }
        if(shouldHasResult){
            //如果该用户需要有nodeResult记录
            if(oldRole==NodeRoleType.MANAGER||oldRole==NodeRoleType.OPERATOR){
                //如果该用户已经有nodeResult记录，那么不处理
            }else {
                //如果该用户没有nodeResult记录，那么创建一条
                nodeResultMapper.addAllNodeResultList(Collections.singletonList(new NodeResult(nodeRole.getProjectID(),nodeRole.getNodeIndex(),nodeRole.getUserID())));
            }
        }else {
            //如果该用户不需要有nodeResult记录,那么直接删除
            nodeResultMapper.deleteNodeResult(nodeRole.getProjectID(),nodeRole.getNodeIndex(),nodeRole.getUserID());
        }
    }

    public void addPath(User user, Edge edge) {
        edgeMapper.addAllEdges(Collections.singletonList(edge));
    }

    public void deletePath(User user, Edge edge) {
        edgeMapper.deleteEdge(edge.getProjectID(),edge.getNodeI(),edge.getNodeJ());
    }
}
