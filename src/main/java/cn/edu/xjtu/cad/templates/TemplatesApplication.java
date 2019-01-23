 package cn.edu.xjtu.cad.templates;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"cn.edu.xjtu.cad.templates.controller",
        "cn.edu.xjtu.cad.templates.service",
        "cn.edu.xjtu.cad.templates.aop",
        "cn.edu.xjtu.cad.templates.config",
        "cn.edu.xjtu.cad.templates.resolver",
        "cn.edu.xjtu.cad.templates.advice"})
@MapperScan("cn.edu.xjtu.cad.templates.dao")
public class TemplatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplatesApplication.class, args);
    }
}
