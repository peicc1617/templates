package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.EvaMapper;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EvaService {

    @Autowired
    EvaMapper evaMapper;

    /**
     * 创建新的评价体系
     * @param eva
     * @param user
     */
    public long addEva(Eva eva, User user) {
        eva.setCreator(user.getUserID());
         evaMapper.addEva(eva);
         return eva.getEvaID();
    }

    /**
     * 删除评价体系
     * @param evaID
     * @param user
     */
    public void deleteEva(long evaID, User user) {
        evaMapper.deleteEva(evaID);
    }

    /**
     * 获取用户可见的评价体系
     * @param user
     * @return
     */
    public List<Eva> getUserEvaList(User user) {
        List<Eva>  list = evaMapper.getUserEvaList(user.getUserID());
        list.forEach(eva->{
            List<EvaIndex> evaIndexList = evaMapper.getEvaIndexByEvaID(eva.getEvaID());
            eva.setEvaIndexList(evaIndexList);
        });
        return list;
    }

    /**
     * 编辑评价体系的信息
     * @param eva
     * @param user
     */
    public void editEva(Eva eva, User user) {
        evaMapper.editEva(eva);
    }


    /**
     * 根据评价指标的体系ID获取评价指标
     * @param evaID
     * @param user
     * @return
     */
    public Eva getEvaByID(long evaID, User user) {
        return evaMapper.getEvaByID(evaID);
    }

    /**
     * 导入新的评估指标
     * @param evaID 评估体系ID
     * @param linkID 关联ID
     * @param user
     */
    public void bindEva(long evaID, long linkID, User user) {
        evaMapper.addEvaLink(evaID,linkID);
    }

    /**
     * 取消评估体系的关联
     * @param evaID 评估体系ID
     * @param linkID 关联ID
     * @param user
     */
    public void unbindEva(long evaID, long linkID, User user) {
        evaMapper.deleteEvaLink(evaID,linkID);
    }

    /**
     * 评估指标体系导入评估指标
     * @param evaID 评估指标体系ID
     * @param indexIDs 评估指标ID数组
     * @param user 当前用户
     */
    public void addIndex2Eva(long evaID, long[] indexIDs, User user) {
        for (long indexID : indexIDs) {
            evaMapper.addIndex2Eva(evaID,indexID);
        }
    }

    /**
     * 将评估指标移除当前体系
     * @param evaID 评估指标体系ID
     * @param indexIDs 评估指标ID
     * @param user 用户ID
     */
    public void deleteIndexFromEva(long evaID, long[] indexIDs, User user) {
        for (Long indexID : indexIDs) {
            evaMapper.deleteIndexFromEva(evaID,indexID);
        }
    }

    /**
     * 获取评估体系内的所有指标
     * @param evaID
     * @param user
     * @return
     */
    public List<EvaIndex> getEvaIndex(long evaID, User user) {
        List<EvaIndex> evaIndexList = evaMapper.getEvaIndexByEvaID(evaID);
        evaIndexList.forEach(evaIndex -> evaIndex.setCanEdit(evaIndex.getCreator()==user.getUserID()));
        return evaIndexList;
    }

    public List<Eva> getProjectEvaList(long projectID, User user) {
        List<Eva> evaList = evaMapper.getEvaByLinkID(projectID);
        for (Eva eva : evaList) {
            eva.setEvaIndexList(evaMapper.getEvaIndexByEvaID(eva.getEvaID()));
        }
        return evaList;
    }

    public double getIndexRes(long indexID, long linkID) {
        return evaMapper.getIndexRes(indexID,linkID).getRes();
    }

    public  List<EvaIndex> getIndexList(long projectID, User user) {
        return evaMapper.getIndexList(projectID);
    }

    public void editIndexW(long evaID, long indexID, double w, User user) {
        evaMapper.editIndexW(evaID,indexID,w);
    }
}
