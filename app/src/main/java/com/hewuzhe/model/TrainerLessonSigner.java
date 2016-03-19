package com.hewuzhe.model;

import android.media.Image;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerLessonSigner {
    private int Id;
   private String Name;//用户姓名
    private String Phone;//用户电话
    private int Sex;//用户性别
    private int UserId;//用户ID
    private int JoinNum;//报名人数
    private String JoinTime;//报名时间
    private TrainerLesson Lesson;//用户报名课程
    private PrivateTrainer Teacher;//用户报名的教练
    private String ImagePath;

    public TrainerLessonSigner() {
    }

    public TrainerLessonSigner(int id, String name, String phone, String joinTime,String imagePath,int sex, int userId, int joinNum, TrainerLesson lesson, PrivateTrainer teacher) {
        Id = id;
        Name = name;
        Phone = phone;
        Sex = sex;
        UserId = userId;
        JoinNum = joinNum;
        Lesson = lesson;
        Teacher = teacher;
        JoinTime=joinTime;
        ImagePath=imagePath;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getJoinNum() {
        return JoinNum;
    }

    public void setJoinNum(int joinNum) {
        JoinNum = joinNum;
    }

    public TrainerLesson getLesson() {
        return Lesson;
    }

    public void setLesson(TrainerLesson lesson) {
        Lesson = lesson;
    }

    public PrivateTrainer getTeacher() {
        return Teacher;
    }

    public void setTeacher(PrivateTrainer teacher) {
        Teacher = teacher;
    }

    public String getJoinTime() {
        return JoinTime;
    }

    public void setJoinTime(String joinTime) {
        JoinTime = joinTime;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
