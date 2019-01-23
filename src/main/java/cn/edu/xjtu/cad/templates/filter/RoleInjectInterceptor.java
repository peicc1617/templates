package cn.edu.xjtu.cad.templates.filter;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.dao.ProjectMapper;
import cn.edu.xjtu.cad.templates.dao.ProjectRoleMapper;
import cn.edu.xjtu.cad.templates.model.project.Project;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RoleInjectInterceptor implements HandlerInterceptor {


    @Autowired
    User user;
    @Autowired
    ProjectRoleMapper projectRoleMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long projectID = 0;
        //这里要判断map是否为空，主要原因是spring boot 2.x 版本以后静态资源也会被拦截，
        // 出现这种情况的时候 路径参数为空，所以map为空，如果不判断回导致出现空指针异常。
        if (map != null) {
            //获取项目ID
            projectID = Long.valueOf((String) map.getOrDefault("projectID", "0"));
        }
        //如果没有项目ID，直接返回
        if (projectID == 0) {
            return true;
        }
        //根据projectID查询项目
        Project project = projectMapper.getProjectByID(projectID);
        //获取用户在当前项目内的权限
        ProjectRoleType projectRoleType = projectRoleMapper.getProjectRoleType(projectID, user.getUserID());
        if(request.getRequestURL().toString().contains(".html")){
            if(project==null){
                request.getRequestDispatcher("/project/noResource.html").forward(request,response);
                return false;
            }
            if(projectRoleType==null||projectRoleType==ProjectRoleType.APPLY){
                request.getRequestDispatcher("/project/noAccess.html").forward(request, response);
                return false;
            }
            return true;
        }
        user.setProjectRoleType(projectRoleType);
        return  true;
    }
}
