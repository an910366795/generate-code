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
public class ServiceBuilder extends AbstractBuilder {

    //数据库配置
    private MysqlDbConfig mysqlDbConfig;

    public ServiceBuilder(PackageConfig packageConfig, MysqlDbConfig mysqlDbConfig, boolean isForceCover) {
        this.packageConfig = packageConfig;
        this.isForceCover = isForceCover;
        this.mysqlDbConfig = mysqlDbConfig;
    }


    @Override
    public AbstractBuilder generateFile() throws Exception {
        for (TableInfo tableInfo : mysqlDbConfig.getTableInfo()) {
            outputFile("service", tableInfo);
        }
        return this;
    }

    @Override
    public String getPath() {
        String path = packageConfig.getBasePath() + "/" + packageConfig.getBaseJava() + "/" + packageConfig.getServicePath();
        return path.replaceAll("\\.", "/");
    }

    @Override
    public void outputFile(String type, TableInfo tableInfo) throws Exception {
        //将表信息放入Map中
        Map<String, Object> infoMap = getTableInfo(tableInfo);

        String filePath = "";
        String templatePath = "";

        String entityName = infoMap.get("entityName") + "";
        filePath = getPath() + "//" + entityName + "Service" + GlobalConstants.JAVA_SUFFIX;
        templatePath = GlobalConstants.TEMPLATE_PATH_SERVICE;
        //保存文件
        saveFile(filePath, templatePath, isForceCover, infoMap);
    }
}
