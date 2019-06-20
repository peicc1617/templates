package cn.edu.xjtu.cad.templates.feign;

import cn.edu.xjtu.cad.templates.model.CAIUser;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "project-manager")
public interface ProjectManagerRemote {
    @RequestMapping(value = "/projectManager/api/v1/project",method = RequestMethod.PUT)
    JSONObject getResult(@RequestParam(value = "userIDList") long tProjectID, @RequestParam(value = "resultKeys[]") String[] resultKeys);
}
