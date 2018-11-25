package com.web.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.web.dao.${datasourceName}.${entityName}Mapper;
import com.web.entity.${datasourceName}.${entityName};
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: ${author}
 * @Date: ${now}
 */
@Service
@Slf4j
public class ${entityName}Service {

    @Autowired
    private ${entityName}Mapper ${paramName}Mapper;

    /**
     * 查询分页数据
     *
     * @param page   分页参数
     * @param params 查询参数
     */
    public PageInfo<${entityName}> getPage(Page page, Map<String, Object> params) {
        return ${paramName}Mapper.getPage(page, params);
    }

    /**
     * 查询全部数据
     *
     * @param params 查询参数
     */
    public List<${entityName}> getList(Map<String, Object> params) {
        return ${paramName}Mapper.getList(params);
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     */
    public ${entityName} getById(String id) {
        return ${paramName}Mapper.selectById(id);
    }


    /**
     * 新增
     *
     * @param ${paramName}
     */
    @Transactional
    public void add${entityName}(${entityName} ${paramName}) {

    }

    @Transactional
    public void update${entityName}(String id, ${entityName} ${paramName}) {

    }

    @Transactional
    public void delete${entityName}(String id) {

    }
}
