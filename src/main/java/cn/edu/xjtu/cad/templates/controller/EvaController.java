package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.Eva;
import cn.edu.xjtu.cad.templates.model.EvaIndex;
import cn.edu.xjtu.cad.templates.model.EvaIndexRes;
import cn.edu.xjtu.cad.templates.service.EvaService;
import cn.edu.xjtu.cad.templates.service.IndexService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 项目评价体系的controller
 */
@RequestMapping("/api/eva")
@RestController
public class EvaController {
    @Autowired
    EvaService evaService;

    @Autowired
    IndexService indexService;

    @Autowired
    User user;

    /**
     * 获取当前用户可以看到的所有评价体系
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Eva> getUserEvaList(long linkID){
        return evaService.getUserEvaList(linkID,user);
    }

    /**
     * 根据评估指标体系ID获取评估体系
     * @param evaID
     */
    @RequestMapping(value = "/{evaID}",method = RequestMethod.GET)
    public Eva getEvaById(@PathVariable long evaID){
        return evaService.getEvaByID(evaID,user);
    }

    /**
     * 添加评估体系
     * @param eva
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public long addEva( Eva eva){
        return evaService.addEva(eva,user);
    }

    /**
     * 删除评估体系
     * @param evaID
     */
    @RequestMapping(value = "/{evaID}",method = RequestMethod.DELETE)
    public void deleteEva(@PathVariable long evaID){
        evaService.deleteEva(evaID,user);
    }

    /**
     * 编辑评估体系的基本信息
     * @param eva
     */
    @RequestMapping(value = "/{evaID}",method = RequestMethod.PUT)
    public void editEva(@PathVariable long evaID,Eva eva){
        eva.setEvaID(evaID);
        evaService.editEva(eva,user);
    }

    /**
     * 关联新的评估体系
     * @param evaID
     * @param linkID
     */
    @RequestMapping(value = "/{evaID}/bind",method = RequestMethod.POST)
    public void bindEva(@PathVariable long evaID,long linkID){
        evaService.bindEva(evaID,linkID,user);
    }

    /**
     * 取消评估体系的关联
     * @param evaID
     * @param linkID
     */
    @RequestMapping(value = "/{evaID}/bind",method = RequestMethod.DELETE)
    public void unbind(@PathVariable long evaID,long linkID){
        evaService.unbindEva(evaID,linkID,user);
    }

    /**
     * 新建新的评估指标同时导入评估指标体系
     * @param evaID
     */
    @RequestMapping(value = "/{evaID}/index",method = RequestMethod.POST)
    public long addIndexAndAdd2Eva(@PathVariable long evaID,
                                   EvaIndex evaIndex){
        //首先创建指标，获取创建的评估指标ID
        long indexID = indexService.addEvaIndex(evaIndex,user);
        //然后进行绑定
        evaService.addIndex2Eva(evaID, new long[]{indexID},user);
        return indexID;
    }

    /**
     * 将指标导入评估指标体系
     * @param evaID
     * @param indexIDs
     */
    @RequestMapping(value = "/{evaID}/index",method = RequestMethod.PUT)
    public void addIndex2Eva(@PathVariable long evaID, @RequestParam("indexIDs[]") long[] indexIDs){
        evaService.addIndex2Eva(evaID,indexIDs,user);
    }

    /**
     * 修改指标在评估体系内的权重
     * @param evaID 评估体系ID
     * @param indexID 指标ID
     * @param w 权重值
     */
    @RequestMapping(value = "/{evaID}/index/{indexID}/w",method = RequestMethod.PUT)
    public void editIndexW(@PathVariable long evaID, @PathVariable long indexID, double w){
        evaService.editIndexW(evaID,indexID,w,user);
    }

    /**
     * 将指标从体系内删除
     * @param evaID
     * @param indexIDs
     */
    @RequestMapping(value = "/{evaID}/index",method = RequestMethod.DELETE)
    public void deleteIndexFromEva(@PathVariable long evaID,@RequestParam("indexIDs[]") long[] indexIDs){
        evaService.deleteIndexFromEva(evaID,indexIDs,user);
    }

    /**
     * 获取当前体系内的所有评估指标
     * @param evaID
     * @return
     */
    @RequestMapping(value = "/{evaID}/index",method = RequestMethod.GET)
    public List<EvaIndex> getEvaIndex(@PathVariable long evaID){
        return evaService.getEvaIndex(evaID,user);
    }



}
