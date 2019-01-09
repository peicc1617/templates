package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.node.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeMapper {
    long addAllNodes(List<Node> nodeList);

    long addNode(Node  node);

    long deleteNode(@Param("projectID") long projectID,@Param("nodeIndex")  String nodeIndex);

    long updateNode(Node node);

    List<Node> getNodesOfProject(long projectID);

    Node getNode(@Param("projectID") long projectID,@Param("nodeIndex") String nodeIndex);
}
