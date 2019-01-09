package cn.edu.xjtu.cad.templates.model.project;


import cn.edu.xjtu.cad.templates.model.project.node.Node;

import java.util.*;

public class Project {
    /**
     * 项目id
     */
    private long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目的创建时间
     */
    private Date createTime;
    /**
     * 项目的修改时间
     */
    private Date editTime;


    /**
     * 项目的查看次数
     */
    private int times;

    /**
     * 模板的基本描述
     */
    private String description;
    /**
     * 融合度
     */
    private String amalgamation;

    /**
     * 集成度
     */
    private String integration;

    /**
     * 项目标签
     */
    private String tags;

    /**
     * 项目是否公开
     */
    private boolean open;

    private boolean code;

    private long creatorID;

    private int num;

    private long referID;

    private Map<String,Step> stepMap = new LinkedHashMap<>();
    private Map<String,Node> nodeMap = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();
    private List<ProjectRole> members = new ArrayList<>();
    public Project() {
    }


    public Project(String name, String description, String tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getAmalgamation() {
        return amalgamation;
    }

    public void setAmalgamation(String amalgamation) {
        this.amalgamation = amalgamation;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }



    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
}
