package cn.edu.xjtu.cad.templates.model;

/**
 * 评价指标
 */
public class EvaIndex {

    /**
     * 评价指标的索引
     */
    private long indexID;
    /**
     * 评价指标的名称
     */
    private String name;
    /**
     * 评价指标的权重
     */
    private double w;
    /**
     * 评价指标的最小值
     */
    private double rangeL;
    /**
     * 评价指标的最大值
     */
    private double rangeR;

    /**
     * 创建者的ID
     */
    private long creator;

    private String nickName;

    /**
     * 是否可以编辑
     */
    private boolean canEdit;

    /**
     * 评价指标的说明
     */
    private String des;

    private double res;

    public long getIndexID() {
        return indexID;
    }

    public void setIndexID(long indexID) {
        this.indexID = indexID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getRangeL() {
        return rangeL;
    }

    public void setRangeL(double rangeL) {
        this.rangeL = rangeL;
    }

    public double getRangeR() {
        return rangeR;
    }

    public void setRangeR(double rangeR) {
        this.rangeR = rangeR;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
