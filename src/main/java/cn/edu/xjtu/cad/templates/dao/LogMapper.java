package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.Log;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LogMapper {


    long countProjectLog(@Param("projectID") long projectID,
                         @Param("stepIndex") int stepIndex,
                         @Param("nodeIndex") int nodeIndex,
                         @Param("startDate") Date startDate,
                         @Param("endDate") Date endDate,
                         @Param("logType") LogType type,
                         @Param("userID") long userID);

    Log getProjectLog(@Param("id")int id,@Param("tableName") String logTableName);

    long addLog(@Param("log") Log log,
                @Param("tableName") String logTableName,
                @Param("colNames")String colNames,
                @Param("fieldNames")String fieldNames);

    long deleteLog(@Param("id") long id,@Param("tableName") String logTableName);

}
