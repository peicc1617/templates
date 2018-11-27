package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    ProjectMapper projectMapper;

    @RequestMapping(value = "/project",method = RequestMethod.GET)
    public List<Project> getProjectListByUsername(){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        List<Project> projectList = projectMapper.getProjectListByUsername(curUsername);
        return projectList;
    }

    @RequestMapping(value = "project",method = RequestMethod.POST)
    public boolean createProject(Project project){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        project.setCreator(curUsername);
        return true;
    }

    @RequestMapping(value = "/project/name",method = RequestMethod.PUT)
    public int updateProjectName(String projectName,int projectID){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByUsernameAndID(curUsername,projectID);
        if(project==null){
            res = 1;
        }else {
            project.setDescription(projectName);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    @RequestMapping(value = "/project/description",method = RequestMethod.PUT)
    public int updateProjectDes(String projectDescription,int projectID){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByUsernameAndID(curUsername,projectID);
        if(project==null){
            res = 1;
        }else {
            project.setDescription(projectDescription);
            projectMapper.updateProjectInfo(project);
        }
        return 0;

    }

    @RequestMapping(value = "/project/tags",method = RequestMethod.PUT)
    public int updateProjectTags(String tags,int projectID){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByUsernameAndID(curUsername,projectID);
        if(project==null){
            res = 1;
        }else {
            project.setTags(tags);
            projectMapper.updateProjectInfo(project);
        }
        return res;
    }

    @RequestMapping(value = "/project",method = RequestMethod.PUT)
    public boolean deleteProject(int projectID){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
         projectMapper.deleteProject(projectID);
         return true;
    }


}
