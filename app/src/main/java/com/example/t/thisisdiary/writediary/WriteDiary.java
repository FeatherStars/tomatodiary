package com.example.t.thisisdiary.writediary;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.Utils.TimeUtil;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.bean.Diary;
import com.example.t.thisisdiary.main.MainActivity;

import static com.example.t.thisisdiary.login.AppStartActivity.db;

public class WriteDiary extends BaseActivity {

    private EditText et_diary_title;

    private EditText et_diary_content;

    private Diary editeddiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_writediary, null);
        return view;
    }

    @Override
    public void initData() {
        //使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_writediary);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground); // 设置导航按钮图标
        }

        editeddiary = (Diary) getIntent().getSerializableExtra("viewdiaryactivity_diary");

        et_diary_title = (EditText) findViewById(R.id.et_diary_title);
        et_diary_content = (EditText) findViewById(R.id.et_diary_content);

        if(editeddiary != null) {
            String diary_title = editeddiary.getTitle();
            String diary_content = editeddiary.getContent();
            et_diary_title.setText(diary_title);
            et_diary_content.setText(diary_content);
        }
    }

    /** 加载Toolbar **/
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_writediary, menu);
        return true;
    }

    /** 处理Toolbar中各个按钮的点击事件 **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // HomeAsUp按钮的id永远是android.R.id.home
                //返回上一页
                onBackPressed();
                break;
            case R.id.toolbar_writediary_save:
                saveDiary();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveDiary();
    }

    public void saveDiary() {
        //保存日记
        String title = et_diary_title.getText().toString();
        String content = et_diary_content.getText().toString();
        String time = TimeUtil.getCurrentTime();
        int isRecycleBin = 0;
        int isSecret = 0;
        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content)) {
            Diary diary = new Diary();
            diary.setTitle(title);
            diary.setContent(content);
            diary.setTime(time);
            diary.setIsRecycleBin(0);
            diary.setIsSecret(0);
            SQLiteUtil.saveDiary(title, content, time, isRecycleBin, isSecret);
            Toast.makeText(WriteDiary.this, "保存成功", Toast.LENGTH_SHORT).show();
            // 把diary传回
            Intent intent = new Intent();
            intent.putExtra("writediary_newdiary", diary);
            setResult(RESULT_OK, intent);
            // 以上是新建日记，如果是编辑日记
            if(editeddiary != null) {
                SQLiteUtil.deleteDiary(editeddiary.getTime());
                Intent intent1 = new Intent();
                intent1.putExtra("writediary_editdiary", diary);
                setResult(RESULT_OK, intent1);
            }
            finish();
        } else {
            Toast.makeText(WriteDiary.this, "您没有输入任何内容", Toast.LENGTH_SHORT).show();
        }
    }
}
