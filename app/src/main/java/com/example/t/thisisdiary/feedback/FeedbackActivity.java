package com.example.t.thisisdiary.feedback;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.base.BaseActivity;

public class FeedbackActivity extends BaseActivity implements
        View.OnClickListener{

    private TextView tv_qq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_feedback, null);
        return view;
    }

    @Override
    public void initData() {

        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        tv_qq = (TextView) findViewById(R.id.tv_qq);
        tv_qq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qq:
                onClickCopy();
                Toast.makeText(FeedbackActivity.this, R.string.copy_success, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void onClickCopy() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String str = tv_qq.getText().toString();
        cm.setText(str.substring(str.indexOf("QQ：")+3, str.indexOf("(点击复制)")));
    }

    /** 处理toolbar中各个按钮的点击事件 **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // HomeAsUp按钮的id永远是android.R.id.home
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
