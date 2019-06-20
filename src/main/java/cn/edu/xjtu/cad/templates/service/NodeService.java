package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.feign.ProjectManagerRemote;
import cn.edu.xjtu.cad.templates.feign.UserRemote;
import cn.edu.xjtu.cad.templates.model.CAIUser;
import cn.edu.xjtu.cad.templates.model.project.Edge;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.node.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
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

    @Autowired
    UserRemote userRemote;

    @Autowired
    ProjectManagerRemote projectManagerRemote;

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

    /**更新节点结果
     * @param user
     * @param nodeResult
     */
    public void updateNodeResult(@CurUser User user, NodeResult nodeResult) {
        updateNodeResultList(user, Collections.singletonList(nodeResult));
    }

    /**
     * 更新多个节点结果
     * @param user
     * @param nodeResultList
     */
    public void updateNodeResultList(@CurUser User user, List<NodeResult> nodeResultList) {
        for (NodeResult nodeResult : nodeResultList) {
            nodeResultMapper.updateNodeResult(nodeResult);
        }
    }

    /**
     * 通过用户更新节点结果
     * @param user
     * @param projectID
     * @param nodeIndex
     * @return
     */
    public NodeResult getNodeResultByUserAndNodeIndex(@CurUser User user, long projectID, String nodeIndex) {
        return nodeResultMapper.getResultListByNodeIndexAndUserIDList(projectID, nodeIndex, Collections.singletonList(user.getUserID())).get(0);
    }

    /**
     * 获取节点权限
     * @param user
     * @param projectID
     * @param nodeIndex
     * @return
     */
    public List<NodeRole> getNodeRoleList(User user, long projectID, String nodeIndex) {
        List<NodeRole> nodeRoleList = nodeRoleMapper.getNodeRoleList(projectID, nodeIndex);
        Set<Long> userIDSet = nodeRoleList.stream()
                .map(NodeRole::getUserID)
                .collect(Collectors.toSet());
        projectRoleMapper.getRoleOfProject(projectID).stream()
                .filter(projectRole -> !userIDSet.contains(projectRole.getUserID()))
                .forEach(projectRole ->
                        nodeRoleList.add(new NodeRole(projectID, nodeIndex, projectRole.getUserID(), NodeRoleType.STRANGER)));
        userIDSet.addAll(nodeRoleList.stream()
                .map(NodeRole::getUserID).collect(Collectors.toSet()));
        Map<Long,CAIUser> map = userRemote.listIn(new ArrayList<>(userIDSet))
                .stream().collect(Collectors.toMap(u->u.getId(),Function.identity()));
        nodeRoleList.forEach(nodeRole -> {
            String name = map.get(nodeRole.getUserID()).getNickName();
            nodeRole.setNickName(name);
        });
        return nodeRoleList;
    }

    /**
     * 更新用户在节点下的节点权限
     * @param user
     * @param nodeRole
     */
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

    /**
     * 添加工作节点之间的边
     * @param user
     * @param edge
     */
    public void addPath(User user, Edge edge) {
        edgeMapper.addAllEdges(Collections.singletonList(edge));
    }

    /**
     * 删除工作节点之间的边
     * @param user
     * @param edge
     */
    public void deletePath(User user, Edge edge) {
        edgeMapper.deleteEdge(edge.getProjectID(),edge.getNodeI(),edge.getNodeJ());
    }

    /**
     * 更新节点结果的状态，表示审阅人对节点结果的一个审批情况
     *
     * warning: 这里没有考虑用户权限的问题，如果用户在该阶段是管理员才能修改相应的权限
     *
     * @param nodeResult
     * @param user
     */
    public void updateStateOfResult(NodeResult nodeResult, User user) {
        switch (nodeResult.getState()){
            case ACCEPT:
                nodeResult.setMessage(NodeResult.PASSED_MESSAGE);
                break;
            case PENDING_VIEW:
                nodeResult.setMessage(NodeResult.APPLY_MESSAGE);
                break;
            case TO_MODIFIED:
                nodeResult.setMessage(NodeResult.TO_EDIT_MESSAGE);
                break;
            case PENDING_CONFIRM:
            case UN_BIND:
                nodeResult.setMessage(NodeResult.PASSED_MESSAGE);
                nodeResult.setMessage("");
                nodeResult.setResultKey("");
                nodeResult.setResultID(0);
                nodeResult.setResultName("");
                break;
        }
        nodeResultMapper.updateNodeResult(nodeResult);
    }

    /**
     * 用户将自己的数据绑定在当前节点。
     *
     * 这里也需要考虑一个权限问题，也就是用户是否有权限在当前节点绑定数据。
     *
     * @param projectID
     * @param nodeIndex
     * @param resultKey
     * @param resultID
     * @param resultName
     * @param user
     * @return
     */
    public NodeResult bindMyResult(long projectID, String nodeIndex, String resultKey, int resultID, String resultName,User user) {
        NodeResult nodeResult = getNodeResultByUserAndNodeIndex(user,projectID,nodeIndex);
        nodeResult.setResultKey(resultKey);
        nodeResult.setResultID(resultID);
        nodeResult.setResultName(resultName);
        nodeResult.setState(NodeResultState.PENDING_VIEW);
        updateNodeResult(user,nodeResult);
        return nodeResult;
    }

    /**
     * 将数据与当前节
     * @param projectID
     * @param nodeIndex
     * @param user
     * @return
     */
    public NodeResult unbindingMyResult(long projectID, String nodeIndex, User user) {
        NodeResult nodeResult = getNodeResultByUserAndNodeIndex(user,projectID,nodeIndex);
        nodeResult.setState(NodeResultState.UN_BIND);
        nodeResult.setMessage("");
        nodeResult.setResultKey("");
        nodeResult.setResultID(0);
        nodeResult.setResultName("");
        nodeResultMapper.updateNodeResult(nodeResult);
        return nodeResult;
    }


    /**
     * 数据已过期
     * @param projectID
     * @param nodeIndex
     * @param userIDList
     * @return
     */
    public List<NodeResult> outDateResult(long projectID, String nodeIndex, List<Long> userIDList) {
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserIDList(projectID,nodeIndex,userIDList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResultState.PENDING_CONFIRM);
            nodeResult.setMessage(NodeResult.EDIT_MESSAGE);
        });
        for (NodeResult nodeResult : nodeResultList) {
            nodeResultMapper.updateNodeResult(nodeResult);
        }
        return nodeResultList;
    }

    /**
     * 数据已失效
     * @param projectID
     * @param nodeIndex
     * @param userIDList
     * @return
     */
    public List<NodeResult> disableResult(long projectID, String nodeIndex, List<Long> userIDList) {
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserIDList(projectID,nodeIndex,userIDList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResultState.UN_BIND);
            nodeResult.setMessage(NodeResult.DISABLE_MESSAGE);
            nodeResult.setResultID(0);
            nodeResult.setResultKey(null);
            nodeResult.setResultName(null);
        });

        for (NodeResult nodeResult : nodeResultList) {
            nodeResultMapper.updateNodeResult(nodeResult);
        }
        return nodeResultList;
    }

    public NodeResult getMyResultOfNodeIndex(long projectID, String nodeIndex, User user) {
        return nodeResultMapper.getMyNodeResultInNode(projectID,nodeIndex,user.getUserID());
    }

    public List<NodeResult> getResultListByNodeIndex(long projectID, String nodeIndex) {
        List<NodeResult> nodeResults = nodeResultMapper.getResultListByNodeIndex(projectID,nodeIndex);
        List<Long> userIDs = nodeResults.stream().map(nodeResult -> nodeResult.getUserID()).collect(Collectors.toList());
        Map<Long,CAIUser> map = userRemote.listIn(userIDs)
                .stream().collect(Collectors.toMap(u->u.getId(),Function.identity()));
        nodeResults.forEach(nodeResult -> {
            String name = map.get(nodeResult.getUserID()).getNickName();
            nodeResult.setNickName(name);
        });

        Map<String,NodeResult> nodeResultMap = nodeResults.stream()
                .filter(nodeResult ->
                        nodeResult.getResultKey()!=null&&nodeResult.getResultKey().trim().length()>0)
                .collect(Collectors.toMap(NodeResult::getResultKey,Function.identity()));
        return nodeResults;
    }

    public void updateNodeTemplate(long projectID, String nodeIndex, int templateProjectID) {
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setTemplateProjectID(templateProjectID);
            nodeMapper.updateNode(node);
        }
    }


}
