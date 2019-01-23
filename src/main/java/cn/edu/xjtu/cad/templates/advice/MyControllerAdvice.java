package cn.edu.xjtu.cad.templates.advice;

import cn.edu.xjtu.cad.templates.aop.MyException;
import cn.edu.xjtu.cad.templates.model.Result;
import cn.edu.xjtu.cad.templates.model.ResultCode;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MyControllerAdvice {
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}


    /**
     * 全局异常捕捉处理，处理系统异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(MyException ex) {
        return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
    }

    /**
     * 处理自定义异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Result MyExceptionHandler(MyException ex) {
        return Result.failure(ex);
    }
}
