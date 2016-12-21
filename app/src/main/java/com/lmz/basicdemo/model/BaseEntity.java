package com.lmz.basicdemo.model;

/**
 * 作者：LMZ on 2016/7/4 0004 14:24
 * 邮箱：88214924@qq.com
 */
public class BaseEntity {

    /**
     * Code : 14001
     * Msg : 上传文件不能为空不能为空
     * Time : 2016-12-02 18:31:59
     * Apiurl : /Api/Picture/Picture.html
     * ApiToken :
     * Data :
     */

    private String Code;
    private String Msg;
    private String Time;
    private String Apiurl;
    private String ApiToken;
    private String Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getApiurl() {
        return Apiurl;
    }

    public void setApiurl(String Apiurl) {
        this.Apiurl = Apiurl;
    }

    public String getApiToken() {
        return ApiToken;
    }

    public void setApiToken(String ApiToken) {
        this.ApiToken = ApiToken;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
