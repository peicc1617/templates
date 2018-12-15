package cn.edu.xjtu.cad.templates.model.log;

import java.util.Map;

public class ProjectLog extends Log {
    /**
     * 操作的项目
     */
    private int projectID;
    private int stepIndex;
    private int nodeIndex;

    public ProjectLog(LogType logType, MethodType logMethod, String username, String content, String ipAddr) {
        super(logType, logMethod,  username, content, ipAddr);
    }

    public boolean parseParamMap(Map<String, String[]> paramMap){
        if(paramMap==null){
            return false;
        }
        String[] arr = {"-1"};
        try {
            this.projectID = Integer.valueOf(paramMap.getOrDefault("projectID",arr)[0]);
            this.nodeIndex = Integer.valueOf(paramMap.getOrDefault("nodeIndex",arr)[0]);
            this.stepIndex = Integer.valueOf(paramMap.getOrDefault("stepIndex",arr)[0]);
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

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

}
