package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.User;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.service.ReferService;
import cn.edu.xjtu.cad.templates.util.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


@Controller
public class ViewController {

    @Autowired
    ProjectService projectService;

    @Autowired
    ReferService referService;

    /**
     * 查看新建项目页面
     *
     * @param model 视图
     * @return
     */
    @RequestMapping("/project/new.html")
    public String viewNewProject(Model model, @CurUser User user) {
        addProjectList(model, user);
        addReferList(model);
        return "newProject";
    }

    @RequestMapping("/project/my.html")
    public String viewMyProjectList(Model model,@CurUser User user){
        addProjectList(model, user);
        return "myProjectList";
    }

    @RequestMapping("/project/public.html")
    public String viewPublicProjectList(Model model,@CurUser User user){
        addProjectList(model, user);
        return "publicProjectList";
    }


    /**
     * 查看项目
     *
     * @param model
     * @param user
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/view.html")
    public String viewProject(Model model, @CurUser User user,
                              @PathVariable long projectID) {
        addProjectList(model, user);
        String ret = addProject(model,user,projectID);
        if(ret!=null) return ret;
        return "project";
    }

    /**
     * 查看项目信息
     *
     * @param model
     * @param user
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/info.html")
    public String viewProjectInfo(Model model, @CurUser User user,
                                  @PathVariable long projectID) {
        addProjectList(model, user);
        String ret = addProject(model,user,projectID);
        if(ret!=null) return ret;
        return "projectInfo";
    }

    /**
     * 查看项目成员
     *
     * @param model
     * @param user
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/user.html")
    public String viewProjectUser(Model model, @CurUser User user,
                                  @PathVariable long projectID) {
        addProjectList(model, user);
        String ret = addProject(model,user,projectID);
        if(ret!=null) return ret;
        return "projectUser";
    }

    /**
     * 查看项目报告
     *
     * @param model
     * @param user
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/report.html")
    public String viewProjectReport(Model model, @CurUser User user,
                                    @PathVariable long projectID) {
        addProjectList(model, user);
        String ret = addProject(model,user,projectID);
        if(ret!=null) return ret;
        return "projectReport";
    }


    /**
     * 包装侧边栏需要的项目列表
     * @param model
     * @param user
     */
    private void addProjectList(Model model, @CurUser User user) {
        //添加自己创建的项目
        model.addAttribute("ownedProjectList", projectService.getOwnedProjectList(user));
        //添加自己加入的项目
        model.addAttribute("joinedProjectList", projectService.getJoinedProjectList(user));
        //添加自己申请的项目
        model.addAttribute("applyProjectList", projectService.getApplyProjectList(user));
    }

    private String addProject(Model model,@CurUser User user,long projectID){
        Project project = projectService.getProjectByID(projectID);
        if(project==null)
            return "noProject";
        switch (user.getProjectRoleTypeMap().get(projectID)){
            case SUPER_MANAGER:
            case MEMBER:
            case CREATOR:
                model.addAttribute("project", project);
                break;
            default:
                return "noAccess";
        }
        return null;
    }

    private void addReferList(Model model) {
        model.addAttribute("referList", referService.getAllRefer());
    }

    private void addUserInfo(@CurUser User user,Model model){
        model.addAttribute("curUser",user);
    }

}
