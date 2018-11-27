package cn.edu.xjtu.cad.templates.model.project;


public class Refer {
    private int referID;
    private String referName;
    private String nodes;
    private String edges;
    private String tags;
    private String description;
    private String steps;

    public int getReferID() {
        return referID;
    }

    public void setReferID(int referID) {
        this.referID = referID;
    }

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getEdges() {
        return edges;
    }

    public void setEdges(String edges) {
        this.edges = edges;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
