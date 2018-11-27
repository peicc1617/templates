package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    ProjectMapper projectMapper;
    @RequestMapping(value = "/project",method = RequestMethod.GET)
    public List<Project> get(){
        List<Project> projectList = projectMapper.getStudentByID("111");
        return projectList;
    }

}
