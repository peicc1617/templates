package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.annotation.CurUserID;
import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.model.Result;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.User;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.ProjectRoleMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {



    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRoleMapper projectRoleMapper;

    /**
     * 获取当前用户所出的所有项目
     * @return 返回项目列表JSON格式
     */
    @RequestMapping(value = "/owned", method = RequestMethod.GET)
    public List<Project> getOwnedProjectList(@CurUser User user) {
        return projectService.getOwnedProjectList(user);
    }

    @RequestMapping(value = "/joined", method = RequestMethod.GET)
    public List<Project> getJoinedProjectList(@CurUser User user) {
        return projectService.getJoinedProjectList(user);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Project> getAllProjectList(@CurUser User user) {
        return projectService.getAllProjectList(user);
    }

    /**
     * 获取id对应项目
     *
     * @param projectID 项目ID
     * @return 返回该项目，JSON格式
     */
    @RequestMapping(value = "", method = RequestMethod.GET,params = "projectID")
    public Project getProjectByProjectID(long projectID) {
        return projectService.getProjectByID(projectID);
    }

    @RequestMapping(value = "",method = RequestMethod.GET,params = "code")
    public Project getProjectByCode(String code){
        return projectService.getProjectByCode(code);
    }

    /**
     * 创建项目
     *
     * @param project 项目的基本信息
     * @return 返回是否创建成功，True or False
     */
    @SystemControllerLog(
            content = "创建了项目,项目名${projectName},项目描述${projectDesc},项目的标签${projectTags}",
            logType = LogType.PROJECT,
            methodType = MethodType.ADD)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public long createProject(@CurUserID long userID,Project project) {
        return projectService.createProject(project, userID);
    }

    /**
     * 修改项目名
     *
     * @param projectName      新项目名
     * @param projectID 项目的ID
     * @return 返回是否修改成功， True or False
     */
    @SystemControllerLog(
            content = "将项目名修改为${projectName}",
            logType = LogType.PROJECT,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/projectName", method = RequestMethod.PUT)
    public int updateProjectName(long projectID, String projectName) {
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setProjectName(projectName);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    /**
     * 更新项目描述
     *
     * @param projectDesc 项目描述
     * @param projectID   项目的ID
     * @return 返回是否修改成功，True or False
     */
    @SystemControllerLog(content = "将项目描述修改为${projectDesc}",
            logType = LogType.PROJECT,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/description", method = RequestMethod.PUT)
    public int updateProjectDes(long projectID, String projectDesc) {
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setProjectDesc(projectDesc);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    /**
     * 修改项目的标签
     *
     * @param projectTags      项目的新标签
     * @param projectID 项目的ID
     * @return 返回是否修改成功， true or false
     */
    @SystemControllerLog(content = "将项目标签修改为${projectTags}", logType = LogType.PROJECT, methodType = MethodType.UPDATE)
    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    public int updateProjectTags(long projectID, String projectTags) {
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setProjectTags(projectTags);
            projectMapper.updateProjectInfo(project);
        }
        return res;
    }

    @SystemControllerLog(content = "将项目删除", logType = LogType.PROJECT, methodType = MethodType.DELETE)
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public boolean deleteProject(long projectID) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        projectMapper.deleteProject(projectID);
        return true;
    }

    @RequestMapping(value = "role/userList", method = RequestMethod.GET)
    public List<ProjectRole> getProjectRoleByID(long projectID) {
        return projectRoleMapper.getRoleOfProjectExcept(projectID, ProjectRoleType.CREATOR.toString());
    }

//    @RequestMapping(value = "role/manager",method = RequestMethod.GET)
//    public List<ProjectRole> getProjectRoleByIDManager(int projectID){
//        return projectRoleMapper.getRoleOfProjectExcept(projectID,new ProjectRoleType[]{ProjectRoleType.CREATOR,ProjectRoleType.SUPER_MANAGER});
//    }

    @RequestMapping(value = "role/map", method = RequestMethod.GET)
    public Map<String, String> getProjectRoleByID() {
        return ProjectRole.getProjectRoleTypeMap();
    }

    @RequestMapping(value = "role/member", method = RequestMethod.GET)
    public ProjectRole getProjectRole(long projectID, long userID) {
        return projectRoleMapper.getMemberRoleOfProject(projectID, userID);
    }

    /**
     * 修改用户的项目内的权限
     * @param member 当前用户
     * @param projectID 项目ID
     * @param userID 待修改权限的用户名
     * @param projectRole 新的项目权限
     * @return
     */
    @SystemControllerLog(content = "将用户${userID}的权限修改为${projectRole}",
            logType = LogType.USER,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "role", method = RequestMethod.PUT)
    public Result updateProjectRole(@CurUser User member, long projectID, long userID, ProjectRoleType projectRole) {
        return projectService.updateRoleOfMemberInProject(member,projectID,userID,projectRole);
    }


    @SystemControllerLog(content = "邀请用户${userID}加入项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public boolean addProjectRole(@CurUser User curUser,long projectID,long  userID) {
        ProjectRole projectRole  = new ProjectRole(projectID,userID,ProjectRoleType.MEMBER);
        projectRoleMapper.addProjectRole(projectRole);
        return true;
    }

    @SystemControllerLog(content = "将用户${userID}移除出项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "role", method = RequestMethod.DELETE)
    public boolean deleteProjectRole(long projectID, long userID) {
        projectRoleMapper.deleteProjectRole(projectID, userID);
        return true;
    }


}
