package com.web.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 公共controller封装
 * @author: ${author}
 * @Date: ${now}
 */
public abstract class BaseController {


    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 获取分页参数
     */
    public Page getPage() {
        Page<Object> page = new Page<>();

        String pageNo = request.getParameter("pageNo");
        if (!(pageNo != null && pageNo != "")) {
            pageNo = "1";
        }

        String pageSize = request.getParameter("pageNo");
        if (!(pageSize != null && pageSize != "")) {
            pageSize = "10";
        }

        page.setPageNum(Integer.parseInt(pageNo));
        page.setPageSize(Integer.parseInt(pageSize));
        return page;
    }

}
