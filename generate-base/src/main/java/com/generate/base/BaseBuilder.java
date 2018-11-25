package com.generate.base;

import com.generate.core.AbstractBuilder;
import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import com.generate.db.MysqlDbConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:基础文件生成
 * @author: chengan.liang
 * @Date: 2018/11/24
 */
@Slf4j
public class BaseBuilder extends AbstractBuilder {

    //全局属性
    private Properties properties;
    //数据库属性
    private MysqlDbConfig mysqlDbConfig;

    public BaseBuilder(Properties properties, PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig) {
        this.properties = properties;
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
    }

    public BaseBuilder(Properties properties, PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig, boolean isForceCover) {
        this.properties = properties;
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
        this.isForceCover = isForceCover;
    }

    /**
     * 获取基础信息
     */
    private Map<String, Object> getInfoMap() {
        Map<String, Object> info = new HashMap<>();

        //是否初始化数据库
        String dbUrl = (String) properties.get("db.url");
        boolean initDB = StringUtils.isNotBlank(dbUrl) ? true : false;
        info.put("initDB", initDB);
        if (initDB) {
            info.put("dbUrl", dbUrl);
            info.put("dbUsername", properties.get("db.username"));
            info.put("dbPassword", properties.get("db.password"));
        }

        info.put("basePath", packageConfig.getBasePath());
        return info;
    }


    @Override
    public AbstractBuilder generateFile() throws Exception {
        //pom文件输出
        outputFile("pom", null);
        //ignore文件输出
        outputFile("ignore", null);
        //日志文件输出
        outputFile("logback", null);
        //resource文件输出
        outputFile("yml", null);
        return this;
    }

    @Override
    public String getPath() {
        return packageConfig.getBasePath();
    }

    @Override
    public void outputFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getInfoMap();

        String filePath = "";
        String templatePath = "";

        if (type.equals("pom")) {
            filePath = getPath() + "//pom" + GlobalConstants.XML_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_POM;
        } else if (type.equals("ignore")) {
            filePath = getPath() + "//.gitignore";
            templatePath = GlobalConstants.TEMPLATE_PATH_IGNORE;
        } else if (type.equals("logback")) {
            filePath = getPath() + "//logback" + GlobalConstants.XML_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_LOGBACK;
        } else if (type.equals("yml")) {
            filePath = (getPath() + "/" + packageConfig.getBaseResource()).replaceAll("\\.", "/") + "//application.yml";
            templatePath = GlobalConstants.TEMPLATE_PATH_YML;
        }
        //保存文件
        saveFile(filePath, templatePath, isForceCover, infoMap);
    }
}
