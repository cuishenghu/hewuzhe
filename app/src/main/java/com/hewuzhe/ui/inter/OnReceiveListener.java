package com.hewuzhe.ui.inter;

/**
 * Created by xianguangjin on 16/1/5.
 */
public interface OnReceiveListener<T, D> {


    /**
     * 向下传递，执行
     *
     * @param msg
     */
    void onReceive(T msg);

    /**
     * 向上传递
     *
     * @return
     */
    D getMsg();

}
