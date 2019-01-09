package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectMapper {


    List<Project> getAllProjectListByUserID(@Param("userID")long userID);

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
    int addProject(Project project);

    /**
     * 更新项目信息
     * @param project 项目信息
     * @return
     */
    void updateProjectInfo(Project project);

    /**
     * 根据ID删除项目
     * @param projectID 需要删除的项目ID
     * @return 返回是否删除成功
     */
    void deleteProject(long projectID);


}
