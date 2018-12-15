package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectMapper {

    /**
     * 查询当前用户所处的所有模板项目
     * @param username
     * @return 返回项目列表
     */
    @Select("SELECT project.`name`,project.id,project.createTime " +
            "FROM project,project_role " +
            "WHERE project.id = project_role.projectID AND project_role.username=#{username} AND project_role.projectRole = #{role} " +
            "ORDER BY project.createTime")
    List<Project> getOwnedProjectListByUsernameWithRole(@Param("username") String username,@Param("role")ProjectRoleType role);

    @Select("SELECT * " +
            "FROM project,project_role " +
            "WHERE project.id = project_role.projectID AND project_role.username=#{username} AND project_role.projectRole > 0 " +
            "ORDER BY project.editTime")
    List<Project> getProjectListByUserName(@Param("username") String username);
    /**
     * 新建项目
     * @param project 项目内容
     * @return
     */
    int addProject(Project project);

    /**
     * 查询当前项目ID对应的项目
     * @param projectID 项目ID
     * @return 返回该项目信息，如果为null，说明当前项目不存在
     */
    @Select("SELECT * from project  where id = #{projectID}")
    Project getProjectByID(int projectID);

    /**
     * 更新项目信息
     * @param project 项目信息
     * @return
     */
    @Update("UPDATE project set name = #{name}," +
            "description = #{description}," +
            "tags = #{tags} " +
            "where id = #{id}")
    void updateProjectInfo( Project project);

    /**
     * 根据ID删除项目
     * @param projectID 需要删除的项目ID
     * @return 返回是否删除成功
     */
    @Delete("DELETE FROM project where id = #{projectID}")
    void deleteProject(int projectID);

}
