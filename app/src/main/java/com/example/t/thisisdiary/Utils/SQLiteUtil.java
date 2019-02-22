package com.example.t.thisisdiary.Utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.t.thisisdiary.bean.Diary;
import com.example.t.thisisdiary.bean.Tomato;

import java.util.ArrayList;
import java.util.List;

import static com.example.t.thisisdiary.login.AppStartActivity.db;

public class SQLiteUtil {

    // 保存日记
    public static void saveDiary(String title, String content, String time, int isRecycleBin, int isSecret) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("time", time);
        values.put("isRecycleBin", isRecycleBin);
        values.put("isSecret", isSecret);
        db.insert("Diary", null, values); // 插入数据
    }



    // 删除 一个日记
    public static void deleteDiary(String time) {
        db.delete("Diary", "time = ?", new String[] {time});
    }


    // 更新 回收站 标志
    public static void updateIsRecycleBin(int isRecycleBin, String time) {
        ContentValues values = new ContentValues();
        values.put("isRecycleBin", isRecycleBin);
        db.update("Diary", values, "time = ?", new String[] {time});
    }

    // 更新 私密日记 标志
    public static void updateIsSecret(int isSecret, String time) {
        ContentValues values = new ContentValues();
        values.put("isSecret", isSecret);
        db.update("Diary", values, "time = ?", new String[] {time});
    }

    // 查询 回收站标志为0的日记
    public static List<Diary> queryMain() {
        Cursor cursor = db.query("Diary", null, "isRecycleBin = ? and isSecret = ?", new String[] {"0", "0"}, null, null, null);
        return getDiaryList(cursor);
    }

    // 查询 回收站日记
    public static List<Diary> queryRecycleBin() {
        Cursor cursor = db.query("Diary", null, "isRecycleBin = ?", new String[] {"1"}, null, null, null);
        // cursor.close(); // 不可以在此处关闭，会导致异常
        return getDiaryList(cursor);
    }

    // 查询 私密日记
    public static List<Diary> querySecret() {
        Cursor cursor = db.query("Diary", null, "isSecret = ?", new String[] {"1"}, null, null, null);
        return getDiaryList(cursor);
    }

    // 根据cursor 取得 日记列表
    public static List<Diary> getDiaryList (Cursor cursor) {
        List<Diary> diaries = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历cursor对象，取出数据
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int isRecycleBin = cursor.getInt(cursor.getColumnIndex("isRecycleBin"));
                int isSecret = cursor.getInt(cursor.getColumnIndex("isSecret"));
                Diary diary = new Diary();
                diary.setTitle(title);
                diary.setContent(content);
                diary.setTime(time);
                diary.setIsRecycleBin(isRecycleBin);
                diary.setIsSecret(isSecret);
                diaries.add(diary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaries;
    }



    /** 保存一个番茄 **/
    public static void saveTomato(String taskName, int tomatoNumber, String time, int isCompleted) {
        ContentValues values = new ContentValues();
        values.put("taskName", taskName);
        values.put("tomatoNumber", tomatoNumber);
        values.put("time", time);
        values.put("isCompleted", isCompleted);
        db.insert("Tomato", null, values);
    }

    /** 查询一个番茄 **/
    public static Tomato querytTomatoByTime(String time) {
        Cursor cursor = db.query("Tomato", null, "time = ?", new String[] {time}, null, null, null);
        return getTomatoList(cursor).get(0);
    }

    /** 删除 一个番茄 **/
    public static void deleteTomato(String time) {
        db.delete("Tomato", "time = ?", new String[] {time});
    }

    /** 查询所有的番茄 **/
    public static List<Tomato> queryAllTomato() {
        Cursor cursor = db.query("Tomato", null, null, null, null, null, null);
        return getTomatoList(cursor);
    }

    /** 根据cursor 取得 tomato列表 **/
    public static List<Tomato> getTomatoList (Cursor cursor) {
        List<Tomato> tomatoes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // 遍历cursor对象，取出数据
                String taskName = cursor.getString(cursor.getColumnIndex("taskName"));
                int tomatoNumber = cursor.getInt(cursor.getColumnIndex("tomatoNumber"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int isCompleted = cursor.getInt(cursor.getColumnIndex("isCompleted"));
                Tomato tomato = new Tomato();
                tomato.setTaskName(taskName);
                tomato.setTomatoNumber(tomatoNumber);
                tomato.setTime(time);
                tomato.setIsCompleted(isCompleted);
                tomatoes.add(tomato);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tomatoes;
    }



}
