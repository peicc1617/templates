package cn.edu.xjtu.cad.templates.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 评价指标体系
 */
public class Eva {
    /**
     * 指标体系的ID
     */
    private long evaID;
    /**
     * 指标体系的名称
     */
    private String name;
    /**
     * 指标体系的描述说明
     */
    private String des;
    /**
     * 指标体系的tag
     */
    private String tags;
    /**
     * 指标体系的创建者
     */
    private long creator;
    /**
     * 是否公开
     */
    private boolean open;

    private long linkID;

    private double res;

    private boolean canEdit;

    private List<EvaIndex> evaIndexList = new LinkedList<>();


    public long getEvaID() {
        return evaID;
    }

    public void setEvaID(long evaID) {
        this.evaID = evaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<EvaIndex> getEvaIndexList() {
        return evaIndexList;
    }

    public void setEvaIndexList(List<EvaIndex> evaIndexList) {
        this.evaIndexList = evaIndexList;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public long getLinkID() {
        return linkID;
    }

    public void setLinkID(long linkID) {
        this.linkID = linkID;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}
