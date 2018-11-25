package com.generate.common;

import com.generate.core.AbstractBuilder;
import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @author: chengan.liang
 * @Date: 2018/11/24
 */
@Slf4j
public class CommonBuilder extends AbstractBuilder {


    //全局属性
    private Properties properties;


    public CommonBuilder(PackageConfig packageConfig, Properties properties) {
        this.properties = properties;
        this.properties = properties;
        this.packageConfig = packageConfig;
    }

    public CommonBuilder(PackageConfig packageConfig, Properties properties, boolean isForceCover) {
        this.properties = properties;
        this.packageConfig = packageConfig;
        this.isForceCover = isForceCover;
    }


    @Override
    public AbstractBuilder generateFile() throws Exception {
        outputFile("commonResult", null);
        outputFile("msgConstants", null);
        outputFile("resultUtil", null);
        outputFile("commonexception", null);
        outputFile("paramException", null);
        return this;
    }

    @Override
    public String getPath() {
        String path = packageConfig.getBasePath() + "/" + packageConfig.getBaseJava() + "/" + packageConfig.getCommonPath();
        path = path.replaceAll("\\.", "/");
        return path;
    }

    @Override
    public void outputFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getTableInfo(null);

        String filePath = "";
        String templatePath = "";

        if (type.equals("commonResult")) {
            filePath = getPath() + "//CommonResult" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_COMMONRESULT;
        } else if (type.equals("msgConstants")) {
            filePath = getPath() + "//constants" + "//MsgConstants" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_MSGCONSTANTS;
        } else if (type.equals("resultUtil")) {
            filePath = getPath() + "//util" + "//ResultUtil" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_RESULTUTIL;
        } else if (type.equals("commonexception")) {
            filePath = getPath() + "//exception" + "//CommonException" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_COMMONEXCEPTION;
        } else if (type.equals("paramException")) {
            filePath = getPath() + "//exception" + "//ParamException" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_PARAMEXCEPTION;
        }

        //保存文件
        saveFile(filePath, templatePath, isForceCover, infoMap);
    }
}
