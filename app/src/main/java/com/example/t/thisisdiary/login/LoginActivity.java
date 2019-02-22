package com.example.t.thisisdiary.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.main.MainActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_skip;

    private EditText et_username;

    private EditText et_password;

    private Button btn_login;

    private Button btn_register;

    private TextView tv_forget_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_login, null);
        return view;
    }

    @Override
    public void initData() {
        btn_skip = (Button) findViewById(R.id.btn_skip);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);

        btn_skip.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_skip:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                break;
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }

    /** 退出程序提示 **/
    /*
    long startTime = 0;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            startTime = currentTime;
        } else {
            finish();
        }
    }*/
}
