package cn.edu.xjtu.cad.templates.annotation;

import cn.edu.xjtu.cad.templates.model.log.LogType;
import cn.edu.xjtu.cad.templates.model.log.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemControllerLog {
    String content();
    LogType logType() ;
    MethodType methodType() ;
}
