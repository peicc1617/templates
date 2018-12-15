package cn.edu.xjtu.cad.templates.model.project;

import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.util.HashMap;
import java.util.Map;

public class ProjectRole {
    private int projectID;
    private String username;
    private ProjectRoleType projectRole;

    public ProjectRole() {
    }

    public ProjectRole(int projectID, String username, ProjectRoleType projectRole) {
        this.projectID = projectID;
        this.username = username;
        this.projectRole = projectRole;
    }

    public static Map<String,String> getProjectRoleTypeMap(){
        Map<String,String> stringStringMap = new HashMap<>(ProjectRoleType.values().length,1);
        for (ProjectRoleType type : ProjectRoleType.values()) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ProjectRoleType getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(ProjectRoleType projectRole) {
        this.projectRole = projectRole;
    }

}
