package cn.edu.xjtu.cad.templates.config;

import cn.edu.xjtu.cad.templates.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean BuildeLoginFilter(){
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setName("loginFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}

