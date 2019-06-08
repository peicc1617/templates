package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.dao.LogMapper;
import cn.edu.xjtu.cad.templates.dao.StepMapper;
import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import cn.edu.xjtu.cad.templates.model.project.Project;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepService {
    @Autowired
    LogService logService;

    final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<List> getActivation(long projectID, String stepIndex){
        List<ProjectLog> logs = logService.getStepLog(projectID,stepIndex);
        logs.sort((l1,l2)-> (int) (l1.getOperateDate().getTime()-l2.getOperateDate().getTime()));
        Map<String,Integer> map = new HashMap<>();
        logs.forEach(l->{
            String date = simpleDateFormat.format(l.getOperateDate());
            map.put(date,map.getOrDefault(date,0)+1);
        });
        return map.entrySet().stream().map(entry->
            new ArrayList(){{
                add(entry.getKey());
                add(entry.getValue());
            }}
        ).collect(Collectors.toList());

    }
}
