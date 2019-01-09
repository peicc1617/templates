package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Step;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepMapper {


    int addAllSteps(List<Step> list);

    boolean addStep(Step step);

    boolean deleteStep(@Param("projectID")long projectID, @Param("stepIndex")String stepIndex);

    boolean updateStep(Step step);

    List<Step> getStepsOfProject(long projectID);

    Step getStep(@Param("projectID") long projectID, @Param("stepIndex") String stepIndex);

}
