package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("SELECT * FROM project_role where username = #{username}")
    List<ProjectRole> getProjectRole(String username);

    @Select("SELECT * FROM project_node_role where username = #{username}")
    List<NodeRole> getNodeRole(String username);
}
