package cn.edu.xjtu.cad.templates.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class LoginFilter implements Filter {
    private final static String LOGIN_URL = "/webresources/userLogin.jsp";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Map<String,String> userInfo = (Map<String,String>) request.getSession().getAttribute("userInfo");
        if(userInfo==null){
            //用户未登录
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            StringBuilder redirectURL = new StringBuilder(LOGIN_URL).append("?serviceURL=");
            if(request.getMethod()=="GET"){
                redirectURL.append(URLEncoder.encode(getFullServiceURL(request.getRequestURL(),request.getParameterMap()),"UTF-8"));
            }else {
                redirectURL.append(URLEncoder.encode(request.getRequestURL().toString(),"UTF-8"));
            }
            response.sendRedirect(redirectURL.toString());
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
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


    @Override
    public void destroy() {

    }
}
