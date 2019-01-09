package cn.edu.xjtu.cad.templates.model.project;

import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.util.Map;
import java.util.Set;


public class User {



    /**
     * 用户ID
     */
    private long userID;


    private Map<ProjectRoleType,Set<Long>> projectRoleTypeSetMap;
    private Map<NodeRoleType,Set<String[]>> nodeRoleTypeSetMap;

    public User() {
    }

    public User(long userID) {
        this.userID = userID;
    }

    public User(long userID, Map<ProjectRoleType, Set<Long>> projectRoleTypeSetMap, Map<NodeRoleType, Set<String[]>> nodeRoleTypeSetMap) {
        this.userID = userID;
        this.projectRoleTypeSetMap = projectRoleTypeSetMap;
        this.nodeRoleTypeSetMap = nodeRoleTypeSetMap;
    }

    public Map<ProjectRoleType, Set<Long>> getProjectRoleTypeSetMap() {
        return projectRoleTypeSetMap;
    }

    public void setProjectRoleTypeSetMap(Map<ProjectRoleType, Set<Long>> projectRoleTypeSetMap) {
        this.projectRoleTypeSetMap = projectRoleTypeSetMap;
    }

    public Map<NodeRoleType, Set<String[]>> getNodeRoleTypeSetMap() {
        return nodeRoleTypeSetMap;
    }

    public void setNodeRoleTypeSetMap(Map<NodeRoleType, Set<String[]>> nodeRoleTypeSetMap) {
        this.nodeRoleTypeSetMap = nodeRoleTypeSetMap;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

}
