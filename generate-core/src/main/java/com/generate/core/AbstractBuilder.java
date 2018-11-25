package com.generate.core;

import cn.hutool.core.date.DateUtil;
import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: chengan.liang
 * @Date: 2018/11/25
 */
@Slf4j
public abstract class AbstractBuilder {

    //包配置
    public PackageConfig packageConfig;
    //是否强制覆盖已生成的文件
    public boolean isForceCover = false;

    /**
     * 输出文件
     *
     * @param filePath     输出文件路径
     * @param templatePath 模板路径
     * @param isForceCover 是否强制覆盖
     * @param infoMap      信息map
     */
    public void saveFile(String filePath, String templatePath, boolean isForceCover, Map<String, Object> infoMap) throws Exception {
        //获取freemarker模板
        //freemarker设置
        Configuration configuration = new Configuration();
        //模板编码
        configuration.setDefaultEncoding("UTF-8");
        //路径
        configuration.setClassForTemplateLoading(this.getClass(), "/");


        File outFile = new File(filePath);
        if (!outFile.exists() || (outFile.exists() && isForceCover)) {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            Template template = configuration.getTemplate(templatePath);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            template.process(infoMap, new OutputStreamWriter(fileOutputStream, "UTF-8"));
            fileOutputStream.close();
            log.info("输出文件【{}】,【{}】", outFile.getName(), outFile.getPath());
        }

    }

    /**
     * 封装数据库表信息
     *
     * @param tableInfo 表信息
     */
    public Map<String, Object> getTableInfo(TableInfo tableInfo) {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        if (tableInfo != null) {
            //表名
            objectHashMap.put("table", tableInfo);
            String entityName = tableInfo.getEntityName();
            //实体名称
            objectHashMap.put("entityName", entityName);

            String paramName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1, entityName.length());
            objectHashMap.put("paramName", paramName);
            //mapper名字
            objectHashMap.put("mapperName", tableInfo.getEntityName() + GlobalConstants.MAPPER_SUFFIX);
            //service名字
            objectHashMap.put("serviceName", tableInfo.getEntityName());
            //字段
            objectHashMap.put("fieldList", tableInfo.getFieldList());
            //数据库名称
            objectHashMap.put("datasourceName", tableInfo.getDatasourceName());
        }
        //包信息
        objectHashMap.put("package", packageConfig);
        //当前时间
        objectHashMap.put("now", DateUtil.now());
        //作者
        objectHashMap.put("author", "chengan.liang");

        return objectHashMap;
    }

    public abstract AbstractBuilder generateFile() throws Exception;

    public abstract String getPath();

    public abstract void outputFile(String type, TableInfo tableInfo) throws Exception;

    public AbstractBuilder mkDir() {
        String basePath = getPath();

        File file = new File(basePath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                log.info("创建了base目录：【{}】", file.getPath());
            }
        }
        return this;
    }

}
