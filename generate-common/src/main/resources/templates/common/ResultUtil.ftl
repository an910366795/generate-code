package com.common.util;

import com.common.CommonResult;
import com.common.constants.MsgConstants;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * @author: ${author}
 * @Date: ${now}
 */
public class ResultUtil {

    /**
     * 成功code
     */
    private static final int SUCCESS_CODE = 0;


    /**
     * 失败code
     */
    private static final int FAIL_CODE = 1;


    /**
     * 构造成功结果
     *
     * @param msg 返回提示信息
     */
    public static CommonResult success(String msg) {
        CommonResult result = new CommonResult();
        result.setCode(com.common.util.ResultUtil.SUCCESS_CODE);

        if (msg == null || msg == "") {
            msg = MsgConstants.MSG_1000;
        }
        result.setMsg(msg);
        return result;
    }

    public static CommonResult success() {
        return success(MsgConstants.MSG_1000);
    }

    /**
     * 构造分页数据
     *
     * @param pageInfo 分页参数
     */
    public static CommonResult success(PageInfo pageInfo) {
        CommonResult result = new CommonResult();
        result.addData("total", pageInfo.getTotal());
        result.addData("list", pageInfo.getList());
        return result;
    }

    /**
     * 构造失败结果
     *
     * @param msg 返回提示信息
     */
    public static CommonResult fail(String msg) {
        CommonResult result = new CommonResult();
        result.setCode(FAIL_CODE);
        if (msg == null || msg == "") {
            msg = MsgConstants.MSG_1001;
        }
        result.setMsg(msg);
        return result;
    }

    public static CommonResult fail() {
        return fail(MsgConstants.MSG_1001);
    }


}
