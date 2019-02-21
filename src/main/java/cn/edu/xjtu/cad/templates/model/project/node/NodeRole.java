package cn.edu.xjtu.cad.templates.model.project.node;

import java.util.HashMap;
import java.util.Map;

public class NodeRole {
    private long projectID;
    private String nodeIndex;
    private long userID;
    private NodeRoleType roleType;

    public NodeRole() {
    }

    public NodeRole(long projectID, String nodeIndex, long userID, NodeRoleType roleType) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.userID = userID;
        this.roleType = roleType;
    }

    public static Map<String,String> getNodeRoleTypeMap(){
        Map<String,String> stringStringMap = new HashMap<>(NodeRoleType.values().length,1);
        for (NodeRoleType type : NodeRoleType.values()) {
            stringStringMap.put(type.toString(),type.getName());
        }
        return stringStringMap;
    }

    public long getProjectID() {
        return projectID;
    }

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public NodeRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(NodeRoleType roleType) {
        this.roleType = roleType;
    }
}
