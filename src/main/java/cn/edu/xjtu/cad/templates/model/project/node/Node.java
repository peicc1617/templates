package cn.edu.xjtu.cad.templates.model.project.node;


import java.util.*;

/**
 * 项目的节点类，对应数据库中的project_node表，利用项目ID和节点在项目内的索引index作为联合主键
 */
public class Node {

    public Node() {

    }

    public Node( String stepIndex, String nodeName) {
        this.stepIndex = stepIndex;
        this.nodeIndex = UUID.randomUUID().toString();
        this.nodeName = nodeName;
    }

    public Node(long projectID, String stepIndex, String nodeIndex, String nodeName) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.nodeName = nodeName;
        this.stepIndex = stepIndex;
    }

    /**
     * 节点所属的项目ID
     */
    private long projectID;

    private String nodeIndex;

    private String stepIndex;
    /**
     * 节点的名称
     */
    private String nodeName;
    /**
     * 当前节点的状态
     */
    private boolean state;

    /**
     * 节点描述
     */
    private String nodeDesc;

    /**
     * 阶段目标
     */
    private String goal;

    /**
     * 阶段的总结
     */
    private String summary;

    /**
     * 节点的创建时间
     */
    private Date createTime;

    /**
     * 节点的修改时间
     */
    private Date editTime;

    /**
     * 节点绑定的APP名称
     */
    private String appName;

    private String appPath;

    private String appIcon;

    /**
     * 节点绑定的模板
     */
    private int templateProjectID;

    private int workTime;

    private Date startTime;
    private Date endTime;
    private Date planStartTime;
    private Date planEndTime;
    private DateDelay delay;

    private boolean lockState;

    private List<String> preNodeIndexList = new ArrayList<>() ;
    private List<String> nextNodeIndexList = new ArrayList<>();
    /**
     * 阶段包含的成员
     */
    private List<NodeResult> resultList = new ArrayList<>();


    public List<NodeResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<NodeResult> resultList) {
        this.resultList = resultList;
    }

    /**
     * 阶段包含创新方法工具的个数
     */

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }


    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public List<String> getPreNodeIndexList() {
        return preNodeIndexList;
    }

    public void setPreNodeIndexList(List<String> preNodeIndexList) {
        this.preNodeIndexList = preNodeIndexList;
    }

    public List<String> getNextNodeIndexList() {
        return nextNodeIndexList;
    }

    public void setNextNodeIndexList(List<String> nextNodeIndexList) {
        this.nextNodeIndexList = nextNodeIndexList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }


    public boolean getLockState() {
        return lockState;
    }

    public void setLockState(boolean lockState) {
        this.lockState = lockState;
    }

    public int getTemplateProjectID() {
        return templateProjectID;
    }

    public void setTemplateProjectID(int templateProjectID) {
        this.templateProjectID = templateProjectID;
    }

    public boolean isLockState() {
        return lockState;
    }

    public String getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(String stepIndex) {
        this.stepIndex = stepIndex;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public DateDelay getDelay() {
        return delay;
    }

    public void setDelay(DateDelay delay) {
        this.delay = delay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
