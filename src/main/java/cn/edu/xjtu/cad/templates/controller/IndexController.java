package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    User user;

    /**
     * 添加新的评估指标
     * @param evaIndex
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public long addProjectEvaIndex(EvaIndex evaIndex){
        return indexService.addEvaIndex(evaIndex,user);
    }

    /**
     * 删除评估指标
     * @param evaID
     * @return
     */
    @RequestMapping(value = "/{evaID}/index/{indexID}",method = RequestMethod.DELETE)
    public void deleteProjectEvaIndex(@PathVariable long evaID, @PathVariable long indexID){
        indexService.deleteEvaIndex(evaID,indexID,user);
    }

    /**
     * 更新评估指标
     * @param evaID
     * @param indexID
     * @param evaIndex
     */
    @RequestMapping(value = "/{evaID}/index/{indexID}",method = RequestMethod.PUT)
    public void updateProjectEvaIndex(@PathVariable long evaID,@PathVariable long indexID,EvaIndex evaIndex){
        evaIndex.setIndexID(indexID);
        indexService.updateEvaIndex(evaIndex,user);
    }

    @RequestMapping(value = "/{evaID}/index/{indexID}/refreshRange",method = RequestMethod.PUT)
    public void refreshEvaIndexRange(@PathVariable long evaID,@PathVariable long indexID){
        indexService.refreshEvaIndexRange(indexID,user);
    }

}
