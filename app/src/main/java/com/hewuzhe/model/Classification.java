package com.hewuzhe.model;

/**
 * Created by csh on 16/01/29.
 * @desc视频分类
 */
public class Classification {


    /**
     * Id : 63
     * ParentId :
     * Name : 商品分类
     */

    public int Id;/**分类ID*/
    public String Name;/**分类ID*/
    public String ParentId;/**父分类ID*/

    @Override
    public String toString() {
        return Name;
    }
}
