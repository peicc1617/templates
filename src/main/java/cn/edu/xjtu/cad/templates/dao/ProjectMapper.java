package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectMapper {


    List<Project> getAllProjectListByUserID(@Param("userID")long userID) throws DataAccessResourceFailureException;

    /**
     * 查询当前项目ID对应的项目
     * @param projectID 项目ID
     * @return 返回该项目信息，如果为null，说明当前项目不存在
     */
    Project getProjectByID(long projectID);


    List<Project> getProjectListByProjectIDList(List<Long> projectIDList);

    Project getProjectByCode(String invitationCode);
    /**
     * 新建项目
     * @param project 项目内容
     * @return
     */
    long addProject(Project project);

    /**
     * 更新项目信息
     * @param project 项目信息
     * @return
     */
    long updateProjectInfo(Project project);

    /**
     * 根据ID删除项目
     * @param projectID 需要删除的项目ID
     * @return 返回是否删除成功
     */
    long deleteProject(long projectID);


    List<Project> getOpenProject(@Param("userID") long userID);

    long updateProjectInCode(@Param("projectID")long projectID,@Param("invitationCode") String invitationCode);

    List<Project> getProjectListByUserAndRole(@Param("userID")long userID,@Param("projectRole") ProjectRoleType creator);

    void updateProjectStartTime(long projectID);


    void saveNormalizeIndex(@Param("projectID")long projectID,@Param("map") Map<String, Double> outputIndexMap);

    void updateDea(@Param("projectID") long i, @Param("dea")double v);


    Project getProjectByProblemID(@Param("problemID") String problemID );

    void unbindProblemID(@Param("projectID")long  projectID);
}
