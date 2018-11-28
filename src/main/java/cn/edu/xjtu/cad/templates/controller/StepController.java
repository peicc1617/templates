package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.project.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/project/step")
public class StepController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    StepMapper stepMapper;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean addStep(Step step){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        stepMapper.addStep(step);
        return true;
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteStep(int projectID,int stepIndex){
        stepMapper.deleteStep(projectID,stepIndex);
        return true;
    }


    @RequestMapping(value = "",method = RequestMethod.PUT)
    public boolean updateStep(Step step){
        stepMapper.updateStep(step);
        return true;
    }
}
