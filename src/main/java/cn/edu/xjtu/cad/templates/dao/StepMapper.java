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

    boolean deleteStep(int projectID,String stepIndex);

    boolean updateStep(Step step);

    @Select("SELECT * FROM project_step WHERE projectID = #{projectID} ORDER BY pos")
    List<Step> getStepsOfProject(int projectID);

    @Select("SELECT * FROM project_node  WHERE projectID = #{projectID} AND stepIndex = #{stepIndex}")
    Step getStep(@Param("projectID") int projectID, @Param("stepIndex") String stepIndex);

}
