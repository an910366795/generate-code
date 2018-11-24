package com.generate.base;

import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:基础文件生成
 * @author: chengan.liang
 * @Date: 2018/11/24
 */
@Slf4j
public class BaseConfigerBuilder {


    //全局属性
    private Properties properties;
    //包配置
    private PackageConfig packageConfig;
    //是否强制覆盖已生成的文件
    private boolean isForceCover = false;


    public BaseConfigerBuilder(Properties properties, PackageConfig packageConfig) {
        this.properties = properties;
        this.properties = properties;
        this.packageConfig = packageConfig;
    }

    public BaseConfigerBuilder(Properties properties, PackageConfig packageConfig, boolean isForceCover) {
        this.properties = properties;
        this.packageConfig = packageConfig;
        this.isForceCover = isForceCover;
    }

    /**
     * 生成基础文件夹
     */
    public BaseConfigerBuilder mkdir() {
        String basePath = packageConfig.getBasePath();

        File file = new File(basePath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                log.info("创建了base目录：【{}】", file.getPath());
            }

        }

        return this;
    }

    /**
     * 生成基础文件
     */
    public BaseConfigerBuilder generateBaseFile() throws Exception {

        outputFile("pom");
        outputFile("ignore");
        return this;
    }

    private void outputFile(String type) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getInfoMap();
        //freemarker设置
        Configuration configuration = new Configuration();
        //模板编码
        configuration.setDefaultEncoding("UTF-8");
        //路径
        configuration.setClassForTemplateLoading(this.getClass(), "/");

        String filePath = "";
        String templatePath = "";


        if (type.equals("pom")) {
            filePath = packageConfig.getBasePath() + "//pom" + GlobalConstants.XML_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_POM;
        } else if (type.equals("ignore")) {
            filePath = packageConfig.getBasePath() + "//.gitignore";
            templatePath = GlobalConstants.TEMPLATE_PATH_IGNORE;
        }

        //获取freemarker模板
        File outFile = new File(filePath);
        if (!outFile.exists() || (outFile.exists() && isForceCover)) {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            Template template = configuration.getTemplate(templatePath);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            template.process(infoMap, new OutputStreamWriter(fileOutputStream, "UTF-8"));
            fileOutputStream.close();
            log.info("输出文件【" + outFile.getName() + "】");
        }
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


        return info;
    }

}
