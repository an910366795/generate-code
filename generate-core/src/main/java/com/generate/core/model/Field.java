package com.generate.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Decription:
 * @Author: chengan.liang
 * @Date: 2018/4/12
 */
@Getter
@Setter
public class Field {

    //字段名称
    private String filedName;
    //字段类型
    private String fileType;
    //字段含义
    private String fieldComment;
    //属性名称
    private String propertyName;
    //包装类型
    private String propertyType;
    //主键标识
    private boolean key;

    public Field(String filedName, String fileType, String fieldComment) {
        this.filedName = filedName;
        this.fileType = fileType;
        this.fieldComment = fieldComment;
    }

}
