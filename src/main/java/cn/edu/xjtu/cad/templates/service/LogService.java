package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.dao.LogMapper;
import cn.edu.xjtu.cad.templates.model.log.Log;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logService")
public class LogService {
    @Autowired
    LogMapper logMapper;

    public void addLog(Log log){
        logMapper.addLog(log,log.getLogType().getDataBaseName(),log.getColNames(),log.getFieldNames("log"));
    }

    public void removeLog(Log log){
        logMapper.deleteLog(log.getLogID(),log.getLogType().getDataBaseName());
    }

    public List<ProjectLog> getProjectLogCut(long projectID, long limit){
        return logMapper.getProjectLogCut( projectID, limit, LogType.PROJECT.getDataBaseName());
    }

    public List<ProjectLog> getProjectLog(long projectID) {
        return logMapper.getAllProjectLog( projectID,LogType.PROJECT.getDataBaseName());
    }

    public List<ProjectLog>  getStepLog(long projectID, String stepIndex){
        return logMapper.getStepLog(projectID,stepIndex,LogType.PROJECT.getDataBaseName());
    }
}
