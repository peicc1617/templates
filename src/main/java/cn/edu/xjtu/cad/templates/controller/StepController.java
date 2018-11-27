package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.model.project.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class StepController {
    @Autowired
    HttpServletRequest req;

    @RequestMapping
    public boolean addStep(Step step){
        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        return true;
    }
}
