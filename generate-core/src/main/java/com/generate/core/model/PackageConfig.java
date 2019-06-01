package com.generate.core.model;

import lombok.Setter;

/**
 * @Decription:包的信息
 * @Author: chengan.liang
 * @Date: 2018/4/13
 */
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
    private String mapperPath = "web.dao";
    //service 包的位置
    private String servicePath = "web.service";
    //实体包的位置
    private String entityPath = "web.entity";
    //controller包的位置
    private String controllerPath = "web.controller";
    //util包的位置
    private String commonPath = "common";


    public String getBasePath() {
        return basePath;
    }

    public String getBaseJava() {
        return baseJava;
    }

    public String getBaseResource() {
        return baseResource;
    }

    public String getMainPath() {
        return mainPath;
    }

    public String getMapperPath() {
        return mainPath + "." + mapperPath;
    }

    public String getServicePath() {
        return mainPath + "." + servicePath;
    }

    public String getEntityPath() {
        return mainPath + "." + entityPath;
    }

    public String getControllerPath() {
        return mainPath + "." + controllerPath;
    }

    public String getCommonPath() {
        return mainPath + "." + commonPath;
    }

}
