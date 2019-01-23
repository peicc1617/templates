package cn.edu.xjtu.cad.templates.model.project.node;

public enum NodeResultState {
    UN_BIND("未绑定"),PENDING_VIEW("查看"),TO_MODIFIED("待修改"),PENDING_CONFIRM("待确认"),ACCEPT("已确认"),;

    private final String text;

    NodeResultState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
