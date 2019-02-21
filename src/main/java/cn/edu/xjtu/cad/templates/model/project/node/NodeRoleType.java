package cn.edu.xjtu.cad.templates.model.project.node;

import java.util.Map;

public enum NodeRoleType {
    MANAGER("管理者"),OPERATOR("操作者"),VIEWER("观察者"),STRANGER("陌生人");

    private final String name;

    NodeRoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
