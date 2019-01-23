package cn.edu.xjtu.cad.templates.filter;

import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewInterceptor implements HandlerInterceptor {

    @Autowired
    User user;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(user==null||user.getProjectRoleType()==null||user.getProjectRoleType()== ProjectRoleType.APPLY){
            request.getRequestDispatcher("/templates/project/noAccess.html").forward(request,response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
