package cn.edu.xjtu.cad.templates.config;

import cn.edu.xjtu.cad.templates.dao.UserMapper;
import cn.edu.xjtu.cad.templates.model.project.ProjectRole;
import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRole;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class BeanConfig {

    @Bean(name = "projectManagerAPI")
    API getProjectManagerAPI(@Value("${cai-api.project-manager.host}") String host,
                             @Value("${cai-api.project-manager.port}") String port,
                             @Value("${cai-api.project-manager.context}") String context,
                             @Value("${cai-api.project-manager.version}") String version){
        return new API(host,port,context,version);
    }

    @Bean(name = "templatesAPI")
    API getTemplatesAPI(@Value("${cai-api.templates.host}") String host,
                        @Value("${cai-api.templates.port}") String port,
                        @Value("${cai-api.templates.context}") String context,
                        @Value("${cai-api.templates.version}") String version){
        return new API(host,port,context,version);
    }

}
