package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeResultMapper {

    boolean addAllNodeResultList(List<NodeResult> resultList);

    @Select("SELECT * FROM project_node_result where projectID = #{id} AND nodeIndex = #{index}")
    List<NodeResult> getResultListByNodeIndex(@Param("id") int projectID,@Param("index")String nodeIndex);

    List<NodeResult> getResultListByNodeIndexAndUserName(@Param("id")int projectID,@Param("index") String nodeIndex,@Param("list") List<String> list);

    @Select("SELECT * FROM project_node_result where projectID = #{id} AND nodeIndex = #{index} AND username = #{name}")
    NodeResult getResult(@Param("id")int projectID,@Param("index") String nodeIndex,@Param("name") String curName);

    boolean updateNodeResult(NodeResult nodeResult);
}
