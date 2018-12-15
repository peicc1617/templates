package cn.edu.xjtu.cad.templates.aop;

import cn.edu.xjtu.cad.templates.annotation.SystemControllerLog;
import cn.edu.xjtu.cad.templates.model.log.Log;
import cn.edu.xjtu.cad.templates.model.log.ProjectLog;
import cn.edu.xjtu.cad.templates.service.LogService;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;


@Aspect
@Component
public class SystemLogAspect {
    private static final ThreadLocal<Log> logThreadLocal =
            new NamedThreadLocal<>("ThreadLocal log");

    private static final ThreadLocal<String> currentUser=new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required=false)
    private HttpServletRequest request;


    @Autowired
    private LogService logService;


    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("@annotation(cn.edu.xjtu.cad.templates.annotation.SystemControllerLog)")
    public void controllerAspect(){}

    @Pointcut("execution(* cn.edu.xjtu.cad.templates.controller.*.*(..))")
    public void controllerPointerCut(){}


    /**
     * 前置通知，用于拦截Controller层的用户操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Map<String,String> userInfo = ((Map<String,String>)request.getSession().getAttribute("userInfo"));
        if(userInfo==null) return;
        String username =  userInfo.get("username");
        currentUser.set(username);
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint){
        String username = currentUser.get();
        if(Strings.isEmpty(username)) {
            return;
        }
        String remoteAddr=request.getRemoteAddr();//请求的IP
        Map<String,String[]> params=request.getParameterMap(); //请求提交的参数
        SystemControllerLog scl = null;
        String content = null;
        try {
            scl = getControllerMethodAnnotation(joinPoint);
            content = getLogContent(scl.content(),request.getParameterMap());
        }catch (Exception e){
            e.printStackTrace();
        }
        Log log=new ProjectLog(scl.logType(), scl.methodType(),username,content,remoteAddr);
        log.parseParamMap(params);
        logService.addLog(log);
        logThreadLocal.set(log);
    }

    /**
     * 异常通知，当发生异常的进行的操作
     * @param joinPoin
     * @param e
     */
    @AfterThrowing(value = "controllerPointerCut()",throwing = "e")
    public void doAferThrowing(JoinPoint joinPoin,Throwable e){
        Log log = logThreadLocal.get();
        if(log!=null){
            logService.removeLog(log);
        }
    }

    /**
     * 获取注解中对方法的描述信息，用于Controller层的注解
     * @param joinPoint
     * @return
     */
    private SystemControllerLog getControllerMethodAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method
                .getAnnotation(SystemControllerLog.class);
        return controllerLog;
    }

    /**
     * 解析注解
     * @param content
     * @param rparams
     * @return
     */
    private String getLogContent(String content, Map<String, String[]> rparams){
        StringBuilder sb = new StringBuilder();
        char[] chars = content.toCharArray();
        int len  = chars.length;
        for (int i = 0; i < len; ) {
            char c = chars[i];
            int endIndex =i+1;
            if(c=='$') {
                endIndex = getParamEndIndex(chars, i + 1, len);
            }
            if(endIndex!=i+1){
                sb.append(Arrays.toString(rparams.getOrDefault(content.substring(i+2,endIndex-1),new String[0])));
            }else {
                sb.append(chars[i]);
            }
            i = endIndex;

        }
        return sb.toString();
    }

    /**
     * @param chars
     * @param i
     * @param len
     * @return
     */
    private int getParamEndIndex(char[] chars, int i,int len ) {
        int p = 0;
        int endIndex = i+1;
        if(i<len&&chars[i]=='{'){
            //如果符合解析要求，那么继续解析
            p++;
            i++;
            for(;i<len;i++){
                char c = chars[i];
                if(c=='{'){
                    p++;
                }else if(c=='}'){
                    p--;
                }
                //如果成功解析，返回结束的index
                if(p==0){
                    endIndex=i+1;
                    break;
                }
            }
        }
        return endIndex;
    }


}
