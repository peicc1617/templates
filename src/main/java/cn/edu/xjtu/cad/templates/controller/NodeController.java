package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.Step;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/api/project/node")
public class NodeController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    ProjectRoleMapper projectRoleMapper;
    @Autowired
    NodeResultMapper nodeResultMapper;

    @Autowired
    NodeRoleMapper nodeRoleMapper;

    String curName = "111";
    @RequestMapping(value = "",method = RequestMethod.POST)
    public boolean addNode(Node node){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        nodeMapper.addNode(node);
        String creator  = projectRoleMapper.getCreatorOfProject(node.getProjectID(),ProjectRole.CREATOR);
        NodeRole nodeRole = new NodeRole(node.getProjectID(),node.getNodeIndex(),creator,NodeRole.MANAGER);
        nodeRoleMapper.addNodeRole(nodeRole);
        return true;
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteNode(int projectID,int nodeIndex){
        nodeMapper.deleteNode(projectID,nodeIndex);
        return true;
    }

    @RequestMapping(value = "name",method = RequestMethod.PUT)
    public boolean updateNodeName(int projectID,int nodeIndex,String name){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setName(name);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "description",method = RequestMethod.PUT)
    public boolean updateNodeDes(int projectID,int nodeIndex,String description){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setDescription(description);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "goal",method = RequestMethod.PUT)
    public boolean updateNodeGoal(int projectID,int nodeIndex,String goal){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setGoal(goal);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "summary",method = RequestMethod.PUT)
    public boolean updateNodeReview(int projectID,int nodeIndex,String summary){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setSummary(summary);
            nodeMapper.updateNode(node);
        }
        return true;
    }


    @RequestMapping(value = "app",method = RequestMethod.PUT)
    public boolean updateNodeApp(int projectID,int nodeIndex,String appName,String appPath,String appIcon){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setAppName(appName);
            node.setAppPath(appPath);
            node.setAppIcon(appIcon);
            node.setTemplateProjectID(0);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "state",method = RequestMethod.PUT)
    public boolean updateNodeState(int projectID,int nodeIndex,boolean state){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setState(state);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "lock",method = RequestMethod.PUT)
    public boolean updateNodeLock(int projectID,int nodeIndex,boolean lockState){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setLockState(lockState);
            nodeMapper.updateNode(node);
        }
        return true;
    }


    @RequestMapping(value = "step",method = RequestMethod.PUT)
    public boolean updateNodeStep(int projectID,int nodeIndex,int stepIndex){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setStepIndex(stepIndex);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "template",method = RequestMethod.PUT)
    public boolean updateNodeTemplate(int projectID,int nodeIndex,int templateProjectID){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setTemplateProjectID(templateProjectID);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "result",method = RequestMethod.GET)
    public List<NodeResult> getResultListOfNodeint(int projectID,int nodeIndex){
        return nodeResultMapper.getResultListByNodeIndex(projectID,nodeIndex);
    }

    @RequestMapping(value = "result/list/disable",method = RequestMethod.PUT)
    public List<NodeResult> disableResult(int projectID,int nodeIndex,@RequestParam("usernameList")List<String> usernameList){
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserName(projectID,nodeIndex,usernameList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResult.UN_BINGDINGS);
            nodeResult.setMessage(NodeResult.DISABLE_MESSAGE);
            nodeResultMapper.updateNodeResult(nodeResult);
        });
        return nodeResultList;
    }

    @RequestMapping(value = "result/list/outDate",method = RequestMethod.PUT)
    public List<NodeResult> outDateResult(int projectID,int nodeIndex,@RequestParam("usernameList")List<String> usernameList){
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserName(projectID,nodeIndex,usernameList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResult.PENDING);
            nodeResult.setMessage(NodeResult.EDIT_MESSAGE);
            nodeResultMapper.updateNodeResult(nodeResult);
        });
        return nodeResultList;
    }
    @RequestMapping(value = "result/unbinding",method = RequestMethod.PUT)
    public NodeResult unbindingResult(int projectID,int nodeIndex){
        NodeResult nodeResult = nodeResultMapper.getResult(projectID,nodeIndex,curName);
        nodeResult.setState(NodeResult.UN_BINGDINGS);
        nodeResult.setMessage("");
        nodeResult.setResultKey("");
        nodeResult.setResultID(0);
        nodeResult.setResultName("");
        nodeResultMapper.updateNodeResult(nodeResult);
        return nodeResult;
    }

    @RequestMapping(value = "result/binding",method = RequestMethod.PUT)
    public NodeResult bindingResult(int projectID,int nodeIndex,String resultKey,int resultID,String resultName){
        NodeResult nodeResult = nodeResultMapper.getResult(projectID,nodeIndex,curName);
        nodeResult.setResultKey(resultKey);
        nodeResult.setResultID(resultID);
        nodeResult.setResultName(resultName);
        nodeResult.setState(NodeResult.PENDING);
        nodeResultMapper.updateNodeResult(nodeResult);
        return nodeResult;
    }

    @RequestMapping(value = "result",method = RequestMethod.PUT)
    public boolean updateStateOfResult(NodeResult nodeResult){
        switch (nodeResult.getState()){
            case NodeResult.ACCEPT:
                nodeResult.setMessage(NodeResult.PASSED_MESSAGE);
                break;
            case NodeResult.PENDING:
                nodeResult.setMessage(NodeResult.APPLY_MESSAGE);
                break;
            case NodeResult.TO_MODIFIED:
                nodeResult.setMessage(NodeResult.TO_EDIT_MESSAGE);
                break;
            case NodeResult.UN_BINGDINGS:
                nodeResult.setMessage(NodeResult.PASSED_MESSAGE);
                nodeResult.setMessage("");
                nodeResult.setResultKey("");
                nodeResult.setResultID(0);
                nodeResult.setResultName("");
                break;
        }
        nodeResultMapper.updateNodeResult(nodeResult);
        return true;
    }




}
