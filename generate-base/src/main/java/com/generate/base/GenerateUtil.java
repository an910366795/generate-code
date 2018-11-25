package com.generate.base;

import cn.hutool.core.io.resource.ClassPathResource;
import com.generate.common.CommonBuilder;
import com.generate.common.ControllerBuilder;
import com.generate.common.ServiceBuilder;
import com.generate.core.model.PackageConfig;
import com.generate.db.DataSourceBuilder;
import com.generate.db.MysqlDbConfig;

import java.util.Properties;

/**
 * @Description:
 * @author: chengan.liang
 * @Date: 2018/11/19
 */
public class GenerateUtil {


    public static void main(String[] args) {
        GenerateUtil generateUtil = new GenerateUtil();
        generateUtil.beginGenerate();
    }

    /**
     * 开始生成代码
     */
    private void beginGenerate() {
        try {
            //读取properties属性
            ClassPathResource resource = new ClassPathResource("application.properties");
            Properties properties = new Properties();
            properties.load(resource.getStream());

            String url = properties.get("db.url").toString();
            String username = properties.get("db.username").toString();
            String password = properties.get("db.password").toString();
            MysqlDbConfig dbConfig = new MysqlDbConfig(url, username, password);

            //生成基本信息（包含包的信息，pom文件的信息）
            generateBase(properties, dbConfig);
            //生成common信息
            generateCommon(properties);
            //生成数据库信息
            generateDb(properties, dbConfig);
            //生成controller信息
            generateController(properties, dbConfig);
            //生成service信息
            generateService(properties, dbConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取包信息
     *
     * @param properties 配置信息
     */
    private PackageConfig getDefaultPackConfig(Properties properties) {
        PackageConfig packageConfig = new PackageConfig();
        Object basePath = properties.get("base_path");

        if (basePath != null && basePath != "") {
            packageConfig.setBasePath(basePath + "");
        }
        return packageConfig;
    }

    /**
     * 生成基础包信息
     *
     * @param properties 配置信息
     * @param dbConfig
     */
    private void generateBase(Properties properties, MysqlDbConfig dbConfig) throws Exception {
        BaseBuilder baseBuilder = new BaseBuilder(properties, getDefaultPackConfig(properties), dbConfig, true);
        baseBuilder.mkDir().generateFile();
    }

    /**
     * 生成数据库信息
     *
     * @param properties 配置信息
     */
    private void generateDb(Properties properties, MysqlDbConfig mysqlDbConfig) throws Exception {

        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(getDefaultPackConfig(properties), mysqlDbConfig, true);
        dataSourceBuilder.mkDir().generateFile();
    }


    /**
     * 生成common文件信息
     *
     * @param properties 配置信息
     */
    private void generateCommon(Properties properties) throws Exception {

        CommonBuilder commonBuilder = new CommonBuilder(getDefaultPackConfig(properties), properties, true);
        commonBuilder.mkDir().generateFile();
    }

    /**
     * 生成controller信息
     */
    private void generateController(Properties properties, MysqlDbConfig dbConfig) throws Exception {
        ControllerBuilder controllerBuilder = new ControllerBuilder(getDefaultPackConfig(properties), dbConfig, true);
        controllerBuilder.mkDir().generateFile();
    }

    /**
     * 生成service文件
     */
    private void generateService(Properties properties, MysqlDbConfig dbConfig) throws Exception {
        ServiceBuilder serviceBuilder = new ServiceBuilder(getDefaultPackConfig(properties), dbConfig, true);
        serviceBuilder.mkDir().generateFile();
    }


}
