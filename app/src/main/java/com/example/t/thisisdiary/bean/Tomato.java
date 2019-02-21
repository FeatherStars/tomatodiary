package com.example.t.thisisdiary.bean;

public class Tomato {

    private String taskName;

    private int tomatoNumber;

    private String time; // 时间

    private int isCompleted; // 番茄是否完成

    public Tomato () {}

    public Tomato (String taskName, int tomatoNumber) {
        this.taskName = taskName;
        this.tomatoNumber = tomatoNumber;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTomatoNumber() {
        return tomatoNumber;
    }

    public void setTomatoNumber(int tomatoNumber) {
        this.tomatoNumber = tomatoNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}
