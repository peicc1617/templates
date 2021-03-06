package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.EvaMapper;
import cn.edu.xjtu.cad.templates.feign.UserRemote;
import cn.edu.xjtu.cad.templates.model.CAIUser;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.model.EvaIndexRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IndexService {
    @Autowired
    EvaMapper evaMapper;

    @Autowired
    UserRemote userRemote;
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
        evaIndex.setCreator(user.getUserID());
        evaMapper.addEvaIndex(evaIndex);
        return evaIndex.getIndexID();
    }

    public void deleteEvaIndex(long indexID, User user) {
        evaMapper.deleteEvaIndex(indexID);
        evaMapper.deleteEvaLinkIndexByIndexID(indexID);
        evaMapper.deleteIndexResByIndexID(indexID);
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
        packageIndexCreatorName(evaIndexList);
        evaIndexList.forEach(evaIndex -> evaIndex.setCanEdit(evaIndex.getCreator()==user.getUserID()));
        return evaIndexList
                .stream()
                .filter(evaIndex ->
                        !set.contains(evaIndex.getIndexID()))
                .collect(Collectors.toList());
    }


    /**
     * 更新指标值的结果
     * @param indexID 指标ID
     * @param linkID 关联ID
     * @param res 结果
     * @param user
     */
    public void updateIndexRes(long indexID, long linkID, double res, User user) {
        EvaIndexRes evaIndexRes = evaMapper.getIndexRes(indexID,linkID);
        if(evaIndexRes==null){
            evaIndexRes = new EvaIndexRes(indexID,linkID,res);
            evaMapper.addIndexRes(evaIndexRes);
        }else {
            evaIndexRes = new EvaIndexRes(indexID,linkID,res);
            evaMapper.updateIndexRes(evaIndexRes);
        }

    }

    private void packageIndexCreatorName(List<EvaIndex> evaIndices){
        List<Long> userIDList = evaIndices.stream().map(EvaIndex::getCreator).collect(Collectors.toList());
        Map<Long, CAIUser> map = userRemote.listIn(userIDList)
                .stream().collect(Collectors.toMap(u->u.getId(), Function.identity()));
        evaIndices.forEach(eva -> {
            String name = map.get(eva.getCreator()).getNickName();
            eva.setNickName(name);
        });
    }
}
