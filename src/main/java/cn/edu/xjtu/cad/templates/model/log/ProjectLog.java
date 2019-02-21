package cn.edu.xjtu.cad.templates.model.log;

import java.util.Map;

public class ProjectLog extends Log {
    /**
     * 操作的项目
     */
    private long projectID;
    private String stepIndex;
    private String nodeIndex;

    public ProjectLog() {
    }

    public ProjectLog(LogType logType, MethodType logMethod, long userID, String content, String ipAddr) {
        super(logType, logMethod,  userID, content, ipAddr);
    }

    public boolean parseParamMap(Map<String, String[]> paramMap){
        if(paramMap==null){
            return false;
        }
        String[] arr = {"-1"};
        try {
            this.projectID = Integer.valueOf(paramMap.getOrDefault("projectID",arr)[0]);
            if(paramMap.containsKey("nodeIndex")){
                this.nodeIndex = paramMap.getOrDefault("nodeIndex",arr)[0];
            }
            if(paramMap.containsKey("stepIndex")){
                this.stepIndex = paramMap.getOrDefault("stepIndex",arr)[0];
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getColNames(){
        return ",projectID,stepIndex,nodeIndex";
    }
    @Override
    public String getFieldNames(String objectName){
        return ",#{@.projectID},#{@.stepIndex},#{@.nodeIndex}".replaceAll("@",objectName);
    }

    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public String getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(String stepIndex) {
        this.stepIndex = stepIndex;
    }

    public String getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(String nodeIndex) {
        this.nodeIndex = nodeIndex;
    }
}
