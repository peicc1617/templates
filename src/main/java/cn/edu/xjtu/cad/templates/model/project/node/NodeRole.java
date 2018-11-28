package cn.edu.xjtu.cad.templates.model.project.node;

public class NodeRole {
    private int projectID;
    private int nodeIndex;
    private String username;
    private int nodeRole;
    public final static int MANAGER = 2;
    public final static int OPERATOR = 1;
    public final static int VIEWER = 0;

    public NodeRole() {
    }

    public NodeRole(int projectID, int nodeIndex, String username, int nodeRole) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.username = username;
        this.nodeRole = nodeRole;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(int nodeRole) {
        this.nodeRole = nodeRole;
    }
}
