package cn.edu.xjtu.cad.templates.model.project;


public class Edge {

    /**
     * 边的记录ID
     */
    private int id;

    /**
     * 所属的项目ID
     */
    private long projectID;

    /**
     * 边的起始节点
     */
    private String nodeI;

    /**
     * 边的终止节点
     */
    private String nodeJ;

    public Edge() {
    }

    public Edge(long projectID, String nodeI, String nodeJ) {
        this.projectID = projectID;
        this.nodeI = nodeI;
        this.nodeJ = nodeJ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeI() {
        return nodeI;
    }

    public void setNodeI(String nodeI) {
        this.nodeI = nodeI;
    }

    public String getNodeJ() {
        return nodeJ;
    }

    public void setNodeJ(String nodeJ) {
        this.nodeJ = nodeJ;
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }
}
