package com.generate.db;

import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class DbConfigerBuilder {

    //包配置
    private PackageConfig packageConfig;
    //数据库配置
    private MysqlDbConfig mysqlDbConfig;
    //排除的表
    private List<String> excludeTableList = new ArrayList<>();
    //包含的表
    private List<String> includeTableList = new ArrayList<>();
    //是否强制覆盖已生成的文件
    private boolean isForceCover = false;


    public DbConfigerBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig) {
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
    }

    public DbConfigerBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig,boolean isForceCover) {
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
        this.isForceCover = isForceCover;
    }


    //增加排除的表
    public void addExcludeTable(String tableName) {
        excludeTableList.add(tableName);
    }

    //增加包含的表
    public DbConfigerBuilder addIncludeTable(String... tableNames) {
        for (String tableName : tableNames) {
            includeTableList.add(tableName);
        }
        return this;
    }


    public DbConfigerBuilder mkdir() {

        //Mapper目录
        File mapperDir = new File(getMapperPath());
        if (!mapperDir.exists()) {
            boolean mkdir = mapperDir.mkdirs();
            if (mkdir) {
                log.info("创建了mapper目录：【{}】", mapperDir.getPath());
            }
        }
        //entity目录
        File entityDir = new File(getEntityPath());
        if (!entityDir.exists()) {
            boolean mkdir = entityDir.mkdirs();
            if (mkdir) {
                log.info("创建了entity目录：【{}】", entityDir.getPath());
            }
        }

        return this;
    }

    /**
     * 根据模板生成相应的文件
     */
    public List<TableInfo> generateDbFile() throws Exception {
        //获取表信息并处理
        List<TableInfo> autoTableList = processTableList(mysqlDbConfig.getTableInfo());

        //输出基础mapper
        this.outPutFile("baseMapper", null);

        for (TableInfo tableInfo : autoTableList) {
            //输出mapper文件
            this.outPutFile("mapper", tableInfo);
            //输出entity文件
            this.outPutFile("entity", tableInfo);
        }
        return autoTableList;
    }

    /**
     * 将表信息封装入hashmap中
     */
    private Map<String, Object> getObjectdInfo(TableInfo tableInfo) {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        if (tableInfo != null) {
            //表名
            objectHashMap.put("table", tableInfo);
            //实体名称
            objectHashMap.put("entityName", tableInfo.getEntityName());
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
        objectHashMap.put("now", LocalDate.now() + " " + LocalTime.now());
        //作者
        objectHashMap.put("author", "chengan.liang");


        return objectHashMap;
    }

    /**
     * 包含的table或排除的table处理
     */
    private List<TableInfo> processTableList(List<TableInfo> tableList) {
        List<TableInfo> autoTableList = new ArrayList<>();
        //优先处理包含的table
        if (includeTableList.size() > 0) {
            for (String includeTableName : includeTableList) {
                for (TableInfo tableInfo : tableList) {
                    if (includeTableName.equals(tableInfo.getTableName())) {
                        autoTableList.add(tableInfo);
                    }
                }
            }
        } else if (excludeTableList.size() > 0) {
            for (String excludeTableName : excludeTableList) {
                for (TableInfo tableInfo : tableList) {
                    if (!excludeTableName.equals(tableInfo.getTableName())) {
                        autoTableList.add(tableInfo);
                    }
                }
            }
        } else {
            autoTableList = tableList;
        }
        return autoTableList;
    }

    /**
     * 输出文件
     *
     * @param type      输出类型
     * @param tableInfo 表信息
     * @throws Exception
     */
    public void outPutFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> tableInfoMap = getObjectdInfo(tableInfo);
        //freemarker设置
        Configuration configuration = new Configuration();
        //模板编码
        configuration.setDefaultEncoding("UTF-8");
        //路径
        configuration.setClassForTemplateLoading(this.getClass(), "/");

        String filePath = "";
        String templatePath = "";


        if (type.equals("baseMapper")) {
            filePath = getMapperPath() + "//BaseMapper.java";
            templatePath = GlobalConstants.TEMPLATE_PATH_BASE_MAPPER;
        } else if (type.equals("mapper")) {
            filePath = getMapperPath() + "/" + tableInfo.getDatasourceName() + "/" + tableInfo.getEntityName() + GlobalConstants.MAPPER_SUFFIX + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_MAPPER;
        } else if (type.equals("entity")) {
            filePath = getEntityPath() + "/" + tableInfo.getDatasourceName() + "/" + tableInfo.getEntityName() + GlobalConstants.JAVA_SUFFIX;
            templatePath = GlobalConstants.TEMPLATE_PATH_ENTITY;
        }
        //获取freemarker模板
        File outFile = new File(filePath);
        if (!outFile.exists() || (outFile.exists() && isForceCover)) {

            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            Template template = configuration.getTemplate(templatePath);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            template.process(tableInfoMap, new OutputStreamWriter(fileOutputStream, "UTF-8"));
            fileOutputStream.close();
            log.info("输出文件【" + outFile.getName() + "】");
        }
    }

    private String getMapperPath() {
        String path = packageConfig.getBasePath() + "//" + packageConfig.getBaseSrc() + "//" + packageConfig.getMapperPath();
        return path.replaceAll("\\.", "/");
    }

    private String getEntityPath() {
        String path = packageConfig.getBasePath() + "//" + packageConfig.getBaseSrc() + "//" + packageConfig.getEntityPath();
        return path.replaceAll("\\.", "/");
    }


}
