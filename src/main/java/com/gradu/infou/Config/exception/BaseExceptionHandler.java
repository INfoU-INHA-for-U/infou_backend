package com.gradu.infou.Config.exception;

import ch.qos.logback.core.status.ErrorStatus;
import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected BaseResponse handleBaseException(BaseException ex){
        return new BaseResponse(ex.getBaseResponseStatus());
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    protected BaseResponse validException(MissingServletRequestParameterException ex){
//        return new BaseResponse<>(BaseResponseStatus.REQUEST_BLANK);
//    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(ex, HttpHeaders.EMPTY,BaseResponseStatus.valueOf("BAD_REQUEST"),request,errors);

    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, BaseResponseStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        BaseResponse<Object> body = new BaseResponse(errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
