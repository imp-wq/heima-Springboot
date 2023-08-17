package com.itniuma.controller.utils;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    // 拦截所有异常信息
    @ExceptionHandler
    public Result doException(Exception exception) {
        // 控制台打印异常信息
        exception.printStackTrace();
        return new Result(false, "服务器故障，请稍后再试");
    }
}

