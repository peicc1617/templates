package cn.edu.xjtu.cad.templates.model.project;

public class ProjectRole {
    private int projectID;
    private String username;
    private int projectRole;
    private int memberRole;
    public final static int CREATOR = 1;
    public final static int SUPERMANAGER = 2;
    public final static int MEMBER = 3;
    public final static int APPLY = 4;

    public ProjectRole() {
    }

    public ProjectRole(int projectID, String username, int projectRole, int memberRole) {
        this.projectID = projectID;
        this.username = username;
        this.projectRole = projectRole;
        this.memberRole = memberRole;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(int projectRole) {
        this.projectRole = projectRole;
    }


    public int getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(int memberRole) {
        this.memberRole = memberRole;
    }
}
