package com.xqx.travel.domain;

import java.io.Serializable;

/**
 * @author：xingquanxiang
 * createTime：2019/9/21 14:39
 * description: 用于封装后端返回前端的数据对象
 */
public class ResultInfo implements Serializable {
    /**
     * 后端返回结果正常为true，发生异常返回false
     */
    private boolean flag;
    /**
     * 后端返回结果数据对象
     */
    private Object data;
    /**
     * 发生异常的错误信息
     */
    private String errorMsg;

    /**
     * 无参构造方法
     */
    public ResultInfo() {}

    /**
     * 有参构造方法
     * @param flag
     * @param errorMsg
     */
    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    /**
     * 有参构造方法
     * @param flag
     * @param data
     * @param errorMsg
     */
    public ResultInfo(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
