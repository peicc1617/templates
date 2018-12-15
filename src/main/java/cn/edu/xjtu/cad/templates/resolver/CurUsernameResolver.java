package cn.edu.xjtu.cad.templates.resolver;

import cn.edu.xjtu.cad.templates.annotation.CurUsername;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Service
public class CurUsernameResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(CurUsername.class))
            return true;
        else
            return false;
    }

    @Override
    public String resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return ((Map<String,String>)nativeWebRequest.getAttribute("userInfo",RequestAttributes.SCOPE_SESSION)).get("username");
    }
}
