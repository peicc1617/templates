package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRoleMapper {


    long addNodeRole(NodeRole nodeRole);

    long deleteNodeRole(@Param("projectID") long projectID, @Param("nodeIndex")int nodeIndex,@Param("userID") long userID);

    ProjectRole getNodeRole(@Param("projectID")long projectID,long nodeIndex,long userID);

    long updateNodeRole(NodeRole nodeRole);

    long addAllNodeRole(List<NodeRole> nodeRoleList);
}
