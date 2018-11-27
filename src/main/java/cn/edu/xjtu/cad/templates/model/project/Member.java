package cn.edu.xjtu.cad.templates.model.project;

/**
 * 描述项目内成员的类，与数据库内的表相对应，其中使用用户ID和项目ID作为联合主键查询。
 */
public class Member {

    /**
     * 用户的ID
     */
    private String username;

    /**
     * 项目的ID
     */
    private int projectID;

    private int nodeIndex;
    /**
     * 用户在该项目内是否授权，目前考虑的情况是两种，一种是用户已经加入该项目，另外一种是用户处于待确认的状态
     * 权限等级分为三种：
     * 待审批人员，对应为-1
     * 项目创建者,对应为0
     * 项目管理员，对应为1
     * 项目成员，对应为2
     */
    private int au;

    /**
     * 待审批人员
     */
    public final static int PROJECT_APPLY = -1;
    /**
     * 查看
     */
    public final static short NODE_VIEW = 1;

    /**
     * 绑定数据
     */
    public final static short NODE_DATA = 2;

    /**
     * 管理
     */
    public final  static short NODE_MANAGER =3;

    public final  static short CREATOR =10;
    public final  static short SUPER_MANAGER =5;
    public final  static short USER_MANAGER =1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }


    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public int getAu() {
        return au;
    }

    public void setAu(int au) {
        this.au = au;
    }
}
