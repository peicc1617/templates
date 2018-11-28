package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.node.NodeResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeResultMapper {

    boolean addAllNodeResultList(List<NodeResult> resultList);
}
