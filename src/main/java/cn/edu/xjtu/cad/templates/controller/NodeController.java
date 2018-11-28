package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.NodeMapper;
import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.project.Step;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/project/node")
public class NodeController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    NodeMapper nodeMapper;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean addNode(Node node){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        nodeMapper.addNode(node);
        return true;
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteNode(int projectID,int nodeIndex){
        nodeMapper.deletNode(projectID,nodeIndex);
        return true;
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public boolean updateNode(Node node){
        nodeMapper.updateNode(node);
        return true;
    }


}
