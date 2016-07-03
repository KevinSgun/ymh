package com.zitech.framework.data.network.response;

import com.zitech.framework.data.network.response.ApiResponse;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class FileUploadResponse<T> extends ApiResponse<T>{

    /**
     * msg : 上传成功！
     * oper : success
     * retCode : 0
     * retMsg : 上传成功！
     * status : y
     */

    private String msg;
    private String oper;
    private int retCode;
    private String retMsg;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
