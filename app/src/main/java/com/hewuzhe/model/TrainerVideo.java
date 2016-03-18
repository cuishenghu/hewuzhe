package com.hewuzhe.model;

/**
 * Created by Administrator on 2016/3/14 0014.
 */
public class TrainerVideo {
    private int Id;//视频ID
    private String Title;//视频名称
    private String Content;//视频简介内容
    private String VideoPath;//视频地址
    private String VideoDuration;//视频时长
    private String ImagePath;//视频封面
    private int TeacherId;//视频拥有者的ID

    public TrainerVideo(){}
    public TrainerVideo(int id, String title, String content, String videoPath, String videoDuration, String imagePath, int teacherId) {
        Id = id;
        Title = title;
        Content = content;
        VideoPath = videoPath;
        VideoDuration = videoDuration;
        ImagePath = imagePath;
        TeacherId = teacherId;
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

    public String getVideoPath() {
        return VideoPath;
    }

    public void setVideoPath(String videoPath) {
        VideoPath = videoPath;
    }

    public String getVideoDuration() {
        return VideoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        VideoDuration = videoDuration;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(int teacherId) {
        TeacherId = teacherId;
    }
}
