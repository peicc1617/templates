package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Edge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.xml.soap.Node;
import java.util.List;

@Repository
public interface EdgeMapper {
    /**
     * 将边都保存到数据库
     * @param edgeList
     * @return
     */
    void addAllEdges(List<Edge> edgeList);

    /**
     * 从数据库中查询边
     * @param projectID
     * @return
     */
    List<Edge> getEdgesOfProject(long projectID);


    void deleteEdgeOfNode(long projectID,String nodeIndex);


    void deleteEdge(@Param("projectID") long projectID,@Param("nodeI")  String nodeI,@Param("nodeJ")  String nodeJ);
}
