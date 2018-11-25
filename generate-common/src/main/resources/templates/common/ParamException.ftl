package com.common.exception;

/**
 * @Description:参数异常
 * @author: ${author}
 * @Date: ${now}
 */
public class ParamException extends RuntimeException {
    public ParamException(String message) {
        super(message);
    }
}
