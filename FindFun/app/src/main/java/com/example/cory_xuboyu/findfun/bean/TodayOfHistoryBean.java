package com.example.cory_xuboyu.findfun.bean;

import java.util.List;

/**
 * 作用：TodayOfHistoryBean
 * Created by xuboyu on 2018/3/27.
 * e-mail:boyu274572505@qq.com
 */

public class TodayOfHistoryBean {

    /**
     * reason : success
     * result : [{"day":"2/9","date":"前600年02月09日","title":"道家学派创始人老子诞辰","e_id":"1785"},{"day":"2/9","date":"2006年02月09日","title":"\u201c感动中国2005年度十大人物\u201d揭晓","e_id":"1836"}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * day : 2/9
         * date : 前600年02月09日
         * title : 道家学派创始人老子诞辰
         * e_id : 1785
         */

        private String date;
        private String title;
        private String e_id;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }
    }
}
