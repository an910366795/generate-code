package com.generate.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Decription:表信息
 * @Author: chengan.liang
 * @Date: 2018/4/12
 */
@Getter
@Setter
public class TableInfo {
    //数据库名称
    private String datasourceName;
    //表名
    private String tableName;
    //表注释
    private String tabelComment;
    //实体名称
    private String entityName;
    //字段
    private List<Field> fieldList;
    //表包含的字段类型
    private Set<String> typeList = new HashSet<>();

    public TableInfo(String datasourceName,String tableName, String tabelComment) {
        this.datasourceName = datasourceName;
        this.tableName = tableName;
        this.tabelComment = tabelComment;
    }

}
