package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Edge;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.xml.soap.Node;
import java.util.List;

@Repository
public interface EdgeMapper {
    boolean addAllEdges(List<Edge> edgeList);

    @Select("SELECT * FROM project_edge WHERE projectID = #{projectID}")
    List<Edge> getEdgesOfProject(int projectID);
}
