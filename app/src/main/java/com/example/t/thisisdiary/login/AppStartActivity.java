package com.example.t.thisisdiary.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.MyDatabaseHelper;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.main.MainActivity;

import org.litepal.tablemanager.Connector;

public class AppStartActivity extends BaseActivity {

    private final int SPLASH_TIME = 1000; // 启动页停留时间

    public static  MyDatabaseHelper dbHelper;

    public static SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
                startActivity(intent);
                AppStartActivity.this.finish();
            }
        }, SPLASH_TIME);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_splash, null);
        return view;
    }

    @Override
    public void initData() {
        createDatabase();
    }

    /** 建数据库 **/
    //TODO:放在以后的启动活动中
    public void createDatabase() {
        // Connector.getDatabase();
        // 将数据库名指定为DiaryStore.db，版本号指定为1
        dbHelper = new MyDatabaseHelper(this, "DiaryStore.db", null, 1);
        db = dbHelper.getWritableDatabase();

    }
}
