package com.hewuzhe.model;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/1/26.
 */
public class LiveVideo {


    /**
     * Id : 1
     * ImageList : [{"Id":1,"ImagePath":"UpLoad/Image/04d05099-61d3-4cb1-a8fb-aa4e80a3642f.jpg"},{"Id":2,"ImagePath":"UpLoad/Image/d9a97731-5840-4483-9cf5-4abccb4b2e50.jpg"},{"Id":3,"ImagePath":"UpLoad/Image/5c94aaca-be44-44dc-83b7-750fe6259773.jpg"}]
     * Title : 这个是第一个视频直播标题
     * Content : 这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述这个是第一个视频直播内容描述
     * LiveBack :
     * LiveFront :
     * TimeEnd : 2016-01-26 14:00:00
     * TimeStart : 2016-01-26 12:00:00
     */

    public int Id;
    public String Title;
    public String ImagePath;
    public String Content;
    public String LiveBack;
    public String LiveFront;
    public String TimeEnd;
    public String TimeStart;
    public String LiveUId;
    public String LiveVId;
    /**
     * Id : 1
     * ImagePath : UpLoad/Image/04d05099-61d3-4cb1-a8fb-aa4e80a3642f.jpg
     */


    public ArrayList<Pic> ImageList=new ArrayList<>();

}
