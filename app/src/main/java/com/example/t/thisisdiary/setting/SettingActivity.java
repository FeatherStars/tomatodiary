package com.example.t.thisisdiary.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.OtherUtils;
import com.example.t.thisisdiary.Utils.UserInformation;
import com.example.t.thisisdiary.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_personinformation;

    private LinearLayout ll_passwordsetting;

    private LinearLayout ll_alertsetting;

    private LinearLayout ll_tomatotime;

    private TextView tv_tomatotime_set;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_setting, null);
        return view;
    }

    @Override
    public void initData() {
        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        ll_personinformation = (LinearLayout) findViewById(R.id.ll_personinformation);
        ll_passwordsetting = (LinearLayout) findViewById(R.id.ll_passwordsetting);
        ll_alertsetting = (LinearLayout) findViewById(R.id.ll_alertsetting);
        ll_tomatotime = (LinearLayout) findViewById(R.id.ll_tomatotime);
        tv_tomatotime_set = (TextView) findViewById(R.id.tv_tomatotime_set);

        ll_personinformation.setOnClickListener(this);
        ll_passwordsetting.setOnClickListener(this);
        ll_alertsetting.setOnClickListener(this);
        ll_tomatotime.setOnClickListener(this);

        String tomatotime = UserInformation.getTomatoTime(SettingActivity.this);
        tv_tomatotime_set.setText(tomatotime + "min");

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder;
        switch(view.getId()) {
            case R.id.ll_personinformation:
                Intent intent = new Intent(SettingActivity.this, PersonInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_passwordsetting:

                break;
            case R.id.ll_alertsetting:

                break;
            case R.id.ll_tomatotime:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.set_tomatotime);
                View view_tomatotime_set = getLayoutInflater().inflate(R.layout.alert_dialog_tomatotime_set, null);
                final EditText et_tomatotime_set = (EditText) view_tomatotime_set.findViewById(R.id.et_tomatotime_set);
                builder.setView(view_tomatotime_set);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String time = et_tomatotime_set.getText().toString().trim();
                        if (!time.equals("") && OtherUtils.isPositiveInteger(time)) {
                            tv_tomatotime_set.setText(time + "min");
                            UserInformation.setTomatoTime(SettingActivity.this, time);
                        } else {
                            Toast.makeText(SettingActivity.this, R.string.tomatotime_should_positiveinteger, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
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
