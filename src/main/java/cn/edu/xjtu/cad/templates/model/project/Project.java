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
    private boolean open;

    /**
     * 项目邀请码
     */
    private boolean invitationCode;

    /**
     * 创建者ID
     */
    private long creatorID;

    private int num;

    /**
     * 参考模板ID
     */
    private long referID;

    private Map<String,Step> stepMap = new LinkedHashMap<>();
    private Map<String,Node> nodeMap = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();
    private List<ProjectRole> members = new ArrayList<>();

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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(boolean invitationCode) {
        this.invitationCode = invitationCode;
    }
}
