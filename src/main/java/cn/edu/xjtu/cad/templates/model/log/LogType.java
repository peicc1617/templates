package cn.edu.xjtu.cad.templates.model.log;

public enum LogType {
    PROJECT("project_log"),
    STEP("project_log"),
    NODE("project_log"),
    RESULT("project_log"),
    USER("project_log"),
    REFER("refer_log");
    private final String dataBaseName;

    LogType(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

}
