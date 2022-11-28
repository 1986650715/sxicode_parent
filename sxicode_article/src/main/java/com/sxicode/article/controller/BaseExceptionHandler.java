package com.sxicode.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result handler(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
