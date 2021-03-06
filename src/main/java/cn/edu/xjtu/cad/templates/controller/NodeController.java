package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.dao.*;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;
import cn.edu.xjtu.cad.templates.model.project.Edge;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.node.*;
import cn.edu.xjtu.cad.templates.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/project/{projectID}/node")
public class NodeController {

    @Autowired
    User user;

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

    @Autowired
    NodeService nodeService;

    /**
     * 创建新的节点
     * @param node 节点对象
     * @return
     */
    @SystemControllerLog(content = "创建新节点，节点名为${name},节点描述为${description},节点目标为${goal}",logType = LogType.NODE,methodType = MethodType.ADD)
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Node addNode(Node node){
        nodeMapper.addNode(node);
        long creatorUserID  = projectRoleMapper.getUserIDListInProjectByProjectRoleType(node.getProjectID(),ProjectRoleType.CREATOR).get(0);
        NodeRole nodeRole = new NodeRole(node.getProjectID(),node.getNodeIndex(),creatorUserID,NodeRoleType.MANAGER);
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
    public boolean deleteNode(@PathVariable long projectID, String nodeIndex){
        nodeMapper.deleteNode(projectID,nodeIndex);
        return true;
    }

    /**
     * 添加边
     * @param projectID
     * @param path
     */
    @RequestMapping(value = "/path",method = RequestMethod.POST)
    public void addPath(@PathVariable long projectID, Edge path){
        nodeService.addPath(user,path);
    }

    /**
     * 删除边
     * @param projectID
     * @param path
     */
    @RequestMapping(value = "/path",method = RequestMethod.DELETE)
    public void deletePath(@PathVariable long projectID, Edge path){
        nodeService.deletePath(user,path);
    }

    /**
     * 更新节点名称
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param nodeName 新名称
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的名称修改为${name}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/nodeName",method = RequestMethod.PUT)
    public boolean updateNodeName(@PathVariable long projectID,String nodeIndex,String nodeName){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setNodeName(nodeName);
            nodeMapper.updateNode(node);
        }
        return true;
    }

    /**
     * 更新节点描述
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param nodeDesc 新描述
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的描述修改为${description}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/nodeDesc",method = RequestMethod.PUT)
    public void updateNodeDes(@PathVariable long projectID,String nodeIndex,String nodeDesc){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setNodeDesc(nodeDesc);
            nodeMapper.updateNode(node);
        }
    }


    /**
     * 更新节点目标
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param goal 新的节点目标
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}的目标修改为${goal}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/goal",method = RequestMethod.PUT)
    public void updateNodeGoal(@PathVariable long projectID,String nodeIndex,String goal){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setGoal(goal);
            nodeMapper.updateNode(node);
        }
    }

    /**
     * 更新节点总结
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param summary 新的节点总结
     * @return
     */
    @SystemControllerLog(content = "填写了节点总结，总结内容为${summary}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/summary",method = RequestMethod.PUT)
    public void updateNodeReview(@PathVariable long projectID,String nodeIndex,String summary){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setSummary(summary);
            nodeMapper.updateNode(node);
        }
    }

