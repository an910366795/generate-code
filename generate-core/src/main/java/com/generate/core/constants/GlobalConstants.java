package com.generate.core.constants;

/**
 * @Description:
 * @author: chengan.liang
 * @Date: 2018/11/19
 */
public class GlobalConstants {

    //项目目录
    public static final String PROJECT_JAVA_PATH = System.getProperty("user.dir") + "/src/main/java/";

    //默认的Mapper生成路径
//    public static final String DEFAULT_FILE_PATH_MAPPER = "/web/dao/mapper/";
    //默认的entity生成路径
//    public static final String DEFAULT_FILE_PATH_ENTITY = "/web/entity/";


    //默认的xml后缀名
    public static final String XML_SUFFIX = ".xml";
    //默认的Mapper后缀名
    public static final String MAPPER_SUFFIX = "Mapper";
    //java文件后缀
    public static final String JAVA_SUFFIX = ".java";


    //pom模板地址
    public static final String TEMPLATE_PATH_POM = "/templates/pom.ftl";
    //ignore模板地址
    public static final String TEMPLATE_PATH_IGNORE = "/templates/ignore.ftl";
    //基础mapper模板地址
    public static final String TEMPLATE_PATH_BASE_MAPPER = "/templates/baseMapper.ftl";
    //mapper模板地址
    public static final String TEMPLATE_PATH_MAPPER = "/templates/mapper.ftl";
    //entity模板地址
    public static final String TEMPLATE_PATH_ENTITY = "/templates/entity.ftl";
    //controller模板地址
    public static final String TEMPLATE_PATH_CONTROLLER = "/templates/controller.ftl";
    //service模板地址
    public static final String TEMPLATE_PATH_SERVICE = "/templates/service.ftl";
}
