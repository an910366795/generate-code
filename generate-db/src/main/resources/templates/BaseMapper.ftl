package ${package.mapperPath};


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:基础Mapper
 * @Author: ${author}
 * @Date: ${now}
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T> {

    /**
     * 查询排序标志：倒序
     */
    String ORDER_BY_DESC = "order_by_desc";

    /**
     * 查询排序标志：正序
     */
    String ORDER_BY_ASC = "order_by_asc";

    /**
     * 根据主键查询
     *
     * @param primaryKey 主键
     */
    default T selectById(Object primaryKey) {
        return this.selectByPrimaryKey(primaryKey);
    }

    /**
     * 返回列表数据
     *
     * @param params 单表查询条件
     */
    default List<T> getList(Map<String, Object> params) {
        Example example = this.getExample();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(params);

        Object orderByDesc = params.get(ORDER_BY_DESC);
        if (orderByDesc != null && (orderByDesc + "") != "") {
            example.orderBy(orderByDesc + "").desc();
        }
        Object orderAsc = params.get(ORDER_BY_ASC);
        if (orderAsc != null && (orderAsc + "") != "") {
            example.orderBy(orderAsc + "").asc();
        }

        return this.selectByExample(example);
    }

    /**
     * 根据单个查询条件返回所有符合条件的数据
     *
     * @param columName 列名
     * @param value     查询值
     */
    default List<T> getList(String columName, String value) {
        HashMap<String , Object> param = new HashMap<>();
        param.put(columName, value);

        return this.getList(param);
    }

    /**
     * 分页查询
     *
     * @param page   分页信息
     * @param params 单表查询条件
     */
    default PageInfo<T> getPage(Page page, Map<String, Object> params) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        Example example = this.getExample();
        Example.Criteria criteria = example.createCriteria();
        if (params != null && params.size() > 0) {
            criteria.andEqualTo(params);
        }

        Object orderByDesc = params.get(ORDER_BY_DESC);
        if (orderByDesc != null && (orderByDesc + "") != "") {
            example.orderBy(orderByDesc + "").desc();
        }
        Object orderAsc = params.get(ORDER_BY_ASC);
        if (orderAsc != null && (orderAsc + "") != "") {
            example.orderBy(orderAsc + "").asc();
        }

        List listData = this.selectByExample(example);

        PageInfo pageInfo = new PageInfo(listData);
        return pageInfo;
    }

    /**
     * 根据criteria查询List
     *
     * @param criteria
     */
    default List<T> getListByCriteria(Example.Criteria criteria) {
        Example example = this.getExample();
        example.and(criteria);
        return this.selectByExample(example);
    }

    /**
     * 根据查询条件返回第一条结果
     *
     * @param param 查询条件
     */
    default T selectOne(Map<String, Object> param) {
        List<T> list = this.getList(param);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据单个查询条件查询单个数据
     *
     * @param columName 列名
     * @param value     查询值
     */
    default T selectOne(String columName, String value) {
        HashMap<String, Object> param = new HashMap<>();
        param.put(columName, value);
        return this.selectOne(param);
    }

    /**
     * 全量更新所有字段
     */
    default void updateAll(T entity) {
        int i = this.updateByPrimaryKey(entity);
        if (i != 1) {
            throw new RuntimeException("更新失败,更新记录条数为【" + i + "】");
        }
    }

    /**
     * 部分更新
     */
    default void update(T entity) {
        int i = this.updateByPrimaryKeySelective(entity);
        if (i != 1) {
            throw new RuntimeException("更新失败,更新记录条数为【" + i + "】");
        }
    }

    /**
     * 插入实体
     *
     * @param entity
     */
    default void save(T entity) {
        int i = this.insertSelective(entity);
        if (i != 1) {
         throw new RuntimeException("插入失败【" + i + "】");
        }
    }

    /**
     * 获取example对象
     */
    default Example getExample() {
        Class<?> entityClass = this.getEntityClass();
        Example example = new Example(entityClass);
        return example;
    }

    /**
     * 获取实体Class对象
     */
    default Class<?> getEntityClass() {
        try {
            Type type = this.getClass().getGenericInterfaces()[0];
            Class<?> mapperClass = Class.forName(type.getTypeName());
            String entityClassName = ((ParameterizedType)
            mapperClass.getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
            Class<?> entityClass = Class.forName(entityClassName);
            return entityClass;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }


}
