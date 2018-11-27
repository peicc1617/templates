package cn.edu.xjtu.cad.templates.model.project;


import java.util.Date;

public class Step {

    public Step() {
    }

    public Step(int projectID, int stepIndex, String name, String description) {
        this.projectID = projectID;
        this.stepIndex= stepIndex;
        this.name = name;
        this.description = description;
    }

    /**
     * 阶段的ID
     */
    private int stepIndex;

    /**
     * 所属的项目ID
     */
    private int projectID;

    /**
     * 阶段的名称
     */
    private String name;

    /**
     * 阶段的描述
     */
    private String description;
    /**
     * 节点的创建时间
     */
    private Date createTime;

    /**
     * 节点的修改时间
     */
    private Date editTime;

    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
