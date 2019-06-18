package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.EvaMapper;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    EvaMapper evaMapper;

    /**
     * 修改评价指标体系内的具体指标
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

    public void deleteEvaIndex(long evaID, long indexID, User user) {
        evaMapper.deleteEvaIndex(indexID);
    }

    public void refreshEvaIndexRange(long indexID, User user) {

    }
}
