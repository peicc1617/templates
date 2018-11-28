package cn.edu.xjtu.cad.templates.model.project;


import cn.edu.xjtu.cad.templates.model.project.node.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    /**
     * 项目id
     */
    private int id;

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

    private String tags;

    private String creator;
    private int num;


    private int referID;

    private List<Step> steps = new ArrayList<>();
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private List<ProjectRole> members = new ArrayList<>();
    public Project() {
    }



    public Project(String name, String description, String tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    public int getReferID() {
        return referID;
    }

    public void setReferID(int referID) {
        this.referID = referID;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
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
}
