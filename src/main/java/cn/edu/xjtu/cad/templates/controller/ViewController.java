package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.config.API;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResultState;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import cn.edu.xjtu.cad.templates.service.LogService;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.service.ReferService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    LogService logService;

    @ModelAttribute
    public void addProject(Model model, @PathVariable(required = false) Long projectID) {
        model.addAttribute("pmAPI", projectManagerAPI);
        List<Project> projectList = projectService.getMyProjectList(user.getUserID());
        Map<ProjectRoleType, List<Project>> map = projectList.stream().collect(Collectors.toMap(
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
        model.addAttribute("joinedProjectList", new ArrayList<Project>() {{
            addAll(map.getOrDefault(ProjectRoleType.MEMBER, new ArrayList<>()));
            addAll(map.getOrDefault(ProjectRoleType.SUPER_MANAGER, new ArrayList<>()));
        }});
        //添加自己申请的项目
        model.addAttribute("applyProjectList", map.get(ProjectRoleType.APPLY));
        if (projectID != null && projectID > 0) {
            addProject2Model(model, projectID);
        }
    }

    /**
     * 查看新建项目页面
     *
     * @return
     */
    @RequestMapping("/project/new.html")
    public String viewNewProject() {
        return "newProject";
    }

    /**
     * 查看我的项目列表
     *
     * @return
     */
    @RequestMapping("/project/my.html")
    public String viewMyProjectList() {
        return "myProjectList";
    }

    /**
     * 查看公共项目列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/project/public.html")
    public String viewPublicProjectList(Model model) {
        List<Project> openProjectList = projectService.getOpenProjectList(user.getUserID());
        model.addAttribute("openProjectList", openProjectList);
        return "publicProjectList";
    }


    /**
     * 查看项目详细
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/view.html")
    public String viewProject(Model model, @PathVariable long projectID) {
        Map<String, JSONObject> map = Arrays.stream(NodeResultState.values()).collect(Collectors.toMap(state -> state.toString(),
                state -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("text", state.getText());
                    jsonObject.put("style", state.getStyle());
                    JSONArray jsonArray = new JSONArray(Arrays.stream(state.getNextStateArr()).map(next -> {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("text", next.getText());
                        jsonObject1.put("state", next.toString());
                        return jsonObject1;
                    }).collect(Collectors.toList()));
                    jsonObject.put("li", jsonArray);
                    return jsonObject;
                }));
        model.addAttribute("nodeResultStateMap", JSON.toJSONString(map));
        JSONArray jsonArray = new JSONArray(Arrays.stream(NodeRoleType.values())
                .map(nodeRoleType -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value",nodeRoleType.toString());
                    jsonObject.put("text",nodeRoleType.getName());
                    return jsonObject;
                }).collect(Collectors.toList()) );
        model.addAttribute("nodeRoleMap",jsonArray.toJSONString());
        return "project";
    }

    /**
     * 查看项目信息
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/info.html")
    public String viewProjectInfo(Model model,@PathVariable long projectID) {
        addProjectLog(model,logService.getProjectLogCut(projectID,1000));
        Project project = (Project) model.asMap().get("project");
        //成员个数
        model.addAttribute("memberCnt",project.getMembers().size());
        //阶段个数
        model.addAttribute("stepCnt",project.getStepMap().size());
        //节点个数
        model.addAttribute("nodeCnt",project.getNodeMap().size());
        //创新方法个数
        model.addAttribute("appCnt",project.getNodeMap().values().stream().filter(node -> StringUtils.isEmpty(node.getAppPath())).count());
        //集成度
        model.addAttribute("score1",33);
        //融合度
        model.addAttribute("score2",66);
        //组合度
        model.addAttribute("score3",77);
        //活跃度
        model.addAttribute("score4",88);
        //应用效果
        model.addAttribute("score5",98);
        return "projectInfo";
    }

    @RequestMapping("/project/{projectID}/log.html")
    public String viewProjectLog(Model model,@PathVariable long projectID) {
        addProjectLog(model,logService.getProjectLog(projectID));
        return "projectInfo";
    }

    private void addProjectLog(Model model,List<ProjectLog> projectLogs){
        List<ProjectLog> todayActivity = new ArrayList<>();
        List<ProjectLog> yesterdayActivity = new ArrayList<>();
        List<ProjectLog> beforeActivity = new ArrayList<>();
        Calendar targetCalendar = Calendar.getInstance();
        System.out.println(targetCalendar);
        targetCalendar.setTime(new Date());
        targetCalendar.set(Calendar.HOUR, 0);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        Calendar dateCalendar = Calendar.getInstance();
        System.out.println(dateCalendar);
        projectLogs.forEach(projectLog -> {
            dateCalendar.setTime(projectLog.getOperateDate());
            System.out.println(dateCalendar);
            if (dateCalendar.after(targetCalendar)) {
                todayActivity.add(projectLog);
            } else {
                targetCalendar.add(Calendar.DATE, -1);
                if (dateCalendar.after(targetCalendar)) {
                    yesterdayActivity.add(projectLog);
                } else {
                    beforeActivity.add(projectLog);
                }
            }
        });
        model.addAttribute("todayActivity",todayActivity);
        model.addAttribute("yesterdayActivity",yesterdayActivity);
        model.addAttribute("beforeActivity",beforeActivity);
    }

    /**
     * 查看项目成员
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/user.html")
    public String viewProjectUser(Model model,@PathVariable long projectID) {
        JSONArray jsonArray = new JSONArray(Arrays.stream(ProjectRoleType.values())
                .filter(projectRoleType -> {
                    if(projectRoleType==ProjectRoleType.CREATOR)
                        return false;
                    switch (user.getProjectRoleType()){
                        case CREATOR:
                            return true;
                        case SUPER_MANAGER:
                            return projectRoleType!=ProjectRoleType.SUPER_MANAGER;
                        default:
                            return false;
                    }
                })
                .map(projectRoleType -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value",projectRoleType.toString());
                    jsonObject.put("text",projectRoleType.getName());
                    return jsonObject;
                }).collect(Collectors.toList()) );
        model.addAttribute("projectRoleMap",jsonArray.toJSONString());
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
    public String viewProjectNoAccess() {
        return "noAccess";
    }

    @RequestMapping("/project/noResource.html")
    public String viewProjectNoResource() {
        return "noProject";
    }


    private void addProject2Model(Model model, long projectID) {
        Project project = projectService.getProjectByID(projectID);
        model.addAttribute("project", project);
    }

}
