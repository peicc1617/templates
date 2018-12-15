package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 创建新的节点
     * @param node 节点对象
     * @return
     */
    @SystemControllerLog(content = "创建新节点，节点名为${name},节点描述为${description},节点目标为${goal}",logType = LogType.NODE,methodType = MethodType.ADD)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Node addNode(Node node){
//        String curUsername = ((Map<String, String>) req.getSession().getAttribute("userInfo")).get("username");
        nodeMapper.addNode(node);
        String creator  = projectRoleMapper.getCreatorOfProject(node.getProjectID(),ProjectRoleType.CREATOR);
        NodeRole nodeRole = new NodeRole(node.getProjectID(),node.getNodeIndex(),creator,NodeRoleType.MANAGER);
        nodeRoleMapper.addNodeRole(nodeRole);
        return node;
    }

    /**
     * 删除节点
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}删除",logType = LogType.NODE,methodType = MethodType.DELETE)
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public boolean deleteNode(int projectID,String nodeIndex){
        nodeMapper.deleteNode(projectID,nodeIndex);
        return true;
    }

    /**
     * 更新节点名称
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param name 新名称
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的名称修改为${name}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "name",method = RequestMethod.PUT)
    public boolean updateNodeName(int projectID,String nodeIndex,String name){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setName(name);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 更新节点描述
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param description 新描述
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的描述修改为${description}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "description",method = RequestMethod.PUT)
    public boolean updateNodeDes(int projectID,String nodeIndex,String description){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setDescription(description);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 更新节点目标
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param goal 新的节点目标
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的目标修改为${goal}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "goal",method = RequestMethod.PUT)
    public boolean updateNodeGoal(int projectID,String nodeIndex,String goal){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setGoal(goal);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 更新节点总结
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param summary 新的节点总结
     * @return
     */
    @SystemControllerLog(content = "填写了节点总结，总结内容为${summary}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "summary",method = RequestMethod.PUT)
    public boolean updateNodeReview(int projectID,String nodeIndex,String summary){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setSummary(summary);
            nodeMapper.updateNode(node);
        }
        return true;
    }


    /**
     * 更新节点绑定的APP
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param appName app名称
     * @param appPath app的路径
     * @param appIcon app的图标
     * @return
     */
    @SystemControllerLog(content = "节点绑定了${appName}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "app",method = RequestMethod.PUT)
    public boolean updateNodeApp(int projectID,String nodeIndex,String appName,String appPath,String appIcon){
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

    /**
     * 更新节点的状态
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param state 节点的新状态
     * @return
     */
    @SystemControllerLog(content = "将节点状态更改为${state}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "state",method = RequestMethod.PUT)
    public boolean updateNodeState(int projectID,String nodeIndex,boolean state){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setState(state);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    @RequestMapping(value = "lock",method = RequestMethod.PUT)
    public boolean updateNodeLock(int projectID,String nodeIndex,boolean lockState){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setLockState(lockState);
            nodeMapper.updateNode(node);
        }
        return true;
    }


    /**
     * 更新节点所属的阶段
     * @param projectID 项目ID
     * @param nodeIndex 节点项目
     * @param stepIndex 新的节点所属的阶段
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}所属阶段修改为${stepIndex}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "step",method = RequestMethod.PUT)
    public boolean updateNodeStep(int projectID,String nodeIndex,String stepIndex){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setStepIndex(stepIndex);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 更新节点所属的项目ID
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param templateProjectID 链接的项目ID
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}和模板项目${templateProjectID}进行绑定",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "template",method = RequestMethod.PUT)
    public boolean updateNodeTemplate(int projectID,String nodeIndex,int templateProjectID){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setTemplateProjectID(templateProjectID);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 获取当前节点的所有结果数据
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @return
     */
    @RequestMapping(value = "result",method = RequestMethod.GET)
    public List<NodeResult> getResultListOfNodeIndex(int projectID,String nodeIndex){
        return nodeResultMapper.getResultListByNodeIndex(projectID,nodeIndex);
    }

    /**
     * 发送失效信息
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param usernameList 用户名称List
     * @return
     */
    @SystemControllerLog(content = "处于节点${nodeIndex}下的属于用户${usernameList}的数据已经失效",logType = LogType.RESULT,methodType = MethodType.UPDATE)
    @RequestMapping(value = "result/list/disable",method = RequestMethod.PUT)
    public List<NodeResult> disableResult(int projectID,String nodeIndex,@RequestParam("usernameList")List<String> usernameList){
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserName(projectID,nodeIndex,usernameList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResult.UN_BINGDINGS);
            nodeResult.setMessage(NodeResult.DISABLE_MESSAGE);
            nodeResultMapper.updateNodeResult(nodeResult);
        });
        return nodeResultList;
    }

    /**
     * 发送结果更新信息
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param usernameList 用户名称列表
     * @return
     */
    @SystemControllerLog(content = "处于节点${nodeIndex}下的属于用户${usernameList}的数据已经更新",logType = LogType.RESULT,methodType = MethodType.DELETE)
    @RequestMapping(value = "result/list/outDate",method = RequestMethod.PUT)
    public List<NodeResult> outDateResult(int projectID,String nodeIndex,@RequestParam("usernameList")List<String> usernameList){
        List<NodeResult> nodeResultList = nodeResultMapper.getResultListByNodeIndexAndUserName(projectID,nodeIndex,usernameList);
        nodeResultList.forEach(nodeResult -> {
            nodeResult.setState(NodeResult.PENDING);
            nodeResult.setMessage(NodeResult.EDIT_MESSAGE);
            nodeResultMapper.updateNodeResult(nodeResult);
        });
        return nodeResultList;
    }


    /**
     * 解绑数据
     * @param projectID 项目名称
     * @param nodeIndex 节点索引
     * @return
     */
    @SystemControllerLog(content = "解绑了自己的处于节点${nodeIndex}下的数据",logType = LogType.RESULT,methodType = MethodType.DELETE)
    @RequestMapping(value = "result/unbinding",method = RequestMethod.PUT)
    public NodeResult unbindingResult(int projectID,String nodeIndex){
        NodeResult nodeResult = nodeResultMapper.getResult(projectID,nodeIndex,curName);
        nodeResult.setState(NodeResult.UN_BINGDINGS);
        nodeResult.setMessage("");
        nodeResult.setResultKey("");
        nodeResult.setResultID(0);
        nodeResult.setResultName("");
        nodeResultMapper.updateNodeResult(nodeResult);
        return nodeResult;
    }

    /**
     * 绑定数据
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param resultKey 结果key
     * @param resultID 结果ID
     * @param resultName 结果名
     * @return
     */
    @SystemControllerLog(content = "将自己的数据${resultName}绑定在节点${nodeIndex}下",logType = LogType.RESULT,methodType = MethodType.UPDATE)
    @RequestMapping(value = "result/binding",method = RequestMethod.PUT)
    public NodeResult bindingResult(int projectID,String nodeIndex,String resultKey,int resultID,String resultName){
        NodeResult nodeResult = nodeResultMapper.getResult(projectID,nodeIndex,curName);
        nodeResult.setResultKey(resultKey);
        nodeResult.setResultID(resultID);
        nodeResult.setResultName(resultName);
        nodeResult.setState(NodeResult.PENDING);
        nodeResultMapper.updateNodeResult(nodeResult);
        return nodeResult;
    }

    /**
     * 更新数据的状态
     * @param nodeResult 节点结果
     * @return
     */
    @SystemControllerLog(content = "修改了绑定数据${resultName}的状态为${state}",logType = LogType.RESULT,methodType = MethodType.UPDATE)
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
