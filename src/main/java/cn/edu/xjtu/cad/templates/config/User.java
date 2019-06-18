package cn.edu.xjtu.cad.templates.config;

import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {
    /**
     * 用户ID
     */
    private long userID;

    private ProjectRoleType projectRoleType;
    private Map<String,NodeRoleType> nodeRoleTypeMap;

    public User() {
    }

    public ProjectRoleType getProjectRoleType() {
        return projectRoleType;
    }

    public void setProjectRoleType(ProjectRoleType projectRoleType) {
        this.projectRoleType = projectRoleType;
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
