package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.NodeMapper;
import cn.edu.xjtu.cad.templates.dao.NodeResultMapper;
import cn.edu.xjtu.cad.templates.model.project.User;
import cn.edu.xjtu.cad.templates.model.project.node.Node;
import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    NodeResultMapper nodeResultMapper;


    public boolean updateNodeResult(@CurUser User user,NodeResult nodeResult){
        return updateNodeResultList(user,Collections.singletonList(nodeResult));
    }

    public boolean updateNodeResultList(@CurUser User user,List<NodeResult> nodeResultList){
        nodeResultMapper.updateNodeResultList(nodeResultList);
        return true;
    }

    public NodeResult getNodeResultByUserAndNodeIndex(@CurUser User user, long projectID, String nodeIndex){
        return nodeResultMapper.getResultListByNodeIndexAndUserIDList(projectID,nodeIndex,Collections.singletonList(user.getUserID())).get(0);
    }
}
