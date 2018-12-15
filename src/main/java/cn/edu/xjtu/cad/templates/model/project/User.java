package cn.edu.xjtu.cad.templates.model.project;

import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.util.Map;


public class User {


    private String username;

    public User(String username) {
        this.username = username;
    }

    private Map<Integer,ProjectRoleType> projectRoleMap;

    private Map<Integer,Map<Integer,NodeRoleType>> nodeProjectRoleTypeMap;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Integer, ProjectRoleType> getProjectRoleMap() {
        return projectRoleMap;
    }

    public void setProjectRoleMap(Map<Integer, ProjectRoleType> projectRoleMap) {
        this.projectRoleMap = projectRoleMap;
    }

    public Map<Integer, Map<Integer, NodeRoleType>> getNodeProjectRoleTypeMap() {
        return nodeProjectRoleTypeMap;
    }

    public void setNodeProjectRoleTypeMap(Map<Integer, Map<Integer, NodeRoleType>> nodeProjectRoleTypeMap) {
        this.nodeProjectRoleTypeMap = nodeProjectRoleTypeMap;
    }
}
