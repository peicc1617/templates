package cn.edu.xjtu.cad.templates.filter;

import cn.edu.xjtu.cad.templates.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;


public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    User user;

    private final static String LOGIN_URL = "/webresources/userLogin.jsp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String> userInfo = (Map<String,String>) request.getSession().getAttribute("userInfo");
        if(userInfo==null){
            //用户未登录
            StringBuilder redirectURL = new StringBuilder(LOGIN_URL).append("?serviceURL=");
            if(request.getMethod().equals("GET")){
                redirectURL.append(URLEncoder.encode(getFullServiceURL(request.getRequestURL(),request.getParameterMap()),"UTF-8"));
            }else {
                redirectURL.append(URLEncoder.encode(request.getRequestURL().toString(),"UTF-8"));
            }
            response.sendRedirect(redirectURL.toString());
            return false;
        }
        String userIDStr = userInfo.get("id");
        user.setUserID(Long.valueOf(userIDStr));
        return true;
    }
    /**
     * 获取完整的ServiceURL，
     * 通过将请求URL与参数列表进行拼接得到完成的URL,只适用于GET请求
     * @param requestURL 请求的URL
     * @param parameterMap 请求的参数列表
     * @return
     */
    private String getFullServiceURL(StringBuffer requestURL, Map<String,String[]> parameterMap) {
        if(!parameterMap.isEmpty()){
            requestURL.append('?');
            parameterMap.forEach((paramName,paramValues)->{
                if(paramValues!=null){
                    for (String value : paramValues) {
                        requestURL.append(paramName).append('=').append(value).append('&');
                    }
                }
            });
            requestURL.deleteCharAt(requestURL.length()-1);
        }
        return requestURL.toString();
    }

}
