package cn.edu.xjtu.cad.templates.controller;

import cn.edu.xjtu.cad.templates.dao.ReferMapper;
import cn.edu.xjtu.cad.templates.model.project.Refer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/refer")
public class ReferController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    ReferMapper referMapper;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Refer> getAllRefer(){
        return referMapper.getAllRefer();
    }
}
