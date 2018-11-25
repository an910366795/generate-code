package com.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:全局通用响应封装
 * @author: ${author}
 * @Date: ${now}
 */
@Getter
@Setter
public class CommonResult implements Serializable {

    /**
     * 响应码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;


    /**
     * 请求数据结果
     */
    private Map<String, Object> data = new HashMap<>();


    /**
     * 添加返回数据
     *
     * @param key
     * @param value
     */
    public com.common.CommonResult addData(String key, Object value) {
        data.put(key, value);
        return this;
    }


}
