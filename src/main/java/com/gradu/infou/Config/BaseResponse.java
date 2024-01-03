package com.gradu.infou.Config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.gradu.infou.Config.BaseResponseStatus.SUCCESS;

@Getter
public class BaseResponse<T> {//BaseResponse 객체를 사용할 때 성공, 실패 경우

    private Boolean isSuccess;
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    //요청에 성공한 경우
    public BaseResponse() {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
    }

    public BaseResponse(T result){
        this.isSuccess=SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    //요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status){
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
