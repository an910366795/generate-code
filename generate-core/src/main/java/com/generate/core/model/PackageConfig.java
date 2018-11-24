package com.generate.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Decription:包的信息
 * @Author: chengan.liang
 * @Date: 2018/4/13
 */
@Getter
@Setter
public class PackageConfig {

    //基本路径
    private String basePath = "E://auto-project";
    //基本代码路径
    private String baseSrc = "src.main.java";
    //maapper（dao）包的位置
    private String mapperPath = "web.dao";
    //service 包的位置
    private String servicePath = "web.service";
    //实体包的位置
    private String entityPath = "web.entity";
    //controller包的位置
    private String controllerPath = "web.controller";
    //util包的位置
    private String commonPath = "common.util";


}
