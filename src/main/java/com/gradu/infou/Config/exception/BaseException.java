package com.gradu.infou.Config.exception;

import com.gradu.infou.Config.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private final BaseResponseStatus baseResponseStatus;
}
