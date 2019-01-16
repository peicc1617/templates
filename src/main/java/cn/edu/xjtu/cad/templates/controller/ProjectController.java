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
import org.springframework.web.bind.annotation.*;

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
    public Result getOwnedProjectList(@CurUser User user) {
        return projectService.getOwnedProjectListResult(user);
    }

    @RequestMapping(value = "/joined", method = RequestMethod.GET)
    public Result getJoinedProjectList(@CurUser User user) {
        return projectService.getJoinedProjectListResult(user);
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public Result getMyProjectList(@CurUser User user) {
        return projectService.getMyProjectListResult(user);
    }

    @RequestMapping(value = "/open",method = RequestMethod.GET)
    public Result getOpenProjectList(){
        return projectService.getOpenProjectListResult();
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET,params = "projectID")
    public Result getProjectInfoByProjectID(@CurUser User user, long projectID){
        return projectService.getProjectInfoByIDResult(user,projectID);
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET,params = "invitationCode")
    public Result getProjectInfoByCode(@CurUser User user, String invitationCode){
        return projectService.getProjectInfoByCodeResult(invitationCode);
    }
    /**
     * 获取id对应项目
     * @param projectID 项目ID
     * @return 返回该项目，JSON格式
     */
    @RequestMapping(value = "", method = RequestMethod.GET,params = "projectID")
    public Result getProjectByProjectID(@CurUser User user, long projectID) {
        return projectService.getProjectByIDResult(user,projectID);
    }

    @RequestMapping(value = "",method = RequestMethod.GET,params = "invitationCode")
    public Result getProjectByCode(String invitationCode){
        return projectService.getProjectByCodeResult(invitationCode);
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
    public Result createProject(@CurUserID long userID,Project project,long referID) {
        return projectService.newProject(project, userID,referID);
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
    public Result updateProjectName(@CurUser User user,@PathVariable long projectID, String projectName) {
        return projectService.updateProjectInfo(user,projectID,projectName,null,null);
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
    public Result updateProjectDes(@CurUser User user,@PathVariable long projectID, String projectDesc) {
        return projectService.updateProjectInfo(user,projectID,null,projectDesc,null);
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
    public Result updateProjectTags(@CurUser User user,@PathVariable long projectID, String projectTags) {
        return projectService.updateProjectInfo(user,projectID,null,null,projectTags);
    }

    @SystemControllerLog(content = "将项目删除", logType = LogType.PROJECT, methodType = MethodType.DELETE)
    @RequestMapping(value = "/{projectID}", method = RequestMethod.DELETE)
    public Result deleteProject(@CurUser User user,@PathVariable long projectID) {
        return projectService.deleteProject(user,projectID);
    }

    @RequestMapping(value = "/{projectID}/role/userList", method = RequestMethod.GET)
    public Result getProjectRoleByID(@CurUser User user,@PathVariable long projectID) {
        return projectService.getUserListInProject(user,projectID);
    }

    @RequestMapping(value = "/projectRoleType.json", method = RequestMethod.GET)
    public Map<String, String> getProjectRoleByID() {
        return ProjectRole.getProjectRoleTypeMap();
    }

    @RequestMapping(value = "/{projectID}/role/member", method = RequestMethod.GET)
    public ProjectRole getProjectRole(@PathVariable long projectID, long userID) {
        return projectRoleMapper.getMemberRoleOfProject(projectID, userID);
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
    public Result updateProjectRole(@CurUser User user, @PathVariable long projectID, long userID, ProjectRoleType projectRole) {
        return projectService.updateRoleOfMemberInProject(user,projectID,userID,projectRole);
    }


    @SystemControllerLog(content = "邀请用户${userID}加入项目", logType = LogType.USER, methodType = MethodType.ADD)
    @RequestMapping(value = "/{projectID}/role", method = RequestMethod.POST)
    public Result addProjectRole(@CurUser User curUser,@PathVariable long projectID,long  userID) {

        return projectService.appendUser2Project(curUser,projectID,userID);
    }

    @SystemControllerLog(content = "将用户${userID}移除出项目", logType = LogType.USER, methodType = MethodType.DELETE)
    @RequestMapping(value = "/{projectID}/role", method = RequestMethod.DELETE)
    public Result deleteProjectRole(@CurUser User user,@PathVariable long projectID, long userID) {
        return projectService.deleteUserFromProject(user,projectID,userID);
    }


}
