package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRoleMapper {

    long addProjectRole(ProjectRole projectRole);

    long deleteProjectRole(@Param("projectID")long projectID,@Param("userID") long userID);

    ProjectRoleType getProjectRoleType(@Param("projectID") long projectID, @Param("userID") long userID);

    List<ProjectRole> getRoleOfProject(@Param("projectID")long projectID);

    List<ProjectRole> getRoleOfProjectExcept(@Param("projectID")long projectID,@Param("roles")String roleTypes);

    List<ProjectRole> getRoleOfProjectIn(@Param("projectID")long projectID,@Param("roles")ProjectRoleType[] roleTypes);

    List<Long> getUserIDListInProjectByProjectRoleType(@Param("projectID") long projectID,@Param("role") ProjectRoleType role);

    ProjectRole getRoleOfMemberInProject(@Param("id")long projectID,@Param("userID") long userID);

    long updateProjectRole(@Param("projectID")long projectID,@Param("userID") long userID,@Param("newRole") ProjectRoleType role);

}
