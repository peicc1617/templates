package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/project/step")
public class StepController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    StepMapper stepMapper;

    @SystemControllerLog(content = "新建阶段，阶段名${name},阶段描述${description}",logType = LogType.STEP,methodType = MethodType.ADD)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean addStep(Step step){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        stepMapper.addStep(step);
        return true;
    }

    @SystemControllerLog(content = "删除阶段${stepIndex}",logType = LogType.STEP,methodType = MethodType.DELETE)
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteStep(int projectID,String stepIndex){
        stepMapper.deleteStep(projectID,stepIndex);
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的名称修改为${name}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "name",method = RequestMethod.PUT)
    public boolean updateStepName(int projectID,String stepIndex,String name){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setName(name);
            stepMapper.updateStep(step);
        }
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的描述修改为${description}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "description",method = RequestMethod.PUT)
    public boolean updateStepDes(int projectID,String stepIndex,String description){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setDescription(description);
            stepMapper.updateStep(step);
        }
        return true;
    }

    @SystemControllerLog(content = "将阶段${stepIndex}的总结修改为${summary}",logType = LogType.STEP,methodType = MethodType.UPDATE)
    @RequestMapping(value = "summary",method = RequestMethod.PUT)
    public boolean updateStep(int projectID,String stepIndex,String summary){
        Step step = stepMapper.getStep(projectID,stepIndex);
        if(step!=null){
            step.setSummary(summary);
            stepMapper.updateStep(step);
        }
        return true;
    }
}
