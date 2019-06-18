package cn.edu.xjtu.cad.templates.model;

import java.util.Date;

public class EvaIndexRes {
    /**
     * 指标ID
     */
    private long indexID;

    /**
     * 关联ID，这里的关联ID可以看作是项目ID
     */
    private long linkedID;

    /**
     * 指标对象
     */
    private EvaIndex evaIndex;
    /**
     * 指标值
     */
    private double res;

    /**
     * 指标填写时间
     */
    private Date time;

    public EvaIndexRes() {
    }

    public EvaIndexRes(long indexID, long linkedID, EvaIndex evaIndex, double res) {
        this.indexID = indexID;
        this.linkedID = linkedID;
        this.evaIndex = evaIndex;
        this.res = res;
    }

    public long getIndexID() {
        return indexID;
    }

    public void setIndexID(long indexID) {
        this.indexID = indexID;
    }

    public long getLinkedID() {
        return linkedID;
    }

    public void setLinkedID(long linkedID) {
        this.linkedID = linkedID;
    }

    public EvaIndex getEvaIndex() {
        return evaIndex;
    }

    public void setEvaIndex(EvaIndex evaIndex) {
        this.evaIndex = evaIndex;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
