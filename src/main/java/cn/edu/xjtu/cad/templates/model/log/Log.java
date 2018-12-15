package cn.edu.xjtu.cad.templates.model.log;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Log implements Serializable {

    private int logID;
    private LogType logType;
    /**
     *
     */
    private MethodType logMethod;
    private Date operateDate;


    /**
     * 日志对应的用户名
     */
    private String username;


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

    public Log(LogType logType, MethodType logMethod,String username, String content, String ipAddr) {
        this.logType = logType;
        this.logMethod = logMethod;
        this.username = username;
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
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
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

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
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
