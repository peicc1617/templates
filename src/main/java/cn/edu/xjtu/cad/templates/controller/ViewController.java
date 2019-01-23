package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.config.API;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.service.ReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class ViewController {

    @Resource(name = "projectManagerAPI")
    API projectManagerAPI;

    @Autowired
    User user;

    @Autowired
    ProjectService projectService;

    @ModelAttribute
    public void addProject(Model model, @PathVariable(required = false) Long projectID) {
        model.addAttribute("pmAPI", projectManagerAPI);
        List<Project> projectList = projectService.getMyProjectList(user.getUserID());
        Map<ProjectRoleType,List<Project>> map = projectList.stream().collect(Collectors.toMap(
                Project::getProjectRole,
                p -> new ArrayList<Project>() {{
                    add(p);
                }},
                (List<Project> oldList, List<Project> newList) -> {
                    oldList.addAll(newList);
                    return oldList;
                }));
//        添加自己拥有的项目
        model.addAttribute("ownedProjectList", map.get(ProjectRoleType.CREATOR));
        //添加自己加入的项目
        model.addAttribute("joinedProjectList", new ArrayList<Project>(){{
            addAll(map.getOrDefault(ProjectRoleType.MEMBER,new ArrayList<>()));
            addAll(map.getOrDefault(ProjectRoleType.SUPER_MANAGER,new ArrayList<>()));
        }});
        //添加自己申请的项目
        model.addAttribute("applyProjectList", map.get(ProjectRoleType.APPLY));
        if(projectID!=null&&projectID>0){
            addProject2Model(model,projectID);
        }
    }

    /**
     * 查看新建项目页面
     * @return
     */
    @RequestMapping("/project/new.html")
    public String viewNewProject() {
        return "newProject";
    }

    /**
     * 查看我的项目列表
     * @return
     */
    @RequestMapping("/project/my.html")
    public String viewMyProjectList() {
        return "myProjectList";
    }

    /**
     * 查看公共项目列表
     * @param model
     * @return
     */
    @RequestMapping("/project/public.html")
    public String viewPublicProjectList(Model model) {
        List<Project> openProjectList = projectService.getOpenProjectList(user.getUserID());
        model.addAttribute("openProjectList",openProjectList);
        return "publicProjectList";
    }


    /**
     * 查看项目详细
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/view.html")
    public String viewProject(@PathVariable long projectID) {
        return "project";
    }

    /**
     * 查看项目信息
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/info.html")
    public String viewProjectInfo(@PathVariable long projectID) {
        return "projectInfo";
    }

    /**
     * 查看项目成员
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/user.html")
    public String viewProjectUser(@PathVariable long projectID) {
        return "projectUser";
    }

    /**
     * 查看项目报告
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/report.html")
    public String viewProjectReport(@PathVariable long projectID) {
        return "projectReport";
    }

    @RequestMapping("/project/noAccess.html")
    public String viewProjectNoAccess(){
        return "noAccess";
    }

    @RequestMapping("/project/noResource.html")
    public String viewProjectNoResource(){
        return "noProject";
    }


    private void addProject2Model(Model model, long projectID) {
        Project project = projectService.getProjectByID(projectID);
        model.addAttribute("project", project);
    }

}
