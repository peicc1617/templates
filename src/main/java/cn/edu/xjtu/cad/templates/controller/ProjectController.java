package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.aop.MyException;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.ProjectRoleMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {


    @Autowired
    User user;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRoleMapper projectRoleMapper;

    /**
     * 获取当前用户所拥有的所有项目
     * @return 返回项目列表JSON格式
     */
    @RequestMapping(value = "/owned", method = RequestMethod.GET)
    public List<Project> getOwnedProjectList() {
        return projectService.getOwnedProjectList(user);
    }

    @RequestMapping(value = "/joined", method = RequestMethod.GET)
    public List<Project> getJoinedProjectList() {
//        return projectService.getJoinedProjectListResult(user);
        return null;
    }


    /**
     * 获取我的项目列表
     * @return
     */
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<Project> getMyProjectList() {
        return projectService.getMyProjectList(user.getUserID());
    }

    /**
     * 获取当前所有的公开项目
     * @return
     */
    @RequestMapping(value = "/public",method = RequestMethod.GET)
    public List<Project> getOpenProjectList(){
        return projectService.getOpenProjectList(user.getUserID());
    }



    /**
     * 获取id对应项目信息
     * @param projectID
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Project getProjectInfoByProjectID(long projectID) throws MyException {
        return projectService.getProjectInfoByProjectID(user.getUserID(),projectID);
    }

    /**
     * 根据邀请码获取项目信息
     * @param invitationCode 邀请码
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET,params = "invitationCode")
    public Project getProjectInfoByCode(String invitationCode) throws MyException {
        return projectService.getProjectInfoByCode(user.getUserID(),invitationCode);
    }

    /**
     * 申请加入项目
     * @param projectID 项目ID
     * @return 返回该项目，JSON格式
     */
    @RequestMapping(value = "/apply", method = RequestMethod.POST,params = "projectID")
    public void applyToProject(long projectID) throws MyException {
         projectService.applyToProject(user.getUserID(),projectID);
    }


    /**
     * 根据邀请码加入项目
     * @param invitationCode
     * @return
     */
    @RequestMapping(value = "/apply",method = RequestMethod.POST,params = "invitationCode")
    public void joinProjectByCode(String invitationCode) throws MyException {
         projectService.joinProjectByCode(user.getUserID(),invitationCode);
    }

    @RequestMapping(value = "/quit",method = RequestMethod.DELETE,params = "projectID")
    public ModelAndView quitFromProject(long projectID){
        ModelAndView model = new ModelAndView( "forward:/api/project/"+projectID+"/role?userID="+user.getUserID());//默认forward，可以不用写
        return model;
    }

    /**
     * 获取项目的详细信息，包括项目的信息、项目内的工作节点信息、项目内的阶段信息。
     * @param projectID
     * @return
     */
    @RequestMapping(value = "/{projectID}/detail",method = RequestMethod.GET)
    public Project getProjectDetail(@PathVariable long projectID){
        return projectService.getProjectByID(projectID);
    }
    /**
     * 创建项目
     * @param project 项目的基本信息
     * @return 返回是否创建成功，True or False
     */
    @SystemControllerLog(
            content = "创建了项目,项目名${projectName},项目描述${projectDesc},项目的标签${projectTags}",
            logType = LogType.PROJECT,
            methodType = MethodType.ADD)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public long createProject(Project project,long referID,String problemID) throws MyException {
        return projectService.newProject(project, user.getUserID(),referID,problemID);
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
    @RequestMapping(value = "/{projectID}/projectName", method = RequestMethod.PUT)
    public void updateProjectName(@PathVariable long projectID, String projectName) throws MyException {
        projectService.updateProjectInfo(user,projectID,projectName,null,null);
    }

    /**
     * 更新项目描述
     *
     * @param projectDesc 项目描述
     * @param projectID   项目的ID
     * @return
     */
    @SystemControllerLog(content = "将项目描述修改为${projectDesc}",
            logType = LogType.PROJECT,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/{projectID}/projectDesc", method = RequestMethod.PUT)
    public void updateProjectDes(@PathVariable long projectID, String projectDesc) throws MyException {
        projectService.updateProjectInfo(user,projectID,null,projectDesc,null);
    }

    /**
     * 修改项目的标签
     *
     * @param projectTags 项目的新标签
     * @param projectID 项目的ID
     * @return 返回是否修改成功
     */
    @SystemControllerLog(content = "将项目标签修改为${projectTags}", logType = LogType.PROJECT, methodType = MethodType.UPDATE)
    @RequestMapping(value = "/{projectID}/projectTags", method = RequestMethod.PUT)
    public void updateProjectTags(@PathVariable long projectID, String projectTags) throws MyException {
         projectService.updateProjectInfo(user,projectID,null,null,projectTags);
    }



    /**
     * 修改项目的邀请码
     * @param projectID
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/{projectID}/invitationCode", method = RequestMethod.PUT)
    public String[] updateProjectInvitationCode(@PathVariable long projectID) throws MyException {
        return new String[]{projectService.updateProjectInCode(user,projectID)};
    }

    /**删除项目
     * @param projectID
     */
    @SystemControllerLog(content = "将项目删除", logType = LogType.PROJECT, methodType = MethodType.DELETE)
    @RequestMapping(value = "/{projectID}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable long projectID) {
         projectService.deleteProject(user,projectID);
    }

    /**
     * 开始项目监控
     * @param projectID
     */
    @SystemControllerLog(content = "开始项目监控", logType = LogType.PROJECT, methodType = MethodType.UPDATE)
    @RequestMapping(value = "/{projectID}/doStart", method = RequestMethod.PUT)
    public void startProject(@PathVariable long projectID) {
        projectService.startProject(user,projectID);
    }


    /**
     * 获取成员在项目内的权限
     * @param projectID
     * @return
     */
    @RequestMapping(value = "/{projectID}/role/userList", method = RequestMethod.GET)
    public List<ProjectRole> getProjectRoleListByProjectID(@PathVariable long projectID) {
        return projectService.getUserListInProject(user,projectID);
    }

    /**
     * 获取权限对应表
     * @return
     */
    @RequestMapping(value = "/projectRoleType.json", method = RequestMethod.GET)
    public Map<String, String> getProjectRoleByID() {
        return ProjectRole.getProjectRoleTypeMap();
    }

    /**
     * 获取单个用户在项目内的权限
     * @param projectID
     * @param userID
     * @return
     */
    @RequestMapping(value = "/{projectID}/role/member", method = RequestMethod.GET)
    public ProjectRoleType getProjectRole(@PathVariable long projectID, long userID) {
        return projectRoleMapper.getProjectRoleType(projectID, userID);
    }

    /**
     * 修改用户的项目内的权限
     * @param projectID 项目ID
     * @param userID 待修改权限的用户名
     * @param projectRole 新的项目权限
     * @return
     */
    @SystemControllerLog(content = "将用户${userID}的权限修改为${projectRole}",
            logType = LogType.USER,
            methodType = MethodType.UPDATE)
    @RequestMapping(value = "/{projectID}/role", method = RequestMethod.PUT)
    public void updateProjectRole(@PathVariable long projectID, long userID, ProjectRoleType projectRole) throws MyException {
        projectService.updateRoleOfMemberInProject(user,projectID,userID,projectRole);
    }


    /**
     * 邀请用户加入项目
     * @param projectID
     * @param userID
     * @return
     */
    @SystemControllerLog(content = "邀请用户${userID}加入项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "/{projectID}/role", method = RequestMethod.POST)
    public void addProjectRole(@PathVariable long projectID,long  userID) throws MyException {
        projectService.appendUser2Project(user.getProjectRoleType(),projectID,userID,ProjectRoleType.MEMBER);
    }


    /**
     * 将用户移除项目
     * @param projectID
     * @param userID
     * @return
     */
    @SystemControllerLog(content = "将用户${userID}移除出项目", logType = LogType.USER, methodType = MethodType.DELETE)
    @RequestMapping(value = "/{projectID}/role", method = RequestMethod.DELETE)
    public void deleteProjectRole(@PathVariable long projectID, long userID) throws MyException {
        projectService.deleteUserFromProject(user,projectID,userID);
    }


}
