package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRoleMapper {

    @Insert("INSERT INTO project_role VALUES (#{projectID},#{username},#{projectRole},#{memberRole})")
    boolean addProjectRole(ProjectRole projectRole);

    @Delete("DELETE FROM project_role where projectID = #{projectID} AND username = #{username}")
    boolean deleteProjectRole(@Param("projectID")int projectID,@Param("username") String username);

    @Select("SELECT * FROM project_role where projectID = #{projectID} AND username = #{username}")
    ProjectRole getMemberRoleOfProject(@Param("projectID") int projectID, @Param("username") String username);

    @Select("SELECT * FROM project_role where projectID = #{projectID} ")
    List<ProjectRole> getRoleOfProject(int projectID);

    @Update("UPDATE project_role SET projectRole = #{projectRole.projectRole},memberRole = #{projectRole.memberRole}" +
            "WHERE projectID = #{projectRole.projectID} AND username =#{projectRole.username}")
    boolean updateProjectRole(ProjectRole projectRole);


    @Select("SELECT username FROM project_role where projectID = #{projectID} AND projectRole = #{role}")
    String getCreatorOfProject(@Param("projectID") int projectID,@Param("role") int role);

    @Select("SELECT username FROM project_role where projectID = #{projectID} AND projectRole = #{role}")
    List<String> getManagerOfProject(@Param("projectID") int projectID,@Param("role") int role);

    @Select("SELECT username FROM project_role where projectID = #{projectID} AND memberRole = #{role}")
    List<String> getMemberManagerOfProject(@Param("projectID") int projectID,@Param("role") int role);
}
