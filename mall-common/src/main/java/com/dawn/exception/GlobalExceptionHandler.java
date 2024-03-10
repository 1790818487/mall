package com.dawn.exception;


import com.dawn.enums.login.ResponseEnum;
import com.dawn.util.R;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //处理所有其它异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object CommonExceptionHandler(Exception e) {
        e.printStackTrace();
        return R.error(ResponseEnum.UNKNOW_EXCEPTION);
    }

    //处理shiro认证失败抛出的异常
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public R UnauthenticatedHandler(AuthorizationException e) {
        e.printStackTrace();
        return R.error(ResponseEnum.USER_UN_LOGIN);
    }

    //处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R BindExceptionHandler(BindException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return R.error(ResponseEnum.BAD_PARAMETER,message);
    }

    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return R.error(ResponseEnum.BAD_PARAMETER,message);
    }

    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R responseDto(MethodArgumentNotValidException e){
        String message=e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return R.error(ResponseEnum.BAD_PARAMETER,message);
    }

}
