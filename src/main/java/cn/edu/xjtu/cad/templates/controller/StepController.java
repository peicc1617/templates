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


    @RequestMapping(value = "name",method = RequestMethod.PUT)
    public boolean updateStepName(int projectID,int stepIndex,String name){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setName(name);
            stepMapper.updateStep(step);
        }
        return true;
    }

    @RequestMapping(value = "description",method = RequestMethod.PUT)
    public boolean updateStepDes(int projectID,int stepIndex,String description){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setDescription(description);
            stepMapper.updateStep(step);
        }
        return true;
    }

    @RequestMapping(value = "summary",method = RequestMethod.PUT)
    public boolean updateStep(int projectID,int stepIndex,String summary){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setSummary(summary);
            stepMapper.updateStep(step);
        }
        return true;
    }
}
