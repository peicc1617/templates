package cn.edu.xjtu.cad.templates.dao;

import cn.edu.xjtu.cad.templates.model.Project;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    @Select("SELECT project.`name`,project.id FROM project,project_person WHERE project.id = project_person.projectID AND project_person.username=#{username} ORDER BY project.createTime;")
    List<Project> getStudentByID(String username);
}
