package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerLesson {
    private int Id;
    private String Title;//课程标题
    private String Content;//课程内容
    private String StartTime;//课程开始时间
    private String EndTime;//结束时间
    private String ImagePath;//图片
    private int JoinNum;//报名人数
    private int IsJoin;//是否报名

    public TrainerLesson() {
    }

    public TrainerLesson(int id, int joinNum, int isJoin, String title, String content, String startTime, String endTime, String imagePath) {
        Id = id;
        Title = title;
        Content = content;
        StartTime = startTime;
        EndTime = endTime;
        ImagePath = imagePath;
        JoinNum = joinNum;
        IsJoin = isJoin;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getJoinNum() {
        return JoinNum;
    }

    public void setJoinNum(int joinNum) {
        JoinNum = joinNum;
    }

    public int getIsJoin() {
        return IsJoin;
    }

    public void setIsJoin(int isJoin) {
        IsJoin = isJoin;
    }
}
