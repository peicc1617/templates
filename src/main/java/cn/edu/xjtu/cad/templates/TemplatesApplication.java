package cn.edu.xjtu.cad.templates;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"cn.edu.xjtu.cad.templates.controller","cn.edu.xjtu.cad.templates"})
@MapperScan("cn.edu.xjtu.cad.templates.dao")
public class TemplatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplatesApplication.class, args);
    }
}
