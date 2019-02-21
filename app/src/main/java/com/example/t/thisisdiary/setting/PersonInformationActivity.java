package com.example.t.thisisdiary.setting;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.UserInformation;
import com.example.t.thisisdiary.base.BaseActivity;

public class PersonInformationActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_account_id;

    private LinearLayout ll_username;

    private TextView tv_username_edit;

    private LinearLayout ll_user_signature;

    private TextView tv_user_signature_edit;

    private LinearLayout ll_mail;

    private LinearLayout ll_phone_number;

    private LinearLayout ll_wechat;

    private LinearLayout ll_qq;

    //private EditText et_username; // 弹出修改用户名对话框中EditText

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_personinformation, null);
        return view;
    }

    @Override
    public void initData() {
        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_personinformation);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        ll_account_id = (LinearLayout) findViewById(R.id.ll_account_id);
        ll_username = (LinearLayout) findViewById(R.id.ll_username);
        ll_user_signature = (LinearLayout) findViewById(R.id.ll_user_signature);
        ll_mail = (LinearLayout) findViewById(R.id.ll_mail);
        ll_phone_number = (LinearLayout) findViewById(R.id.ll_phone_number);
        ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        ll_qq = (LinearLayout) findViewById(R.id.ll_qq);
        ll_account_id.setOnClickListener(this);
        ll_username.setOnClickListener(this);
        ll_user_signature.setOnClickListener(this);
        ll_mail.setOnClickListener(this);
        ll_phone_number.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        tv_username_edit = (TextView)findViewById(R.id.tv_username_edit);
        tv_user_signature_edit = (TextView)findViewById(R.id.tv_user_signature_edit);
        String userName = UserInformation.getUserName(PersonInformationActivity.this);
        String userSignature = UserInformation.getUserSignature(PersonInformationActivity.this);
        tv_username_edit.setText(userName);
        tv_user_signature_edit.setText(userSignature);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder;
        switch(view.getId()) {
            case R.id.ll_account_id:
                break;
            case R.id.ll_username:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.modify_username);
                View view_username = getLayoutInflater().inflate(R.layout.alert_dialog_username, null);
                final EditText et_username = (EditText) view_username.findViewById(R.id.et_username);
                builder.setView(view_username);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userName = et_username.getText().toString().trim();
                        if (TextUtils.isEmpty(userName)) { // TODO：这里用et_username.getText().equals("")不可以，为什么？？？
                            Toast.makeText(PersonInformationActivity.this, R.string.username_can_not_empty, Toast.LENGTH_SHORT).show();
                        } else {
                            tv_username_edit.setText(userName);
                            UserInformation.setUserName(PersonInformationActivity.this, userName);
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
            case R.id.ll_user_signature:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.modify_signature);
                View view_user_signature = getLayoutInflater().inflate(R.layout.alert_dialog_user_signature, null);
                final EditText et_user_signature = (EditText) view_user_signature.findViewById(R.id.et_user_signature);
                builder.setView(view_user_signature);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userSignature = et_user_signature.getText().toString().trim();
                        if (TextUtils.isEmpty(userSignature)) {
                            Toast.makeText(PersonInformationActivity.this, R.string.signature_can_not_empty, Toast.LENGTH_SHORT).show();
                        } else {
                            tv_user_signature_edit.setText(userSignature);
                            UserInformation.setUserSignature(PersonInformationActivity.this, userSignature);
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
            case R.id.ll_mail:
                break;
            case R.id.ll_phone_number:
                break;
            case R.id.ll_wechat:
                break;
            case R.id.ll_qq:
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
