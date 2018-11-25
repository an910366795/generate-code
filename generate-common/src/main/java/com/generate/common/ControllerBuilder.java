package com.generate.common;

import com.generate.core.AbstractBuilder;
import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import com.generate.db.MysqlDbConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description:
 * @author: chengan.liang
 * @Date: 2018/11/25
 */
@Slf4j
public class ControllerBuilder extends AbstractBuilder {

    //包配置
    private PackageConfig packageConfig;
    //是否强制覆盖已生成的文件
    private boolean isForceCover = false;
    //数据库配置
    private MysqlDbConfig mysqlDbConfig;


    public ControllerBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig, boolean isForceCover) {
        this.packageConfig = packageConfig;
        this.isForceCover = isForceCover;
        this.mysqlDbConfig = mysqlDbConfig;
    }

    @Override
    public AbstractBuilder generateFile() throws Exception {
        outputFile("application", null);
        outputFile("baseController", null);
        outputFile("errorController", null);

        for (TableInfo tableInfo : mysqlDbConfig.getTableInfo()) {
            outputFile("resultUtil", tableInfo);
        }
        return this;
    }

    @Override
    public String getPath() {
        String path = packageConfig.getBasePath() + "/" + packageConfig.getBaseJava() + "/" + packageConfig.getControllerPath();
        path = path.replaceAll("\\.", "/");
        return path;
    }

    @Override
    public void outputFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getTableInfo(tableInfo);

        String filePath = "";
        String templatePath = "";
        if (type.equals("application")) {
            filePath = (packageConfig.getBasePath() + "/" + packageConfig.getBaseJava() + "/" + packageConfig.getMainPath() ).replaceAll("\\.","/")
                    + "//Application" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_APPLICATION;
        } else if (type.equals("baseController")) {
            filePath = getPath() + "//BaseController" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_BASECONTROLLER;
        } else if (type.equals("errorController")) {
            filePath = getPath() + "//ErrorController" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_ERRORCONTROLLER;
        } else {
            String entityName = infoMap.get("entityName") + "";
            filePath = getPath() + "//" + entityName + "Controller" + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_OTHERCONTROLLER;
        }

        //保存文件
        saveFile(filePath, templatePath, isForceCover, infoMap);
    }
}
