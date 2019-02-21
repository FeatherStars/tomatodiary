package com.example.t.thisisdiary.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 将建表语句定义成了一个字符串常量
    public static final String CREATE_DIARY = "create table Diary ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "time text, "
            + "isRecycleBin integer, "
            + "isSecret integer) ";

    public static final String CREATE_TOMATO = "create table Tomato ("
            + "id integer primary key autoincrement, "
            + "taskName text, "
            + "tomatoNumber integer, "
            + "time text, "
            + "isCompleted integer) ";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY); // 执行建表语句
        db.execSQL(CREATE_TOMATO);
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
