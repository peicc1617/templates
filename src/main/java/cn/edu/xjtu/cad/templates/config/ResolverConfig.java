package cn.edu.xjtu.cad.templates.config;

import cn.edu.xjtu.cad.templates.resolver.CurUserResolver;
import cn.edu.xjtu.cad.templates.resolver.CurUsernameResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResolverConfig implements WebMvcConfigurer {


    @Autowired
    CurUsernameResolver curUsernameResolver;

    @Autowired
    CurUserResolver curUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(curUsernameResolver);
        resolvers.add(curUserResolver);
    }

}
