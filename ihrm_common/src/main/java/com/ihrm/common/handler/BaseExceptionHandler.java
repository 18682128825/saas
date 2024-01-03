package com.ihrm.common.handler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义公共异常处理器
 * 1、声明异常处理器
 * 2、对异常统一处理
 */
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){

        //如果异常跟自定义异常类名一样
        if(e.getClass() == CommonException.class){
            //类型转化
            CommonException ce = (CommonException) e;
            Result result = new Result(ce.getResultCode());
            return result;
        }else {
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }

    }
}
