package com.hewuzhe.model;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class ConditionComment {


    /**
     * Id : 161
     * Content : å¶å¦å¦å¦
     * PublishTime : 2016-01-12 12:42:24
     * MessageId : 202
     * MessageAndVideoContent : å°å¦å¦å¦å¦ççå¥¹ä¹ä¼å½±åå ç´ 
     * PicList : []
     * ImagePath :
     * NicName : åä¸åä¸è®©ç¶äº²ä¸º
     * CommenterId : 2
     * Phone : 123
     * PhotoPath : UpLoad/Photo/022ee277-1542-4fb2-b68d-9e1cb3cfbcbe.jpg
     */

    public int Id;
    public String Content;
    public String PublishTime;
    public int MessageId;
    public String MessageAndVideoContent;
    public String ImagePath;
    public String NicName;
    public int CommenterId;
    public String Phone;
    public String PhotoPath;
    public ArrayList<Pic> PicList=new ArrayList<>();
}
