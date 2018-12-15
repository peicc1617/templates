package cn.edu.xjtu.cad.templates.resolver;

import cn.edu.xjtu.cad.templates.annotation.CurUser;
import cn.edu.xjtu.cad.templates.dao.UserMapper;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        String curUsername = ((Map<String,String>)nativeWebRequest.getAttribute("userInfo", NativeWebRequest.SCOPE_SESSION)).get("username");
        User user = new User(curUsername);
        List<ProjectRole> projectRoleList = userMapper.getProjectRole(curUsername);
        List<NodeRole> nodeRoleList = userMapper.getNodeRole(curUsername);
        user.setProjectRoleMap(projectRoleList.stream().collect(Collectors.toMap(ProjectRole::getProjectID,ProjectRole::getProjectRole)));
        Map<Integer,Map<String,NodeRoleType>> nodeRoleMap = new HashMap<>();
        for (NodeRole nodeRole : nodeRoleList) {
            int projectID = nodeRole.getProjectID();
            String nodeIndex=  nodeRole.getNodeIndex();
            Map<String,NodeRoleType> map = nodeRoleMap.getOrDefault(projectID,new HashMap<>());
            map.put(nodeIndex,nodeRole.getNodeRole());
        }
        return user;
    }
}
