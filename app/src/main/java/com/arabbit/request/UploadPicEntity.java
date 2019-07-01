package com.arabbit.request;

/**
 * Created by net8 on 2017/8/1.
 */

public class UploadPicEntity {

    /**
     * code : 1
     * message : 上传成功
     * data : {"url":"http://liuliutuapi.gzaojun.com/static/upload_pic/avatar_img/20170729/136889e2d0ba90e833f51de93b1317be.jpg"}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://liuliutuapi.gzaojun.com/static/upload_pic/avatar_img/20170729/136889e2d0ba90e833f51de93b1317be.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
