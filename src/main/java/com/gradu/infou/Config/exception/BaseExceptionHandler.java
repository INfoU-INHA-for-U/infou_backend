package com.gradu.infou.Config.exception;

import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected BaseResponse handleBaseException(BaseException ex){
        return new BaseResponse(ex.getBaseResponseStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected BaseResponse validException(MissingServletRequestParameterException ex){
        return new BaseResponse<>(BaseResponseStatus.REQUEST_BLANK);
    }
}
