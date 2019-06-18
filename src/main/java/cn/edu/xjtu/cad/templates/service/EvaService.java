package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.EvaMapper;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.model.EvaIndexRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.util.Cache;

import java.util.ArrayList;
import java.util.List;

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
     * @param indexID 评估指标ID
     * @param user 当前用户
     */
    public void addIndex2Eva(long evaID, long indexID, User user) {
        evaMapper.addIndex2Eva(evaID,indexID);
    }


    /**
     * 将评估指标移除当前体系
     * @param evaID 评估指标体系ID
     * @param indexID 评估指标ID
     * @param user 用户ID
     */
    public void deleteIndexFromEva(long evaID, long indexID, User user) {
        evaMapper.deleteIndexFromEva(evaID,indexID);
    }

    /**
     * 获取评估体系内的所有指标
     * @param evaID
     * @param user
     * @return
     */
    public List<EvaIndex> getEvaIndex(long evaID, User user) {
        return evaMapper.getEvaIndexByEvaID(evaID);
    }

}
