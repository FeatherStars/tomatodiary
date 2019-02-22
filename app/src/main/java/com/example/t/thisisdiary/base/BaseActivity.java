package com.example.t.thisisdiary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity{

    public View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = initView();
        setContentView(mRootView);
        initData();
    }

    /** 初始化视图 **/
    public abstract View initView();

    /** 初始化数据 **/
    public abstract void initData();



}
