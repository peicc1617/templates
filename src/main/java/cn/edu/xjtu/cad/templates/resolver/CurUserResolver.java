package cn.edu.xjtu.cad.templates.resolver;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.UserMapper;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.config.User;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurUser.class))
            return true;
        else
            return false;
    }

    @Override
    public User resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        long userID= Integer.valueOf(((Map<String,String>)nativeWebRequest.getAttribute("userInfo", NativeWebRequest.SCOPE_SESSION)).get("id"));
        //获取用户的权限
        Map<Long,ProjectRoleType> projectRoleTypeMap = userMapper.getProjectRole(userID)
                .stream()
                .collect(Collectors.toMap(ProjectRole::getProjectID,ProjectRole::getProjectRole));
        List<NodeRole> nodeRoleList = userMapper.getNodeRole(userID);
        Map<String,NodeRoleType> nodeRoleTypeMap = userMapper.getNodeRole(userID)
                .stream()
                .collect(Collectors.toMap(role->role.getProjectID()+";"+role.getNodeIndex(),NodeRole::getNodeRole));
        return null;
    }
}
