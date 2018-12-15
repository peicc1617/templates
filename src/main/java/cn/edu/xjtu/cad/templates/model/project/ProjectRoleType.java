package cn.edu.xjtu.cad.templates.model.project;

public enum ProjectRoleType {
    CREATOR("创建者"),SUPER_MANAGER("超级管理员"),MEMBER("普通成员"),APPLY("申请者");

    private final String name;

    ProjectRoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
