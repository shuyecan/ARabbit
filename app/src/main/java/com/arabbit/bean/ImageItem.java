package com.arabbit.bean;

/**
 * Created by net8 on 2017/6/13.
 */
public class ImageItem {
    public String path;
    public int status;

    public ImageItem(String p, int status) {
        this.path = p;
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
