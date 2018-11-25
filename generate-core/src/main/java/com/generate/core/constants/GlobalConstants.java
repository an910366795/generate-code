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
    //日志模板地址
    public static final String TEMPLATE_PATH_LOGBACK = "/templates/logback.ftl";
    //yml模板地址
    public static final String TEMPLATE_PATH_YML = "/templates/yml.ftl";
    //commonResult模板地址
    public static final String TEMPLATE_PATH_COMMONRESULT = "/templates/common/CommonResult.ftl";
    //MsgConstants模板地址
    public static final String TEMPLATE_PATH_MSGCONSTANTS = "/templates/common/MsgConstants.ftl";
    //ResultUtil模板地址
    public static final String TEMPLATE_PATH_RESULTUTIL = "/templates/common/ResultUtil.ftl";
    //ParamException模板地址
    public static final String TEMPLATE_PATH_PARAMEXCEPTION = "/templates/common/Paramexception.ftl";
    //CommonException模板地址
    public static final String TEMPLATE_PATH_COMMONEXCEPTION = "/templates/common/CommonException.ftl";
    //基础mapper模板地址
    public static final String TEMPLATE_PATH_BASE_MAPPER = "/templates/baseMapper.ftl";
    //mapper模板地址
    public static final String TEMPLATE_PATH_MAPPER = "/templates/mapper.ftl";
    //entity模板地址
    public static final String TEMPLATE_PATH_ENTITY = "/templates/entity.ftl";
    //application模板地址
    public static final String TEMPLATE_PATH_APPLICATION = "/templates/common/Application.ftl";
    //BaseController模板地址
    public static final String TEMPLATE_PATH_BASECONTROLLER = "/templates/controller/BaseController.ftl";
    //ErrorController模板地址
    public static final String TEMPLATE_PATH_ERRORCONTROLLER = "/templates/controller/ErrorController.ftl";
    //OtherController模板地址
    public static final String TEMPLATE_PATH_OTHERCONTROLLER= "/templates/controller/OtherController.ftl";
    //service模板地址
    public static final String TEMPLATE_PATH_SERVICE = "/templates/service/OtherService.ftl";
}
