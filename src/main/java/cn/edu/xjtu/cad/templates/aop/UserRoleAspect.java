package cn.edu.xjtu.cad.templates.aop;

import cn.edu.xjtu.cad.templates.model.project.User;
import cn.edu.xjtu.cad.templates.service.LogService;
import cn.edu.xjtu.cad.templates.service.ProjectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Aspect
@Component
public class UserRoleAspect implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Autowired
    private ProjectService projectService;

    private static final ThreadLocal<User> currentUser =new NamedThreadLocal<>("ThreadLocal user");
    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("@annotation(cn.edu.xjtu.cad.templates.annotation.UserRoleFilter)")
    public void serviceAspect(){}

    @Pointcut("execution(* cn.edu.xjtu.cad.templates.service.*.*(..))")
    public void servicePointerCut(){}


    /**
     * 前置通知，用于拦截Controller层的用户操作的开始时间
     * @param joinPoint 切点joinPoint.getSignature().getDeclaringTypeName()
     * @throws InterruptedException
     */
    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Class aClass = joinPoint.getThis().getClass();
        System.out.println("@Before：模拟权限检查...");
        System.out.println("@Before：目标方法为：" +
                joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName());
        System.out.println("@Before：参数为：" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("@Before：被织入的目标对象为：" + joinPoint.getTarget());

    }

    @After("serviceAspect()")
    public void doAfter(JoinPoint joinPoint) throws InterruptedException{
        System.out.println(joinPoint);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserRoleAspect.applicationContext = applicationContext;
    }

    public static <T> T getObject(Object object) throws Exception{
        T t = (T)object;
        return t;
    }

}
