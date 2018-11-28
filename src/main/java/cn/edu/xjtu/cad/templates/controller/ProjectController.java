package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.ProjectService;
import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.ProjectRoleMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    ProjectService projectService;
    String curUsername = "111";

   @Autowired
    ProjectRoleMapper projectRoleMapper;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Project> getProjectListByUsername(){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        List<Project> projectList = projectMapper.getProjectListByUsername(curUsername);
        return projectList;
    }
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Project getProjectByProjectID(int projectID){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        return  projectService.getProjectByID(projectID);

    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean createProject(Project project){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        projectService.wrapProject(project,curUsername);
        return true;
    }

    @RequestMapping(value = "/name",method = RequestMethod.PUT)
    public int updateProjectName(String projectName,int projectID){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if(project==null){
            res = 1;
        }else {
            project.setName(projectName);
            projectMapper.updateProjectInfo(project);
        }
        return 0;
    }

    @RequestMapping(value = "/description",method = RequestMethod.PUT)
    public int updateProjectDes(String projectDescription,int projectID){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if(project==null){
            res = 1;
        }else {
            project.setDescription(projectDescription);
            projectMapper.updateProjectInfo(project);
        }
        return 0;

    }

    @RequestMapping(value = "/tags",method = RequestMethod.PUT)
    public int updateProjectTags(String projectTags,int projectID){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        int res = 0;
        Project project = projectMapper.getProjectByID(projectID);
        if(project==null){
            res = 1;
        }else {
            project.setTags(projectTags);
            projectMapper.updateProjectInfo(project);
        }
        return res;
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteProject(int projectID){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
         projectMapper.deleteProject(projectID);
         return true;
    }

    @RequestMapping(value = "role",method = RequestMethod.GET)
    public List<ProjectRole> getProjectRoleByID(int projectID){
        return projectRoleMapper.getRoleOfProject(projectID);
    }

    @RequestMapping(value = "member/role",method = RequestMethod.GET)
    public ProjectRole getProjectRole(int projectID,String username){
        return projectRoleMapper.getMemberRoleOfProject(projectID,username);
    }

    @RequestMapping(value = "role",method = RequestMethod.PUT)
    public boolean updateProjectRole(ProjectRole projectRole){
        projectRoleMapper.updateProjectRole(projectRole);
        return true;
    }

    @RequestMapping(value = "role",method = RequestMethod.POST)
    public boolean addProjectRole(ProjectRole projectRole){
        projectRoleMapper.addProjectRole(projectRole);
        return true;
    }

    @RequestMapping(value = "role",method = RequestMethod.DELETE)
    public boolean deleteProjectRole(int projectID, String username){
        projectRoleMapper.deleteProjectRole(projectID,username);
        return true;
    }


}
