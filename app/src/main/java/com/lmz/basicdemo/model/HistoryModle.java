package com.lmz.basicdemo.model;

import java.util.List;

/**
 * 作者：LMZ on 2016/12/20 0020 16:56
 */
public class HistoryModle {

    /**
     * Code : 0
     * Msg : ok
     * Time : 2016-12-21 10:15:57
     * Apiurl : /Api/Forms/forms.html
     * ApiToken : 1e44beeacd2b308cbedb2f31598ddaa8
     * Data : [{"text":"2016年12月","start_time":"1480521600","end_time":"1483113600","money":"1.18"},{"text":"2016年11月","start_time":"1477929600","end_time":"1480435200","money":"0"},{"text":"2016年10月","start_time":"1475251200","end_time":"1477843200","money":"0"},{"text":"2016年09月","start_time":"1472659200","end_time":"1475164800","money":"0"},{"text":"2016年08月","start_time":"1469980800","end_time":"1472572800","money":"0"},{"text":"2016年07月","start_time":"1467302400","end_time":"1469894400","money":"0"}]
     */

    private String Code;
    private String Msg;
    private String Time;
    private String Apiurl;
    private String ApiToken;
    private List<DataEntity> Data;

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

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        /**
         * text : 2016年12月
         * start_time : 1480521600
         * end_time : 1483113600
         * money : 1.18
         */

        private String text;
        private String start_time;
        private String end_time;
        private String money;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
