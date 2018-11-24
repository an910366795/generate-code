package com.generate.db;

import com.generate.core.model.Field;
import com.generate.core.model.TableInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Decription:
 * @Author: chengan.liang
 * @Date: 2018/4/13
 */
@Slf4j
public class MysqlDbConfig {

    //数据库url
    private String url;
    //用户名
    private String userName;
    //密码
    private String password;
    //数据库连接preparement
    PreparedStatement preparedStatement;
    //表信息
    private List<TableInfo> tableInfoList;

    //mysql获取数据库表信息语句
    private static final String SHOW_TABLE_INFO = "show table status";
    //mysql获取表名关键字
    private static final String DATASOURCE_NAME = "catalog";
    //mysql获取表名关键字
    private static final String TABLE_NAME = "NAME";
    //mysql获取表注释关键字
    private static final String TABLE_COMMENT = "COMMENT";
    //mysql获取表字段语句
    private static final String SHOW_TABLE_FIELD = "show full fields from `%s`";
    //mysql获取表的字段名关键字
    private static final String TABLE_FIELD = "FIELD";
    //mysql获取表的字段的类型
    private static final String TABLE_FIELD_TYPE = "TYPE";
    //mysql获取表的字段的注释
    private static final String TABLE_FIELD_COMMENT = "COMMENT";
    //mysql获取表的主键关键字
    private static final String TABLE_FIELD_KEY = "KEY";

    public MysqlDbConfig(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        initTableInfo();
    }

    /**
     * 获取数据库表信息
     */
    public List<TableInfo> getTableInfo() {
        return tableInfoList;
    }

    private void initTableInfo() {
        if (url == "" || url == null) {
            throw new GeneratorDbException("数据库地址为空");
        }
        if (userName == "" || userName == null) {
            throw new GeneratorDbException("用户名为空");
        }
        if (password == "" || password == null) {
            throw new GeneratorDbException("数据库为空");
        }

        try {
            //初始化驱动
            Class.forName("com.mysql.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection(this.url, this.userName, this.password);
            //查询表信息,show table status
            preparedStatement = connection.prepareStatement(SHOW_TABLE_INFO);
            //获取结果
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //数据库名称
                String dataSourceName = connection.getCatalog();
                //表名
                String tableName = resultSet.getString(TABLE_NAME);
                //表的注释
                String tableComment = resultSet.getString(TABLE_COMMENT);

                if (tableName != null || tableName != "") {
                    TableInfo tableInfo = new TableInfo(dataSourceName, tableName, tableComment);

                    if (tableInfoList == null) {
                        tableInfoList = new ArrayList<>();
                    }
                    tableInfoList.add(tableInfo);
                } else {
                    throw new GeneratorDbException("数据库表为空");
                }
            }
            //获取表字段信息
            for (TableInfo tableInfo : tableInfoList) {
                tableInfo = getTableFiedInfo(tableInfo, preparedStatement);
            }
            // 释放资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //转化表信息，转化实体名称，mapperName,ServiceName
        transactionTableList(tableInfoList);
    }

    /**
     * 获取表的字段信息
     */
    private TableInfo getTableFiedInfo(TableInfo tableInfo, PreparedStatement preparedStatement) throws SQLException {
        String show_field = String.format(SHOW_TABLE_FIELD, tableInfo.getTableName());
        ResultSet resultSet = preparedStatement.executeQuery(show_field);

        ArrayList<Field> fields = new ArrayList<>();
        while (resultSet.next()) {
            //字段名称
            String fieldName = resultSet.getString(TABLE_FIELD);
            //字段类型
            String type = conversionFieldType(resultSet.getString(TABLE_FIELD_TYPE));
            //字段注释
            String comment = resultSet.getString(TABLE_FIELD_COMMENT);
            //是否主键
            String key = resultSet.getString(TABLE_FIELD_KEY);
            // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
            boolean isKeyId = ("" != key && null != key) && key.toUpperCase().equals("PRI");

            Field field = new Field(fieldName, type, comment);
            //字段属性名称
            field.setPropertyName(conversionPropName(fieldName));
            //属性类型
            String fieldType = conversionPropType(field.getFileType());
            field.setPropertyType(fieldType);

            //类型集合
            tableInfo.getTypeList().add(fieldType);
            //是否主键
            field.setKey(isKeyId);

            fields.add(field);
        }
        tableInfo.setFieldList(fields);

        return tableInfo;
    }


    /**
     * 数据库字段名称转化为实体类名称
     */
    private String conversionPropName(String fieldName) {
        if (fieldName.contains("_")) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] split = fieldName.split("_");
            for (int i = 0; i < split.length; i++) {
                if (i > 0) {
                    String s = split[i];
                    stringBuilder.append(s.substring(0, 1).toUpperCase() + s.substring(1, s.length()));
                } else {
                    stringBuilder.append(split[i]);
                }
            }
            return stringBuilder.toString();
        } else {
            return fieldName;
        }
    }


    /**
     * 数据库类型转化为实体的包装类型
     */
    private String conversionPropType(String fileType) {

        if (fileType.contains("CHAR") || fileType.contains("TEXT")) {
            return "String";
        } else if (fileType.contains("BIGINT")) {
            return "Long";
        } else if (fileType.contains("INT")) {
            return "Integer";
        } else if (fileType.contains("DATE") || fileType.contains("TIME") || fileType.contains("YEAR")) {
            return "Date";
        } else if (fileType.contains("BIT")) {
            return "Boolean";
        } else if (fileType.contains("DECIMAL")) {
            return "BigDecimal";
        } else if (fileType.contains("CLOB")) {
            return "Clob";
        } else if (fileType.contains("BLOB")) {
            return "Blob";
        } else if (fileType.contains("BINARY")) {
            return "byte[]";
        } else if (fileType.contains("FLOAT")) {
            return "Float";
        } else if (fileType.contains("DOUBLE")) {
            return "Double";
        } else if (fileType.contains("JSON") || fileType.contains("ENUM")) {
            return "String";
        }
        return "String";
    }

    private List<TableInfo> transactionTableList(List<TableInfo> tableList) {
        for (TableInfo tableInfo : tableList) {
            String tableName = tableInfo.getTableName();
            String[] split = tableName.split("_");
            StringBuilder builder = new StringBuilder();
            if (split.length > 1) {
                for (int i = 1; i < split.length; i++) {
                    String s = split[i].trim();
                    if (s != "" && s != null && s.length() > 1) {
                        log.debug("扫描表【{}】", s);
                        //首字母大写
                        builder.append(s.substring(0, 1).toUpperCase() + s.substring(1, s.length()));
                    }
                }
            } else {
                builder.append(split[0]);
            }
            //设置实体名称
            tableInfo.setEntityName(builder.toString());
        }
        return tableList;
    }

    /**
     * 转化数据库字段为jdbcType
     */
    private String conversionFieldType(String field) {
        String s = field.split("\\(")[0].toUpperCase();
        if (s.equals("DATETIME")) {
            s = "TIMESTAMP";
        } else if (s.equals("INT")) {

            s = "INTEGER";
        } else if (s.equals("TEXT")) {
            s = "VARCHAR";
        }

        return s;

    }

}
