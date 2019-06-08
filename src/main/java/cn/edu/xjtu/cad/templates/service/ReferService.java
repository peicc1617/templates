package cn.edu.xjtu.cad.templates.service;

import cn.edu.xjtu.cad.templates.dao.ReferMapper;
import cn.edu.xjtu.cad.templates.model.project.Refer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;

@Service
public class ReferService {
    @Autowired
    ReferMapper referMapper;

    /**
     * 获取所有的参考模板
     * @return
     */
    public List<Refer> getAllRefer(){
        return referMapper.getAllRefer();
    }

    public Refer getRefer(long referID){
        return  referMapper.getReferByID(referID);
    }
}
