package cn.edu.xjtu.cad.templates.model.project;


public class Edge {

    /**
     * 边的记录ID
     */
    private int id;

    /**
     * 所属的项目ID
     */
    private int projectID;

    /**
     * 边的起始节点
     */
    private int nodeI;

    /**
     * 边的终止节点
     */
    private int nodeJ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNodeI() {
        return nodeI;
    }

    public void setNodeI(int nodeI) {
        this.nodeI = nodeI;
    }

    public int getNodeJ() {
        return nodeJ;
    }

    public void setNodeJ(int nodeJ) {
        this.nodeJ = nodeJ;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
}
