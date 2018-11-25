package com.web.controller;

import com.common.CommonResult;
import com.common.constants.MsgConstants;
import com.common.exception.CommonException;
import com.common.exception.ParamException;
import com.common.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description: 公共异常处理
 * @author: ${author}
 * @Date: ${now}
 */
@ControllerAdvice
@Slf4j
public class ErrorController {

    /**
     * 公共异常处理
     */
    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handlerCommonException(CommonException e) {
        log.error(e.getMessage(), e);

        return ResultUtil.fail(e.getMessage());
    }

    /**
     * 参数异常处理
     */
    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handlerParamException(ParamException e) {
        log.error(e.getMessage(), e);

        return ResultUtil.fail(MsgConstants.MSG_1002);
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handlerException(Exception e) {
        log.error(MsgConstants.MSG_1001, e);

        return ResultUtil.fail(MsgConstants.MSG_1001);
    }


}
