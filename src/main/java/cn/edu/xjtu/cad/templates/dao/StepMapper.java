package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Step;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepMapper {

    boolean addAllSteps(List<Step> stepList);

    boolean addStep(Step step);

    boolean deleteStep(int projectID,int stepIndex);

    boolean updateStep(Step step);

    List<Step> getStepsOfProject(int projectID);

    Step getStep(int projectID,int stepIndex);

}
