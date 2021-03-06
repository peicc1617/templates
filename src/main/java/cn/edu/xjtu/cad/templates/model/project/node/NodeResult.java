package cn.edu.xjtu.cad.templates.model.project.node;


import java.util.Date;

/**
 * 项目节点内所使用的APP对应的类，使用项目ID节点ID和工具在节点内的索引作为联合主键
 */
public class NodeResult {
    /**
     * 项目ID
     */
    private long projectID;

    /**
     * 节点ID
     */
    private String nodeIndex;


    /**
     * 用户ID
     */
    private long userID;

    private String nickName;


    /**
     * 当前结果的状态
     */
    private NodeResultState state = NodeResultState.UN_BIND;

    /**
     * 更新时间
     */
    private Date editTime;

    /**
     * 结果绑定的数据名称
     */
    private String resultName;

    /**
     * 结果的消息
     */
    private String message;

    public final static String DISABLE_MESSAGE = "原数据丢失，请重新绑定";
    public final static String EDIT_MESSAGE = "原数据更新，请重新审阅";
    public final static String APPLY_MESSAGE = "数据绑定，请审阅";
    public final static String TO_EDIT_MESSAGE = "数据需要修改，请查看";
    public final static String PASSED_MESSAGE = "数据已通过";

    /**
     * 结果绑定的数据key
     */
    private String resultKey;

    /**
     * 结果绑定的数据ID
     */
    private int resultID;

    public NodeResult() {
    }

    public NodeResult(long projectID, String nodeIndex, long userID) {
        this.projectID = projectID;
        this.nodeIndex = nodeIndex;
        this.userID = userID;
    }


    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public NodeResultState getState() {
        return state;
    }

    public void setState(NodeResultState state) {
        this.state = state;
    }


    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
