package cn.edu.xjtu.cad.templates.annotation;

import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserRoleFilter {
    /**
     * 函数名
     * @return
     */
    String methodName() default "";

    /**
     * 可访问该函数的项目权限
     * @return
     */
    ProjectRoleType[] allowedProjectRoleTypes() default {};

    /**
     * 可访问的该函数的节点权限
     * @return
     */
    NodeRoleType[] allowedNodeRoleTypes() default {};

    /**
     * 禁止方法该函数的项目权限
     * @return
     */
    ProjectRoleType[] bannedProjectRoleTypes() default {};

    /**
     * 禁止访问该函数的节点权限
     * @return
     */
    ProjectRoleType[] bannedNodeRoleTypes() default {};
}
