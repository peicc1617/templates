package cn.edu.xjtu.cad.templates.annotation;

import cn.edu.xjtu.cad.templates.model.project.ProjectRoleType;
import cn.edu.xjtu.cad.templates.model.project.node.NodeRoleType;

public @interface UserRoleMethodMap {
    /**
     * 函数名
     * @return
     */
    String methodName() default "";

    /**
     * 函数对应的项目权限
     * @return
     */
    ProjectRoleType[] projectRoleTypes() default {};

    /**
     * 函数对应的节点权限
     * @return
     */
    NodeRoleType[] nodeRoleTypes() default {};

}
