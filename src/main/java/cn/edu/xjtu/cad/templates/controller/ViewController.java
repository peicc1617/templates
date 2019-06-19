package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.config.API;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.model.log.Log;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResultState;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import cn.edu.xjtu.cad.templates.service.EvaService;
import cn.edu.xjtu.cad.templates.service.LogService;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import cn.edu.xjtu.cad.templates.service.ReferService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
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

    @Autowired
    ReferService referService;

    @Autowired
    EvaService evaService;

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

    @RequestMapping("/")
    public ModelAndView viewIndex(){
        ModelAndView modelAndView = new ModelAndView("redirect:project/my.html");
        return modelAndView;
    }

    /**
     * 查看新建项目页面
     *
     * @return
     */
    @RequestMapping("/project/new.html")
    public String viewNewProject(Model model, Long referID, String problemID) {
        if(referID==null){
            referID=0L;

        }else {
            model.addAttribute("referName",   referID+"-"+referService.getRefer(referID).getReferName());
        }
        if(problemID==null||problemID.length()==0){
            problemID="0";
        }
        model.addAttribute("referID",referID);
        model.addAttribute("problemID",problemID);
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
     * LCUE 跳转到ProblemID绑定的项目监控页面
     * @param problemID
     * @return
     */
    @RequestMapping("/project")
    public ModelAndView viewProjectInfoByProblemID(String problemID){
        Project project = projectService.getProjectByProblemID(problemID);
        ModelAndView model = null;
        if(project==null){
            model = new ModelAndView("redirect:/project/noResource.html");//默认forward，可以不用写
        }else {
            model = new ModelAndView("redirect:/project/"+project.getProjectID()+"/info.html");//默认forward，可以不用写

        }
        return model;
    }


    /**
     * LCUE 跳转到项目页面，如果项目为空，则跳转到创建项目，如果项目不为空，查看项目
     * @param referID
     * @param problemID
     * @return
     */
    @RequestMapping("/project/problem.html")
    public ModelAndView viewProjectByProblemID(Long referID,String problemID){
        Project project = projectService.getProjectByProblemID(problemID);
        ModelAndView model = null;
        if(project==null){
            //如果 项目为空，那么跳转到新建项目页面
            model = new ModelAndView("redirect:/project/new.html?referID="+referID+"&problemID="+problemID);//默认forward，可以不用写
        }else {
            //如果项目不为空，那么跳转查看项目页面
            model = new ModelAndView("redirect:/project/"+project.getProjectID()+"/view.html");//默认forward，可以不用写

        }
        return model;
    }

    /**
     * 查看项目信息
     *
     * @param projectID
     * @return
     */
    @RequestMapping("/project/{projectID}/info.html")
    public String viewProjectInfo(Model model,@PathVariable long projectID) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ProjectLog> logs = logService.getProjectLogCut(projectID,1000);
        addProjectLog(model,logs);
        Map<String,List<ProjectLog>> dayLog = new HashMap<>();
        for (ProjectLog log : logs) {
            String day = simpleDateFormat.format(log.getOperateDate());
            if(!dayLog.containsKey(day)){
                dayLog.put(day,new ArrayList<>());
            }
            dayLog.get(day).add(log);
        }
        List<JSONObject> calendarData = dayLog.entrySet().stream().map(e->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title",e.getValue().size()+"个活动");
            jsonObject.put("start",e.getKey());
            jsonObject.put("allDay", true);
            jsonObject.put("className","label-info");
            return jsonObject;
        }).collect(Collectors.toList());
        String today = simpleDateFormat.format(new Date());
        List<ProjectLog> todayLog = dayLog.getOrDefault(today,new ArrayList<>());

        model.addAttribute("calendarData",JSON.toJSONString(calendarData));
        model.addAttribute("dayLog",JSON.toJSONString(dayLog));
        model.addAttribute("todayLog",todayLog);
        Project project = (Project) model.asMap().get("project");
        //成员个数
        model.addAttribute("memberCnt",project.getMembers().size());
        //阶段个数
        model.addAttribute("stepCnt",project.getStepMap().size());
        //节点个数
        model.addAttribute("nodeCnt",project.getNodeMap().size());
        //创新方法个数
        model.addAttribute("appCnt",project.getNodeMap().values().stream().filter(node -> StringUtils.isEmpty(node.getAppPath())).count());

        //活跃度
        model.addAttribute("activity",logs.size());


        if(project.getStartTime()!=null){
            String process = JSON.toJSONString(project.getNodeMap()
                    .entrySet()
                    .stream()
                    .map(e->e.getValue())
                    .sorted((n1,n2)->Comparators.comparable().compare(n2.getPlanStartTime(),n1.getPlanStartTime()))
                    .collect(Collectors.toList()));
            model.addAttribute("processStatic",process );
        }
        return "projectInfo";
    }

    /**
     * 查看项目的评估页面
     * @return
     */
    @RequestMapping("/project/{projectID}/eva.html")
    public String viewProjectEva(Model model,@PathVariable long projectID){
        //从视图中获取当前项目
//        Project project = (Project) model.asMap().get("project");
        //获取项目预定义的指标
//        model.addAttribute("projectIndex",projectService.getProjectIndex(project));

        //获取评估体系列表，同时填充评估体系内的指标列表
        List<Eva> evaList = evaService.getProjectEvaList(projectID,user);
        //获取所有的指标及当前值
        Map<Long,EvaIndex> evaIndexMap = evaService.getIndexList(projectID,user)
                .stream()
                .collect(Collectors.toMap(index->index.getIndexID(), Function.identity()));

        //设置评估体系的结果
        evaList.forEach(eva -> {
            List<EvaIndex> evaIndexList =  eva.getEvaIndexList();
            if(evaIndexList!=null&&evaIndexList.size()>0){
                //设置评估指标体系的指标的标准值
                evaIndexList.forEach(evaIndex -> {
                    //获取指标值
                    double res = evaIndexMap.get(evaIndex.getIndexID()).getRes();
                    //对值进行标准化
                    double nRes = (res-evaIndex.getRangeL())/(evaIndex.getRangeR()-evaIndex.getRangeL());
                    evaIndex.setRes(nRes);
                });
                //根据标准值计算加权平均结果
                double resSum =evaIndexList.stream()
                        .mapToDouble(evaIndex ->
                                evaIndex.getRes()*evaIndex.getW())
                        .sum();
                double wSum =evaIndexList.stream().mapToDouble(evaIndex -> evaIndex.getW()).sum();
                double res = resSum/wSum;
                eva.setRes(res);
            }

        });
        model.addAttribute("evaList",evaList);
        model.addAttribute("evaListJSONString",JSON.toJSONString(evaList));
        model.addAttribute("indexList",JSON.toJSONString(evaIndexMap.values()));
        return "projectEva";
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
        targetCalendar.setTime(new Date());
        targetCalendar.set(Calendar.HOUR, 0);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        Calendar dateCalendar = Calendar.getInstance();
        projectLogs.forEach(projectLog -> {
            dateCalendar.setTime(projectLog.getOperateDate());
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

    @RequestMapping("/eva/{evaID}/edit.html")
    public String viewEva(Model model,@PathVariable long evaID){
        Eva eva = evaService.getEvaByID(evaID,user);
        model.addAttribute("eva",eva);

        return "eva";
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
