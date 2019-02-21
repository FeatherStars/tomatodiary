package com.example.t.thisisdiary.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Diary extends DataSupport implements Serializable{ // 对应数据库中的Diary表

    private String title = "无题"; // 标题

    private String content; // 内容

    private String time; // 时间

    private int isRecyclebin = 0; // 是否在回收站：0 不在 1 在

    private int isSecret = 0; // 是否私密日记：0 不是 1 是

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsRecycleBin() {
        return isRecyclebin;
    }

    public void setIsRecycleBin(int isRecycleBin) {
        this.isRecyclebin = isRecycleBin;
    }

    public int getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(int isSecret) {
        this.isSecret = isSecret;
    }
}
