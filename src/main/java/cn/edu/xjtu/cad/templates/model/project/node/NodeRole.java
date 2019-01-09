package cn.edu.xjtu.cad.templates.model.project.node;

import java.util.HashMap;
import java.util.Map;

public class NodeRole {
    private long projectID;
    private String nodeIndex;
    private long userID;
    private NodeRoleType nodeRole;

    public NodeRole() {
    }

    public NodeRole(long projectID, String nodeIndex, long userID, NodeRoleType nodeRole) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.userID = userID;
        this.nodeRole = nodeRole;
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

    public NodeRoleType getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(NodeRoleType nodeRole) {
        this.nodeRole = nodeRole;
    }
}
