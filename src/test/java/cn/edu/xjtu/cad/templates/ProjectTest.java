package cn.edu.xjtu.cad.templates;

import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.Step;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProjectTest {

    @Autowired
    StepMapper stepMapper;
    @Test
    public void TestAddAllProject(){
        List<Step> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Step(0,i,i+"",i+""));
        }
        stepMapper.addAllSteps(list);
    }
}
