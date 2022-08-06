package com.fsnteam.fsnweb.handler;

import com.fsnteam.fsnweb.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理，全部异常可处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error();
    }

    /**
     * 算术异常
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.arithmeticException();
    }

    @ExceptionHandler(BussinessException.class)
    @ResponseBody
    public Result error(BussinessException e) {
        e.printStackTrace();
        return Result.error().code(e.getCode())
                .message(e.getMessage());
    }
}
