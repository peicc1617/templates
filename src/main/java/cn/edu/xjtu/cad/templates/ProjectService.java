package cn.edu.xjtu.cad.templates;

import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.ReferMapper;
import cn.edu.xjtu.cad.templates.model.project.Edge;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.Refer;
import cn.edu.xjtu.cad.templates.model.project.Step;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.Node;
import java.io.FileFilter;
import java.sql.Ref;
import java.util.List;
import java.util.logging.Filter;

@Service("projectService")
public class ProjectService {


    @Autowired
    ReferMapper referMapper;
    @Autowired
    ProjectMapper projectMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public int createProjectWithRefer(Project project,String username) {
        Refer refer = referMapper.getReferByID(project.getReferID());

        List<Step> steps = JSONArray.parseArray(refer.getSteps(), Step.class);//解析模板中的阶段

        List<Node> nodes = JSONArray.parseArray(refer.getNodes(),Node.class);//解析模板中的节点

        List<Edge> edges = JSONArray.parseArray(refer.getEdges(),Edge.class);//解析模板中的边

        return 0;
    }



}
