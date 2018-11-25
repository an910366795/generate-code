package com.generate.db;

import com.generate.core.AbstractBuilder;
import com.generate.core.constants.GlobalConstants;
import com.generate.core.model.PackageConfig;
import com.generate.core.model.TableInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class DataSourceBuilder extends AbstractBuilder {

    //数据库配置
    private MysqlDbConfig mysqlDbConfig;
    //排除的表
    private List<String> excludeTableList = new ArrayList<>();
    //包含的表
    private List<String> includeTableList = new ArrayList<>();


    public DataSourceBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig) {
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
    }

    public DataSourceBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig, boolean isForceCover) {
        this.packageConfig = packageConfig;
        this.mysqlDbConfig = mysqlDbConfig;
        this.isForceCover = isForceCover;
    }


    //增加排除的表
    public void addExcludeTable(String tableName) {
        excludeTableList.add(tableName);
    }

    //增加包含的表
    public DataSourceBuilder addIncludeTable(String... tableNames) {
        for (String tableName : tableNames) {
            includeTableList.add(tableName);
        }
        return this;
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


    private String getMapperPath() {
        String path = packageConfig.getBasePath() + "//" + packageConfig.getBaseJava() + "//" + packageConfig.getMapperPath();
        return path.replaceAll("\\.", "/");
    }

    private String getEntityPath() {
        String path = packageConfig.getBasePath() + "//" + packageConfig.getBaseJava() + "//" + packageConfig.getEntityPath();
        return path.replaceAll("\\.", "/");
    }


    @Override
    public AbstractBuilder generateFile() throws Exception {
        //获取表信息并处理
        List<TableInfo> autoTableList = processTableList(mysqlDbConfig.getTableInfo());

        //输出基础mapper
        outputFile("baseMapper", null);

        for (TableInfo tableInfo : autoTableList) {
            //输出mapper文件
            outputFile("mapper", tableInfo);
            //输出entity文件
            outputFile("entity", tableInfo);
        }
        return this;
    }

    @Override
    public void outputFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getTableInfo(tableInfo);

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
        //保存文件
        saveFile(filePath, templatePath, isForceCover, infoMap);
    }

    @Override
    public AbstractBuilder mkDir() {
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

    @Override
    public String getPath() {
        return null;
    }
}
