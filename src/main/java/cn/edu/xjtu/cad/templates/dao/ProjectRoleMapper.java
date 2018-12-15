package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRoleMapper {

    @Insert("INSERT INTO project_role VALUES (#{projectID},#{username},#{projectRole})")
    boolean addProjectRole(ProjectRole projectRole);

    @Delete("DELETE FROM project_role where projectID = #{projectID} AND username = #{username}")
    boolean deleteProjectRole(@Param("projectID")int projectID,@Param("username") String username);

    @Select("SELECT * FROM project_role where projectID = #{projectID} AND username = #{username}")
    ProjectRole getMemberRoleOfProject(@Param("projectID") int projectID, @Param("username") String username);

    @Select("SELECT * FROM project_role where projectID = #{projectID} ")
    List<ProjectRole> getRoleOfProject(int projectID);

    @Select("SELECT * FROM project_role where projectID = #{projectID} AND projectRole not in (#{roles})")
    List<ProjectRole> getRoleOfProjectExcept(@Param("projectID")int projectID,@Param("roles")String roleTypes);


    @Select("SELECT * FROM project_role where projectID = #{projectID} AND projectRole in (#{roles})")
    List<ProjectRole> getRoleOfProjectIn(int projectID,@Param("roles")ProjectRoleType[] roleTypes);


    @Update("UPDATE project_role SET projectRole = #{newRole}" +
            "WHERE projectID = #{projectID} AND username =#{username}")
    int updateProjectRole(@Param("projectID")int projectID,@Param("username") String username,@Param("newRole") ProjectRoleType role);

    @Select("SELECT username FROM project_role where projectID = #{projectID} AND projectRole = #{role}")
    String getCreatorOfProject(@Param("projectID") int projectID,@Param("role") ProjectRoleType role);

    @Select("SELECT username FROM project_role where projectID = #{projectID} AND projectRole = #{role}")
    List<String> getSuperManagerOfProject(@Param("projectID") int projectID,@Param("role") ProjectRoleType role);

    @Select("SELECT * FROM project_role where projectID = #{id} AND username = #{name}")
    ProjectRole getRoleOfMemberInProject(@Param("id")int projectID,@Param("name") String username);
}
