package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.project.Refer;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Ref;
import java.util.List;

@Repository
public interface ReferMapper {

    List<Refer> getAllRefer();


    Refer getReferByID(long referID);


}
