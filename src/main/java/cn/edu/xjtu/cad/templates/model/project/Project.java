package cn.edu.xjtu.cad.templates.model.project;


import cn.edu.xjtu.cad.templates.model.project.node.Node;

import java.util.*;

public class Project {
    /**
     * 项目id
     */
    private long projectID;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目的创建时间
     */
    private Date createTime;
    /**
     * 项目的修改时间
     */
    private Date editTime;

    private Date startTime;
    /**
     * 项目的基本描述
     */
    private String projectDesc;

    /**
     * 项目标签
     */
    private String projectTags;

    /**
     * 项目是否公开
     */
    private boolean openState;

    /**
     * 项目邀请码
     */
    private String invitationCode;

    /**
     * 创建者ID
     */
    private long creatorID;

    private int num;

    private double maturity;

    private double dea;

    private ProjectIndex projectIndex;

    private String problemID;

    /**
     * 参考模板ID
     */
    private long referID;

    private ProjectRoleType projectRole;

    private Map<String,Step> stepMap = new LinkedHashMap<>();
    /**
     * 阶段数
     */
    private int stepNum =0;
    /**
     * 阶段Map
     */
    private Map<String,Node> nodeMap = new HashMap<>();
    /**
     * 工作节点数
     */
    private int nodeNum =0;
    /**
     * 节点Map
     */
    private List<Edge> edges = new ArrayList<>();

    /**
     * 成员列表
     */
    private List<ProjectRole> members = new ArrayList<>();
    /**
     * 用户数
     */
    private int memberNum =0;

    private double activity;

    public Project() {

    }

    public Project(String projectName, String projectDesc, String projectTags) {
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.projectTags = projectTags;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectTags() {
        return projectTags;
    }

    public void setProjectTags(String projectTags) {
        this.projectTags = projectTags;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(long creatorID) {
        this.creatorID = creatorID;
    }

    public long getReferID() {
        return referID;
    }

    public void setReferID(long referID) {
        this.referID = referID;
    }

    public Map<String, Step> getStepMap() {
        return stepMap;
    }

    public void setStepMap(Map<String, Step> stepMap) {
        this.stepMap = stepMap;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<ProjectRole> getMembers() {
        return members;
    }

    public void setMembers(List<ProjectRole> members) {
        this.members = members;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isOpenState() {
        return openState;
    }

    public void setOpenState(boolean openState) {
        this.openState = openState;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public ProjectRoleType getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(ProjectRoleType projectRole) {
        this.projectRole = projectRole;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public double getMaturity() {
        return maturity;
    }

    public void setMaturity(double maturity) {
        this.maturity = maturity;
    }

    public double getDea() {
        return dea;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }

    public ProjectIndex getProjectIndex() {
        return projectIndex;
    }

    public void setProjectIndex(ProjectIndex projectIndex) {
        this.projectIndex = projectIndex;
    }

    public String getProblemID() {
        return problemID;
    }

    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }
}
