package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.EvaMapper;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IndexService {
    @Autowired
    EvaMapper evaMapper;

    /**
     * 修改评价指标体系内的具体指标
     *
     * @param evaIndex
     * @param user
     */
    public void updateEvaIndex(EvaIndex evaIndex, User user) {
        evaMapper.updateEvaIndex(evaIndex);
    }

    public long addEvaIndex(EvaIndex evaIndex, User user) {
        evaMapper.addEvaIndex(evaIndex);
        return evaIndex.getIndexID();
    }

    public void deleteEvaIndex(long indexID, User user) {
        evaMapper.deleteEvaIndex(indexID);
    }

    public void refreshEvaIndexRange(long indexID, User user) {

    }

    /**
     * 获取所有的指标
     *
     * @param evaID
     * @param user
     * @return
     */
    public List<EvaIndex> getAllEvaIndex(long evaID, User user) {
        Set<Long> set = evaMapper.getEvaIndexByEvaID(evaID)
                .stream()
                .map(evaIndex ->
                        evaIndex.getIndexID())
                .collect(Collectors.toSet());
        List<EvaIndex> evaIndexList = evaMapper.getAllEvaIndex();
        return evaIndexList
                .stream()
                .filter(evaIndex ->
                        !set.contains(evaIndex.getIndexID()))
                .collect(Collectors.toList());
    }


}
