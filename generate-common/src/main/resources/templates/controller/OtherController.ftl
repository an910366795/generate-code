package com.web.controller;

import com.common.CommonResult;
import com.common.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.web.entity.${datasourceName}.${entityName};
import com.web.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @author: ${author}
 * @Date: ${now}
 */
@RestController
@RequestMapping("/${paramName}")
public class ${entityName}Controller extends BaseController {

    @Autowired
    private ${entityName}Service ${paramName}Service;

    /**
     * 分页数据
     */
    @GetMapping
    public CommonResult getPageData() {
        PageInfo<${entityName}> pageInfo = ${paramName}Service.getPage(this.getPage(), null);
        return ResultUtil.success(pageInfo);
    }

    /**
     * 单体查询
     */
    @GetMapping("/{id}")
    public CommonResult get${entityName}(@PathVariable("id") String id) {
        ${entityName} ${paramName} = ${paramName}Service.getById(id);

        CommonResult result = ResultUtil.success();
        result.addData("list", ${paramName});
        return result;
    }

    /**
     * 新增
     *
     * @param ${paramName} 请求参数
     */
    @PostMapping
    public CommonResult add${entityName}(${entityName} ${paramName}) {
        ${paramName}Service.add${entityName}(${paramName});
        return ResultUtil.success("新增成功");
    }


    /**
     * 更新
     *
     * @param id    主键
     * @param ${paramName} 更新信息
     */
    @PutMapping("/{id}")
    public CommonResult update${entityName}(@PathVariable("id") String id, ${entityName} ${paramName}) {
        ${paramName}Service.update${entityName}(id, ${paramName});
        return ResultUtil.success("更新成功");
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @DeleteMapping("/{id}")
    public CommonResult delete${entityName}(@PathVariable("id") String id) {
        ${paramName}Service.delete${entityName}(id);
        return ResultUtil.success("删除成功");
    }

}
