package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRoleMapper {


    @Insert("INSERT INTO project_node_role VALUES " +
            "(#{nodeRole.projectID},#{nodeRole.nodeIndex},#{nodeRole.username},#{nodeRole.nodeRole})")
    boolean addNodeRole(NodeRole nodeRole);

    @Delete("DELETE FROM project_node_role where projectID = #{projectID} AND nodeIndex = #{nodeIndex} AND username = #{username} ")
    boolean deleteNodeRole(int projectID,int nodeIndex,String username);

    @Select("SELECT * FROM project_node_role where projectID = #{projectID} AND nodeIndex = #{nodeIndex} AND username = #{username}")
    ProjectRole getNodeRole(int projectID,int nodeIndex,String username);

    @Update("UPDATE project_node_role SET nodeRole = #{nodeRole.nodeRole}" +
            "WHERE projectID = #{nodeRole.projectID} AND nodeIndex = #{nodeRole.nodeIndex}AND username =#{projectRole.username}")
    boolean updateNodeRole(NodeRole nodeRole);

    boolean addAllNodeRole(List<NodeRole> nodeRoleList);
}
