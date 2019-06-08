package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.Step;
import cn.edu.xjtu.cad.templates.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/project/{projectID}/step")
public class StepController {

    @Autowired
    User user;

    @Autowired
    StepMapper stepMapper;
    @Autowired
    StepService stepService;

    @SystemControllerLog(content = "新建阶段，阶段名${stepName},阶段描述${stepDesc}",logType = LogType.STEP,methodType = MethodType.ADD)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean addStep(Step step){
        stepMapper.addStep(step);
        return true;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<List> getActivation(@PathVariable long projectID, String stepIndex){
        return stepService.getActivation(projectID,stepIndex);

    }

    @SystemControllerLog(content = "删除阶段${stepIndex}",logType = LogType.STEP,methodType = MethodType.DELETE)
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteStep(@PathVariable long projectID,String stepIndex){
        stepMapper.deleteStep(projectID,stepIndex);
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的名称修改为${stepName}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/stepName",method = RequestMethod.PUT)
    public boolean updateStepName(@PathVariable long projectID,String stepIndex,String stepName){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setStepName(stepName);
            stepMapper.updateStep(step);
        }
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的描述修改为${stepDesc}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/stepDesc",method = RequestMethod.PUT)
    public boolean updateStepDes(@PathVariable long projectID,String stepIndex,String stepDesc){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setStepDesc(stepDesc);
            stepMapper.updateStep(step);

        }
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的总结修改为${summary}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/summary",method = RequestMethod.PUT)
    public boolean updateStep(@PathVariable long projectID,String stepIndex,String summary){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setSummary(summary);
            stepMapper.updateStep(step);
        }
        return true;
    }
}
