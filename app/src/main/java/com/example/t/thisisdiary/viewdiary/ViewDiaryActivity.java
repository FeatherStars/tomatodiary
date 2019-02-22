package com.example.t.thisisdiary.viewdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.Utils.TimeUtil;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.bean.Diary;
import com.example.t.thisisdiary.writediary.WriteDiary;

public class ViewDiaryActivity extends BaseActivity{

    private TextView tv_diary_title;

    private TextView tv_diary_content;

    private Diary diary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_viewdiary, null);
        return view;
    }

    @Override
    public void initData() {
        //使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_viewdiary);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground); // 设置导航按钮图标
        }

        diary = (Diary) getIntent().getSerializableExtra("mainactivity_diary");
        String diary_title = diary.getTitle();
        String diary_content = diary.getContent();

        tv_diary_title = (TextView) findViewById(R.id.tv_diary_title);
        tv_diary_content = (TextView) findViewById(R.id.tv_diary_content);

        tv_diary_title.setText(diary_title);
        tv_diary_content.setText(diary_content);
    }


    /** 加载Toolbar **/
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_viewdiary, menu);
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
            case R.id.toolbar_editdiary:
                Intent intent = new Intent(ViewDiaryActivity.this, WriteDiary.class);
                intent.putExtra("diary-1", diary);
                startActivityForResult(intent, 1);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK) { // RESULT_CANCELED = 0，RESULT_OK = -1
                    diary = (Diary) data.getSerializableExtra("diary-2");
                    tv_diary_title.setText(diary.getTitle());
                    tv_diary_content.setText(diary.getContent());

                    Intent intent = new Intent();
                    intent.putExtra("viewdiaryactivity_diary_to_mainactivity", diary);
                    setResult(RESULT_OK, intent);
                }
                break;
            default:
                break;

        }
    }
}
