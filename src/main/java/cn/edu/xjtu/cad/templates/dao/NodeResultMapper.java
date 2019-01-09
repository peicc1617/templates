package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeResultMapper {

    /**
     * 将节点结果列表添加到数据库
     * @param resultList
     * @return
     */
    long addAllNodeResultList(List<NodeResult> resultList);

    /**
     * 根据节点index查询结果
     * @param projectID 项目ID
     * @param nodeIndex 节点index
     * @return 返回结果列表
     */
    List<NodeResult> getResultListByNodeIndex(@Param("id") long projectID,@Param("index")String nodeIndex);

    /**
     * 根据节点index和多个用户ID查询结果
     * @param projectID 项目ID
     * @param nodeIndex 节点index
     * @param userIDs 用户ID列表
     * @return 返回结果列表
     */
    List<NodeResult> getResultListByNodeIndexAndUserIDList(@Param("id")long projectID,@Param("index") String nodeIndex,@Param("list") List<Long> userIDs);

    /**
     * 更新多个节点结果
     * @param nodeResultList
     * @return 成功更新的行数
     */
    long updateNodeResultList(List<NodeResult> nodeResultList);
}
