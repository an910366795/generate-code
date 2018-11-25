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
    private String baseJava = "src.main.java";
    //基本资源路径
    private String baseResource = "src.main.resources";
    //主类路径
    private String mainPath = "com";
    //maapper（dao）包的位置
    private String mapperPath = "com.web.dao";
    //service 包的位置
    private String servicePath = "com.web.service";
    //实体包的位置
    private String entityPath = "com.web.entity";
    //controller包的位置
    private String controllerPath = "com.web.controller";
    //util包的位置
    private String commonPath = "com.common";


}
