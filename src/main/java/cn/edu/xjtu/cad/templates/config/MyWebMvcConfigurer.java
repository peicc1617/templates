package cn.edu.xjtu.cad.templates.config;

import cn.edu.xjtu.cad.templates.filter.RoleInjectInterceptor;
import cn.edu.xjtu.cad.templates.filter.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    public RoleInjectInterceptor projectRoleIntercepter(){
        return new RoleInjectInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(projectRoleIntercepter()).addPathPatterns("/api/project/**").addPathPatterns("/project/**");
    }
}
