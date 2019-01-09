package cn.edu.xjtu.cad.templates.model.log;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Log implements Serializable {

    private long logID;
    private LogType logType;
    /**
     *
     */
    private MethodType logMethod;
    private Date operateDate;


    /**
     * 日志对应的用户ID
     */
    private long userID;


    /**
     * 日志的内容
     */
    private String content;

    /**
     * 操作的用户地址
     */
    private String ipAddr;

    public Log() {
    }

    public Log(LogType logType, MethodType logMethod,long userID, String content, String ipAddr) {
        this.logType = logType;
        this.logMethod = logMethod;
        this.userID = userID;
        this.content = content;
        this.ipAddr = ipAddr;
    }

    public boolean parseParamMap(Map<String, String[]> paramMap){
        return true;
    }
    public String getColNames(){
        return null;
    }
    public String getFieldNames(String objectName){
        return null;
    }

    public long getLogID() {
        return logID;
    }

    public void setLogID(long logID) {
        this.logID = logID;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public MethodType getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(MethodType logMethod) {
        this.logMethod = logMethod;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
