package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.Log;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import cn.edu.xjtu.cad.templates.model.project.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    List<ProjectLog> getProjectLogCut(@Param("projectID")long projectID,@Param("limit") long limit,@Param("tableName") String logTableName);

    List<ProjectLog> getAllProjectLog(@Param("projectID") long projectID,@Param("tableName") String dataBaseName);

    List<ProjectLog> getStepLog(@Param("projectID") long projectID,@Param("stepIndex")String stepIndex,@Param("tableName") String dataBaseName);
}
