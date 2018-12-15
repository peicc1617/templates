package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.dao.LogMapper;
import cn.edu.xjtu.cad.templates.model.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
