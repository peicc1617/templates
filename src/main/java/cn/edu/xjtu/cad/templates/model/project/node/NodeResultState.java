package cn.edu.xjtu.cad.templates.model.project.node;

public enum NodeResultState {
    UN_BIND("未绑定","btn-grey"),
    PENDING_VIEW("待查看","btn-grey"),
    TO_MODIFIED("待修改","btn-warning"),
    PENDING_CONFIRM("待确认","btn-warning"),
    ACCEPT("已确认","btn-success"),;

    static {
        NodeResultState.UN_BIND.nextStateArr=new NodeResultState[]{};
        NodeResultState.PENDING_VIEW.nextStateArr=new NodeResultState[]{NodeResultState.TO_MODIFIED,NodeResultState.ACCEPT};
        NodeResultState.TO_MODIFIED.nextStateArr=new NodeResultState[]{NodeResultState.ACCEPT};
        NodeResultState.PENDING_CONFIRM.nextStateArr=new NodeResultState[]{NodeResultState.TO_MODIFIED,NodeResultState.ACCEPT};
        NodeResultState.ACCEPT.nextStateArr=new NodeResultState[]{NodeResultState.TO_MODIFIED};
    }
    private final String text;

    private String style;
    private NodeResultState[] nextStateArr;
    NodeResultState(String text,String style) {
        this.text = text;
        this.style=style;
    }

    public String getText() {
        return text;
    }

    public String getStyle() {
        return style;
    }

    public NodeResultState[] getNextStateArr() {
        return nextStateArr;
    }


}
