package cn.edu.xjtu.cad.templates.model.project;

import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.util.HashMap;
import java.util.Map;

public class ProjectRole {
    private long projectID;
    private long userID;
    private ProjectRoleType projectRole;

    public ProjectRole() {
    }

    public ProjectRole(long projectID, long userID, ProjectRoleType projectRole) {
        this.projectID = projectID;
        this.userID = userID;
        this.projectRole = projectRole;
    }

    public static Map<String,String> getProjectRoleTypeMap(){
        Map<String,String> stringStringMap = new HashMap<>(ProjectRoleType.values().length,1);
        for (ProjectRoleType type : ProjectRoleType.values()) {
            stringStringMap.put(type.toString(),type.getName());
        }
        return stringStringMap;
    }

    public long getProjectID() {
        return projectID;
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

    public ProjectRoleType getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(ProjectRoleType projectRole) {
        this.projectRole = projectRole;
    }

}
