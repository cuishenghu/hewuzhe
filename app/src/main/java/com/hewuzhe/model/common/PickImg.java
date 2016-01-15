package com.hewuzhe.model.common;

/**
 * Created by xianguangjin on 16/1/2.
 */
public class PickImg {

    public final static int STATUS_EMPTY = 0;//待添加状态
    public final static int STATUS_PICKED = 1;//添加了图片

    public String filePath;
    public String picUrl;
    public int status = STATUS_EMPTY;

    public PickImg(String filePath, int status) {
        this.filePath = filePath;
        this.status = status;
    }

    public PickImg() {
    }
}