    @SystemControllerLog(content = "修改了工作节点的工期，为${workTime}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/workTime",method = RequestMethod.PUT)
    public void updateNodeWorkTime(@PathVariable long projectID,String nodeIndex,int workTime,Date endTime){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setWorkTime(workTime);
            node.setEndTime(endTime);
            nodeMapper.updateNode(node);
        }
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
    @RequestMapping(value = "/app",method = RequestMethod.PUT  )
    public void updateNodeApp(@PathVariable long projectID,String nodeIndex,String appName,String appPath,String appIcon){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setAppName(appName);
            node.setAppPath(appPath);
            node.setAppIcon(appIcon);
            node.setTemplateProjectID(0);
            nodeMapper.updateNode(node);
        }
    }

    /**
     * 更新节点的状态
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param state 节点的新状态
     * @return
     */
    @SystemControllerLog(content = "将节点状态更改为${state}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/state",method = RequestMethod.PUT)
    public void updateNodeState(@PathVariable long projectID,String nodeIndex,boolean state){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setState(state);
            if(state){
                //如果状态是已经完成，那么设置完成时间
                node.setEndTime(new Date());
            }
            nodeMapper.updateNode(node);
        }
    }

    @RequestMapping(value = "/lock",method = RequestMethod.PUT)
    public void updateNodeLock(@PathVariable long projectID,String nodeIndex,boolean lockState){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setLockState(lockState);
            nodeMapper.updateNode(node);
        }
    }


    /**
     * 更新节点所属的阶段
     * @param projectID 项目ID
     * @param nodeIndex 节点项目
     * @param stepIndex 新的节点所属的阶段
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}所属阶段修改为${stepIndex}",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/step",method = RequestMethod.PUT)
    public void updateNodeStep(@PathVariable long projectID,String nodeIndex,String stepIndex){
        Node node = nodeMapper.getNode(projectID,nodeIndex);
        if(node!=null){
            node.setStepIndex(stepIndex);
            nodeMapper.updateNode(node);
        }
    }

    /**
     * 更新节点所属的项目ID
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param templateProjectID 链接的项目ID
     * @return
     */
    @SystemControllerLog(content = "将节点${nodeIndex}和模板项目${templateProjectID}进行绑定",logType = LogType.NODE,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/template",method = RequestMethod.PUT)
    public void updateNodeTemplate(@PathVariable long projectID,String nodeIndex,int templateProjectID){
        nodeService.updateNodeTemplate(projectID,nodeIndex,templateProjectID);
    }

    /**
     * 获取当前节点的所有结果数据
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    public List<NodeResult> getResultListOfNodeIndex(@PathVariable long projectID,String nodeIndex){
        return nodeService.getResultListByNodeIndex(projectID,nodeIndex);

    }

    /**
     * 获取当前用户在当前节点的结果数据
     * @param projectID
     * @param nodeIndex
     * @return
     */
    @RequestMapping(value = "/result/my",method = RequestMethod.GET)
    public NodeResult getMyResultOfNodeIndex(@PathVariable long projectID,String nodeIndex){
        return nodeService.getMyResultOfNodeIndex(projectID,nodeIndex,user);
    }


    /**
     * 发送失效信息
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param userIDList 用户名称List
     * @return
     */
    @SystemControllerLog(content = "处于节点${nodeIndex}下的属于用户${userID}的数据已经失效",logType = LogType.RESULT,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/result/list/disable",method = RequestMethod.PUT)
    public List<NodeResult> disableResult(@PathVariable long projectID,String nodeIndex,@RequestParam(value = "userIDList[]")List<Long> userIDList){
        return nodeService.disableResult(projectID,nodeIndex,userIDList);
    }

    /**
     * 发送结果更新信息
     * @param projectID 项目ID
     * @param nodeIndex 节点索引
     * @param userIDList 用户名称列表
     * @return
     */
    @SystemControllerLog(content = "处于节点${nodeIndex}下的属于用户${userID}的数据已经更新",logType = LogType.RESULT,methodType = MethodType.DELETE)
    @RequestMapping(value = "/result/list/outDate",method = RequestMethod.PUT)
    public List<NodeResult> outDateResult(@PathVariable long projectID,String nodeIndex,@RequestParam("userIDList[]")List<Long> userIDList){
        return nodeService.outDateResult(projectID,nodeIndex,userIDList);
    }


    /**
     * 解绑数据
     * @param projectID 项目名称
     * @param nodeIndex 节点索引
     * @return
     */
    @SystemControllerLog(content = "解绑了自己的处于节点${nodeIndex}下的数据",logType = LogType.RESULT,methodType = MethodType.DELETE)
    @RequestMapping(value = "/result/unbinding",method = RequestMethod.PUT)
    public NodeResult unbindingResult(@PathVariable long projectID,String nodeIndex){
        return nodeService.unbindingMyResult(projectID,nodeIndex,user);
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
    @RequestMapping(value = "/result/binding",method = RequestMethod.PUT)
    public NodeResult bindingResult(@PathVariable long projectID,String nodeIndex,String resultKey,int resultID,String resultName){
        return nodeService.bindMyResult(projectID,nodeIndex,resultKey,resultID,resultName,user);

    }

    /**
     * 更新数据的状态
     * @param nodeResult 节点结果
     * @return
     */
    @SystemControllerLog(content = "修改了绑定数据${resultName}的状态为${state}",logType = LogType.RESULT,methodType = MethodType.UPDATE)
    @RequestMapping(value = "/result",method = RequestMethod.PUT)
    public void updateStateOfResult(NodeResult nodeResult){
        nodeService.updateStateOfResult(nodeResult,user);
    }

    /**
     * 获取当前节点内的所有用户权限
     * @param projectID
     * @param nodeIndex
     */
    @RequestMapping(value = "/role/list",method = RequestMethod.GET)
    public List<NodeRole> getNodeRoleList(@PathVariable long projectID,String nodeIndex){
        return nodeService.getNodeRoleList(user,projectID,nodeIndex);
    }

    /**
     * 修改用户在节点内的权限
     * @param projectID
     * @param nodeRole
     */
    @RequestMapping(value = "/role",method = RequestMethod.PUT)
    public void NodeRole(@PathVariable long projectID,NodeRole nodeRole){
        nodeService.updateNodeRole(user,nodeRole);
    }


}
