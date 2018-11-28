package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.node.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeMapper {
    boolean addAllNodes(List<Node> nodeList);

    int addNode(Node  node);

    boolean deletNode(int projectID,int nodeIndex);

    boolean updateNode(Node node);

    @Select("SELECT * FROM project_node WHERE projectID = #{projectID}")
    List<Node> getNodesOfProject(int projectID);

    @Select("SELECT * FROM project_node WHERE projectID = #{projectID} AND nodeIndex = #{nodeIndex}")
    Node getNode(@Param("projectID") int projectID,@Param("nodeIndex") int nodeIndex);
}
