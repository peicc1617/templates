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
    private long linkID;

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

    public EvaIndexRes(long indexID, long linkID, double res) {
        this.indexID = indexID;
        this.linkID = linkID;
        this.res = res;
    }

    public long getIndexID() {
        return indexID;
    }

    public void setIndexID(long indexID) {
        this.indexID = indexID;
    }

    public long getLinkID() {
        return linkID;
    }

    public void setLinkID(long linkID) {
        this.linkID = linkID;
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
