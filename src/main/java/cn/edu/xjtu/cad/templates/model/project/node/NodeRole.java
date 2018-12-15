package cn.edu.xjtu.cad.templates.model.project.node;

import java.util.HashMap;
import java.util.Map;

public class NodeRole {
    private int projectID;
    private String nodeIndex;
    private String username;
    private NodeRoleType nodeRole;

    public NodeRole() {
    }

    public NodeRole(int projectID, String nodeIndex, String username, NodeRoleType nodeRole) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.username = username;
        this.nodeRole = nodeRole;
    }

    public static Map<String,String> getNodeRoleTypeMap(){
        Map<String,String> stringStringMap = new HashMap<>(NodeRoleType.values().length,1);
        for (NodeRoleType type : NodeRoleType.values()) {
            stringStringMap.put(type.toString(),type.getName());
        }
        return stringStringMap;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NodeRoleType getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(NodeRoleType nodeRole) {
        this.nodeRole = nodeRole;
    }
}
