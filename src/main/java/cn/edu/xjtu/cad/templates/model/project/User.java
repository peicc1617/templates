package cn.edu.xjtu.cad.templates.model.project;

import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.util.Map;
import java.util.Set;


public class User {



    /**
     * 用户ID
     */
    private long userID;


    private Map<Long,ProjectRoleType> projectRoleTypeMap;
    private Map<String, NodeRoleType> nodeRoleTypeMap;

    public User() {
    }

    public User(long userID) {
        this.userID = userID;
    }

    public User(long userID, Map<Long,ProjectRoleType>projectRoleTypeMap , Map<String, NodeRoleType> nodeRoleTypeMap) {
        this.userID = userID;
        this.projectRoleTypeMap = projectRoleTypeMap;
        this.nodeRoleTypeMap = nodeRoleTypeMap;
    }

    public Map<Long, ProjectRoleType> getProjectRoleTypeMap() {
        return projectRoleTypeMap;
    }

    public void setProjectRoleTypeMap(Map<Long, ProjectRoleType> projectRoleTypeMap) {
        this.projectRoleTypeMap = projectRoleTypeMap;
    }

    public Map<String, NodeRoleType> getNodeRoleTypeMap() {
        return nodeRoleTypeMap;
    }

    public void setNodeRoleTypeMap(Map<String, NodeRoleType> nodeRoleTypeMap) {
        this.nodeRoleTypeMap = nodeRoleTypeMap;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

}
