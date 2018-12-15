package cn.edu.xjtu.cad.templates.model;


public class Result<T> {


    private ResultStatus status;

    private String msg;
    private T data;

    public Result(ResultStatus status,String msg) {
        this.status = status;
        this.msg=msg;
    }

    public Result(ResultStatus status,String msg, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 返回成功
     *
     * @param msg 自定义消息
     * @param <T>
     * @return
     */
    public static <T> Result getSuccess(String msg) {
        return new Result(ResultStatus.SUCCESS,msg);
    }

    /**
     * 返回成功，携带数据
     * @param msg 自定义消息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> Result getSuccess(String msg,T data) {
        return new Result(ResultStatus.SUCCESS,msg,data);
    }

//    public static <T> Result getRedirect(String msg) {
//        return new Result(ResultStatus.REDIRECT,msg);
//    }


    /**
     * 返回跳转
     * @param msg 消息
     * @param url 跳转的url
     * @param <T>
     * @return
     */
    public static <T> Result getRedirect(String msg,String url) {
        return new Result(ResultStatus.REDIRECT,msg,url);
    }


    /**
     * 返回权限
     * @param <T>
     * @return
     */
    public static <T> Result getNoAuth() {
        return new Result(ResultStatus.NO_AUTH, "当前用户无权限");
    }


    /**
     * 返回当前用户未登录
     * @param <T>
     * @return
     */
    public static <T> Result getNoLogin(){
        return new Result(ResultStatus.NO_LOGIN,"当前用户未登录");
    }

    public static <T> Result getError(){
        return new Result(ResultStatus.INTERNAL_ERROR,"服务器内部发生错误");
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

