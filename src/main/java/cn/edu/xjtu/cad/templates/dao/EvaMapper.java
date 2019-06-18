package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.model.EvaIndexRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaMapper {
    List<Eva> getOpenEvaList();

    List<Eva> getUserEvaList(@Param("userID") long userID);

    int addEva(Eva eva);

    void deleteEva(@Param("evaID") long evaID);

    void editEva( Eva eva);

    Eva getEvaByID(@Param("evaID") long evaID);

    EvaIndex getEvaIndexByIndex(@Param("evaID") long evaID, @Param("index") long index);

    int addEvaIndex(EvaIndex ei);

    void updateEvaIndex(EvaIndex ei);

    void deleteEvaIndex(@Param("indexID")long indexID);

    List<EvaIndex> getEvaIndexByEvaID(@Param("evaID") long evaID);


    /**
     * 将评估指标添加到评估体系内
     * @param evaID 评估体系ID
     * @param indexID 评估指标ID
     */
    void addIndex2Eva(@Param("evaID")long evaID,@Param("indexID")long indexID);

    void addEvaIndexRes(EvaIndexRes evaIndexRes);

    void updateEvaIndexRes(EvaIndexRes evaIndexRes);

    void deleteIndexFromEva(long evaID, long indexID);


    void addEvaLink(long evaID, long linkID);

    void deleteEvaLink(long evaID, long linkID);
}
