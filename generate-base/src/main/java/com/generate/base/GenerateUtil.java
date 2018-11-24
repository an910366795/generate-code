package com.generate.base;

import cn.hutool.core.io.resource.ClassPathResource;
import com.generate.core.model.PackageConfig;
import com.generate.db.DbConfigerBuilder;
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

            //生成基本信息（包含包的信息，pom文件的信息）
            generateBase(properties);
            //生成数据库信息
            generateDb(properties);
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
     */
    private void generateBase(Properties properties) throws Exception {
        BaseConfigerBuilder baseConfigerBuilder = new BaseConfigerBuilder(properties, getDefaultPackConfig(properties), true);
        baseConfigerBuilder.mkdir().generateBaseFile();
    }

    /**
     * 生成数据库信息
     *
     * @param properties 配置信息
     */
    private void generateDb(Properties properties) throws Exception {
        String url = properties.get("db.url").toString();
        String username = properties.get("db.username").toString();
        String password = properties.get("db.password").toString();


        DbConfigerBuilder dbConfigerBuilder = new DbConfigerBuilder(getDefaultPackConfig(properties), new MysqlDbConfig(url, username, password), true);
        dbConfigerBuilder.mkdir().generateDbFile();
    }





}
