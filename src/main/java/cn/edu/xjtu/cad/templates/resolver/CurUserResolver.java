package cn.edu.xjtu.cad.templates.resolver;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.UserMapper;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.User;
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
        User user = new User(userID);
        //获取用户的权限
        List<ProjectRole> projectRoleList = userMapper.getProjectRole(userID);
        Map<ProjectRoleType,Set<Long>> projectRoleTypeLongMap = new HashMap<>();
        for (ProjectRoleType projectRoleType : ProjectRoleType.values()) {
            projectRoleTypeLongMap.put(projectRoleType,new HashSet<>());
        }
        projectRoleList.forEach(projectRole -> projectRoleTypeLongMap.get(projectRole.getProjectRole()).add(projectRole.getProjectID()));
        List<NodeRole> nodeRoleList = userMapper.getNodeRole(userID);
        Map<NodeRoleType,Set<String[]>> nodeRoleTypeSetMap = new HashMap<>();
        for (NodeRoleType nodeRoleType : NodeRoleType.values()) {
            nodeRoleTypeSetMap.put(nodeRoleType,new HashSet<>());
        }
        nodeRoleList.forEach(nodeRole -> nodeRoleTypeSetMap.get(nodeRole.getNodeRole()).add(new String[]{nodeRole.getProjectID()+"",nodeRole.getNodeIndex()}));
        return new User(userID,projectRoleTypeLongMap,nodeRoleTypeSetMap);
    }
}
