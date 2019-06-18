package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    User user;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<EvaIndex> getAllEvaIndex(long evaID){
        return indexService.getAllEvaIndex(evaID,user);
    }

    /**
     * 添加新的评估指标
     * @param evaIndex
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public long addProjectEvaIndex(EvaIndex evaIndex){

        return indexService.addEvaIndex(evaIndex,user);
    }

    /**
     * 删除评估指标
     * @return
     */
    @RequestMapping(value = "/{indexID}",method = RequestMethod.DELETE)
    public void deleteProjectEvaIndex( @PathVariable long indexID){
        indexService.deleteEvaIndex(indexID,user);
    }

    /**
     * 更新评估指标
     * @param indexID
     * @param evaIndex
     */
    @RequestMapping(value = "/{indexID}",method = RequestMethod.PUT)
    public void updateProjectEvaIndex(@PathVariable long indexID,EvaIndex evaIndex){
        evaIndex.setIndexID(indexID);
        indexService.updateEvaIndex(evaIndex,user);
    }

    @RequestMapping(value = "/{indexID}/refreshRange",method = RequestMethod.PUT)
    public void refreshEvaIndexRange(@PathVariable long indexID){
        indexService.refreshEvaIndexRange(indexID,user);
    }


    @RequestMapping(value = "/{indexID}/res/{linkID}",method = RequestMethod.PUT)
    public void updateIndexRes(@PathVariable long indexID,@PathVariable long linkID,double res){
        indexService.updateIndexRest(indexID,linkID,res,user);
    }

}
