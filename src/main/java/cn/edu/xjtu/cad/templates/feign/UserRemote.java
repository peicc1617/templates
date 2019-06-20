package cn.edu.xjtu.cad.templates.feign;

import cn.edu.xjtu.cad.templates.model.CAIUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name= "user")
public interface UserRemote {
    @RequestMapping(value = "/user/list/in",method = RequestMethod.GET)
    public List<CAIUser> listIn(@RequestParam(value = "userIDList")  List<Long> userIDList);
}
