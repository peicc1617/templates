package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.annotation.CurUsername;
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
     *
     * @return 返回项目列表JSON格式
     */
    @RequestMapping(value = "/owned", method = RequestMethod.GET)
    public List<Project> getOwnedProjectListByUsername(@CurUsername String curUsername) {
        List<Project> projectList = projectMapper.getOwnedProjectListByUsernameWithRole(curUsername, ProjectRoleType.CREATOR);
        return projectList;
    }

    @RequestMapping(value = "/joined", method = RequestMethod.GET)
    public List<Project> getJoinedProjectListByUsername(@CurUsername String curUsername) {
        List<Project> projectList = projectMapper.getOwnedProjectListByUsernameWithRole(curUsername, ProjectRoleType.APPLY);
        projectList.addAll(projectMapper.getOwnedProjectListByUsernameWithRole(curUsername, ProjectRoleType.MEMBER));
        return projectList;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Project> getAllProjectListByUsername(@CurUsername String curUsername) {
        List<Project> projectList = projectMapper.getProjectListByUserName(curUsername);
        return projectList;
    }

    /**
     * 获取id对应项目
     *
     * @param projectID 项目ID
     * @return 返回该项目，JSON格式
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Project getProjectByProjectID(int projectID) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        return projectService.getProjectByID(projectID);

    }

    /**
     * 创建项目
     *
     * @param project 项目的基本信息
     * @return 返回是否创建成功，True or False
     */
    @SystemControllerLog(
            content = "创建了项目,项目名${name},项目描述${description},项目的标签${tags}",
            logType = LogType.PROJECT,
            methodType = MethodType.ADD)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean createProject(Project project,@CurUsername String curUsername) {
        projectService.wrapProject(project, curUsername);
        return true;
    }

    /**
     * 修改项目名
     *
     * @param name      新项目名
     * @param projectID 项目的ID
     * @return 返回是否修改成功， True or False
     */
    @SystemControllerLog(
            content = "将项目名修改为${name}",
            logType = LogType.PROJECT,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/name", method = RequestMethod.PUT)
    public int updateProjectName(int projectID, String name) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setName(name);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    /**
     * 更新项目描述
     *
     * @param description 项目描述
     * @param projectID   项目的ID
     * @return 返回是否修改成功，True or False
     */
    @SystemControllerLog(content = "将项目描述修改为${description}",
            logType = LogType.PROJECT,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/description", method = RequestMethod.PUT)
    public int updateProjectDes(int projectID, String description) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setDescription(description);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    /**
     * 修改项目的标签
     *
     * @param tags      项目的新标签
     * @param projectID 项目的ID
     * @return 返回是否修改成功， true or false
     */
    @SystemControllerLog(content = "将项目标签修改为${tags}", logType = LogType.PROJECT, methodType = MethodType.UPDATE)
    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    public int updateProjectTags(int projectID, String tags) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if (project == null) {
            res = 1;
        } else {
            project.setTags(tags);
            projectMapper.updateProjectInfo(project);
        }
        return res;
    }

    @SystemControllerLog(content = "将项目删除", logType = LogType.PROJECT, methodType = MethodType.DELETE)
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public boolean deleteProject(int projectID) {
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        projectMapper.deleteProject(projectID);
        return true;
    }

    @RequestMapping(value = "role/userList", method = RequestMethod.GET)
    public List<ProjectRole> getProjectRoleByID(int projectID) {
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
    public ProjectRole getProjectRole(int projectID, String username) {
        return projectRoleMapper.getMemberRoleOfProject(projectID, username);
    }

    /**
     * 修改用户的项目内的权限
     * @param member 当前用户
     * @param projectID 项目ID
     * @param username 待修改权限的用户名
     * @param projectRole 新的项目权限
     * @return
     */
    @SystemControllerLog(content = "将用户${username}的权限修改为${projectRole}", logType = LogType.USER, methodType = MethodType.UPDATE)
    @RequestMapping(value = "role", method = RequestMethod.PUT)
    public Result updateProjectRole(@CurUser User member, int projectID, String username, ProjectRoleType projectRole) {
        return projectService.updateRoleOfMemberInProject(member,projectID,username,projectRole);

    }

//    @SystemControllerLog(content = "将用户${username}的用户权限修改为${memberRole}",logType = LogType.USER,methodType = MethodType.UPDATE)
//    @RequestMapping(value = "role/member",method = RequestMethod.PUT)
//    public boolean updateProjectMemberRole(int projectID,String username,int memberRole){
//        ProjectRole role = projectRoleMapper.getRoleOfMemberInProject(projectID,username);
//        if(role!=null){
//            role.setMemberRole(memberRole);
//            projectRoleMapper.updateProjectRole(role);
//        }
//        return true;
//    }

    @SystemControllerLog(content = "邀请用户${username}加入项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "role", method = RequestMethod.POST)
    public boolean addProjectRole(@CurUser User member,int projectID, String username) {
        ProjectRole projectRole  = new ProjectRole(projectID,username,ProjectRoleType.MEMBER);
        projectRoleMapper.addProjectRole(projectRole);
        return true;
    }

    @SystemControllerLog(content = "将用户${username}移除出项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "role", method = RequestMethod.DELETE)
    public boolean deleteProjectRole(int projectID, String username) {
        projectRoleMapper.deleteProjectRole(projectID, username);
        return true;
    }


}
