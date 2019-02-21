package com.example.t.thisisdiary.recyclebin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.bean.Diary;
import com.example.t.thisisdiary.main.DiaryListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecycleBinActivity extends BaseActivity implements
        DiaryListAdapter.OnItemClickListener,
        DiaryListAdapter.OnItemLongClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srl_recyclebin_diary_list;

    private RecyclerView rv_recyclebin_diary_list;

    private GridLayoutManager layoutManager;

    private DiaryListAdapter adapter;

    private List<Diary> diaryList = new ArrayList<>();

    private TextView tv_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_recyclebin, null);
        return view;
    }

    @Override
    public void initData() {
        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recyclebin);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        initDiaries();

        // 回收站日记列表
        rv_recyclebin_diary_list = (RecyclerView) findViewById(R.id.rv_recyclebin_diary_list);
        layoutManager = new GridLayoutManager(this, 1);
        rv_recyclebin_diary_list.setLayoutManager(layoutManager);
        adapter = new DiaryListAdapter(diaryList);
        rv_recyclebin_diary_list.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        // 下拉刷新
        srl_recyclebin_diary_list = (SwipeRefreshLayout) findViewById(R.id.srl_recyclebin_diary_list);
        srl_recyclebin_diary_list.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        srl_recyclebin_diary_list.setOnRefreshListener(this);

        tv_empty = (TextView) findViewById(R.id.tv_empty);

        yesOrNo();

    }

    private void initDiaries() {
        diaryList = SQLiteUtil.queryRecycleBin();
        Collections.reverse(diaryList); // 倒序

    }

    /** 加载Toolbar **/
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_recyclebin, menu);
        return true;
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

    @Override
    public void onRefresh() {
        refreshDiaries();
    }

    private void refreshDiaries() {
        srl_recyclebin_diary_list.setRefreshing(false);
    }

    @Override
    public void onClick(View parent, int position) {
        Toast.makeText(RecycleBinActivity.this, R.string.view_after_restore, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View parent, int position) {
        new RecyclebinBottomView(RecycleBinActivity.this, position);
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void yesOrNo() {
        if (diaryList.size() == 0) {
            srl_recyclebin_diary_list.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            srl_recyclebin_diary_list.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);
        }
    }

    /** 内部类 **/
    class RecyclebinBottomView {

        Context context;

        private PopupWindow popupWindow;

        View popupWindowView;

        public RecyclebinBottomView (Context context, int position) {
            this.context = context;
            initPopupWindow(position);
        }

        public void initPopupWindow(int position) {
            popupWindowView = LayoutInflater.from(context).inflate(R.layout.bottomview_recyclebin, null);
            popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setAnimationStyle(R.style.BottomSelectAnimationShow);
            // 菜单背景色。
            ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(0x03, 0xA8, 0x9E));
            popupWindow.setBackgroundDrawable(colorDrawable);
            // 这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
            popupWindow.showAtLocation(LayoutInflater.from(context).inflate(R.layout.activity_recyclebin, null),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            // 设置背景半透明
            backgroundAlpha(0.8f);

            dealWithSelect(position); // 处理点击事件

            popupWindow.setOnDismissListener(new popupDismissListener());

            // 暂时用不上
            popupWindowView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // 这里如果返回true的话，touch事件将被拦截,
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                    return false;
                }
            });
        }

        /** 处理点击事件 **/
        private void dealWithSelect(final int position) {
            // 删除：真正的删除，从数据库删除
            popupWindowView.findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 设置日记的回收站标志位
                    String time = diaryList.get(position).getTime();
                    SQLiteUtil.deleteDiary(time);
                    adapter.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(RecycleBinActivity.this, R.string.has_deleted, Toast.LENGTH_SHORT).show();
                    dismiss();
                    yesOrNo();

                }
            });
            // 还原
            popupWindowView.findViewById(R.id.ll_restore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String time = diaryList.get(position).getTime();
                    SQLiteUtil.updateIsRecycleBin(0, time);
                    adapter.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, R.string.has_restored, Toast.LENGTH_SHORT).show();
                    dismiss();
                    yesOrNo();
                }
            });
        }

        /** 设置popupwindow外面背景透明度 0-1  **/
        public void backgroundAlpha(float alpha) {
            WindowManager.LayoutParams layoutParams = ((Activity) context).getWindow().getAttributes();
            layoutParams.alpha = alpha;
            ((Activity) context).getWindow().setAttributes(layoutParams);
        }

        class popupDismissListener implements PopupWindow.OnDismissListener {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        }

        public void dismiss() {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    }
}
