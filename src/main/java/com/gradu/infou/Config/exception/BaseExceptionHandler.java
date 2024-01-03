package com.gradu.infou.Config.exception;

import com.gradu.infou.Config.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected BaseResponse handleBaseException(BaseException ex){
        return new BaseResponse(ex.getBaseResponseStatus());
    }
}
