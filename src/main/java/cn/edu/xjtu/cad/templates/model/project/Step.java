package cn.edu.xjtu.cad.templates.model.project;


import java.util.Date;
import java.util.UUID;

public class Step {

    public Step() {
    }

    public Step(String stepName) {
        this.stepIndex = UUID.randomUUID().toString();
        this.stepName = stepName;
    }

    public Step(long projectID, String stepIndex, String stepName, String stepDesc) {
        this.projectID = projectID;
        this.stepIndex= stepIndex;
        this.stepName = stepName;
        this.stepDesc = stepDesc;
    }

    /**
     * 阶段的ID
     */
    private String stepIndex;

    /**
     * 所属的项目ID
     */
    private long projectID;

    /**
     * 阶段的名称
     */
    private String stepName;

    /**
     * 阶段的描述
     */
    private String stepDesc;
    /**
     * 阶段的创建时间
     */
    private Date createTime;

    /**
     * 阶段的修改时间
     */
    private Date editTime;

    /**
     * 阶段总结
     */
    private String summary;

    /**
     * 当前阶段的次序
     */
    private int pos;
    public String getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(String stepIndex) {
        this.stepIndex = stepIndex;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
