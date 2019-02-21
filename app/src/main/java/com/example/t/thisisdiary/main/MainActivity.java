package com.example.t.thisisdiary.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.Utils.UserInformation;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.feedback.FeedbackActivity;
import com.example.t.thisisdiary.recyclebin.RecycleBinActivity;
import com.example.t.thisisdiary.secret.SecretActivity;
import com.example.t.thisisdiary.setting.PersonInformationActivity;
import com.example.t.thisisdiary.setting.SettingActivity;
import com.example.t.thisisdiary.tomatoclock.TomatoClockActivity;
import com.example.t.thisisdiary.viewdiary.ViewDiaryActivity;
import com.example.t.thisisdiary.writediary.WriteDiary;
import com.example.t.thisisdiary.bean.Diary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener,
            DiaryListAdapter.OnItemClickListener,
            DiaryListAdapter.OnItemLongClickListener,
            NavigationView.OnNavigationItemSelectedListener,
            SwipeRefreshLayout.OnRefreshListener{

    private DrawerLayout mDrawerLayout;

    private List<Diary> diaryList = new ArrayList<>();

    private RecyclerView rv_diary_list; // 日记列表

    private DiaryListAdapter diaryListAdapter;

    private GridLayoutManager layoutManager;

    private SwipeRefreshLayout srl_diary_list; // 为实现下拉刷新

    private FloatingActionButton fab_new_diary;

    private NavigationView nav_view;

    private CircleImageView nav_header_head_protrait;

    private TextView nav_header_user_name;

    private TextView nav_header_signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_main, null);
        return view;
    }

    @Override
    public void initData() {

        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.icon_navi); // 设置导航按钮图标
        }

        // DrawerLayout布局，其中允许放入两个直接子控件，第一个是主屏幕中显示的内容，第二个是滑动菜单的内容
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout); // 取得DrawerLayout实例
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_menu_tomato_clock); // 设置默认选中菜单项
        // 设置菜单项选中事件监听器
        navView.setNavigationItemSelectedListener(this);

        //悬浮按钮
        fab_new_diary = (FloatingActionButton) findViewById(R.id.fab_new_diary);
        fab_new_diary.setOnClickListener(this);

        initDiaries();

        // 日记列表
        rv_diary_list = (RecyclerView) findViewById(R.id.rv_diary_list);
        layoutManager = new GridLayoutManager(this, 2);
        rv_diary_list.setLayoutManager(layoutManager);
        diaryListAdapter = new DiaryListAdapter(diaryList);
        diaryListAdapter.setOnItemClickListener(this);
        diaryListAdapter.setOnItemLongClickListener(this);
        rv_diary_list.setAdapter(diaryListAdapter);
        // 下拉刷新
        srl_diary_list = (SwipeRefreshLayout) findViewById(R.id.srl_diary_list);
        srl_diary_list.setColorSchemeResources(R.color.colorPrimary);
        srl_diary_list.setOnRefreshListener(this);
        srl_diary_list.setOnRefreshListener(this);

        // 滑动菜单头部
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = nav_view.getHeaderView(0);
        nav_header_head_protrait = headerLayout.findViewById(R.id.nav_header_head_protrait);
        nav_header_user_name = headerLayout.findViewById(R.id.nav_header_user_name);
        nav_header_signature = headerLayout.findViewById(R.id.nav_header_signature);
        nav_header_head_protrait.setOnClickListener(this);
        nav_header_user_name.setOnClickListener(this);
        nav_header_signature.setOnClickListener(this);
        String userName = UserInformation.getUserName(MainActivity.this);
        String userSignature = UserInformation.getUserSignature(MainActivity.this);
        nav_header_user_name.setText(userName);
        nav_header_signature.setText(userSignature);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nav_header_user_name.setText(UserInformation.getUserName(MainActivity.this));
        nav_header_signature.setText(UserInformation.getUserSignature(MainActivity.this));
        refreshDiaries();
    }

    private void initDiaries() {
        diaryList = SQLiteUtil.queryMain(); // 查询不在回收站且非私密的日记
        Collections.reverse(diaryList); // 倒序
    }

    private void refreshDiaries () {
        diaryList.clear();
        diaryList.addAll(SQLiteUtil.queryMain());
        //diaryList = SQLiteUtil.queryMain(); // 这样写不对！！！这样diaryList指向了新的地址，而adapter还关联着原来位置的数据。
        Collections.reverse(diaryList); // 倒序

        diaryListAdapter.notifyDataSetChanged();
        srl_diary_list.setRefreshing(false);
    }

    /** 加载Toolbar **/
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_main_search).getActionView();
        // 假设当前的活动是可搜索的活动？
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (diaryListAdapter != null) {
                    diaryListAdapter.getFilter().filter(s);
                }
                return false;
            }
        });
        return true;
    }

    /** 处理toolbar中各个按钮的点击事件 **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_main_search:
                //Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                //startActivity(intent);
                break;
            case android.R.id.home: // HomeAsUp按钮的id永远是android.R.id.home
                hideKeyboard(getWindow().getDecorView());
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fab_new_diary: // 新建日记
                intent = new Intent(MainActivity.this, WriteDiary.class);
                startActivityForResult(intent, 1); // 下一个活动返回数据给本活动
                break;
            case R.id.nav_header_head_protrait:
                intent = new Intent(MainActivity.this, PersonInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_header_user_name:
                intent = new Intent(MainActivity.this, PersonInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_header_signature:
                intent = new Intent(MainActivity.this, PersonInformationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /** 下拉刷新事件 **/
    @Override
    public void onRefresh() {
        refreshDiaries();
    }

    /** RecyclerView子项点击事件 **/
    @Override
    public void onClick(View parent, int position) {
        Diary diary = diaryList.get(position);
        Intent intent = new Intent(MainActivity.this, ViewDiaryActivity.class);
        intent.putExtra("mainactivity_diary", diary);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onLongClick(View parent, int position) {
        fab_new_diary.hide(); // 悬浮按钮隐藏
        MainBottomView mainBottomView = new MainBottomView(MainActivity.this, position);
        return true; // 此处是false，才会出现上下文菜单
    }

    int night_mode = 0;
    /** 滑动菜单页菜单项选中事件 **/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_menu_tomato_clock:
                intent = new Intent(MainActivity.this, TomatoClockActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_menu_night_mode:
                //TODO:待修改
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                break;
            case R.id.nav_menu_secret:
                intent = new Intent(MainActivity.this, SecretActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_menu_recycle_bin:
                intent = new Intent(MainActivity.this, RecycleBinActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_menu_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_menu_feedback:
                intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case 1: // 从WriteDiary返回新保存的diary
                if (resultCode == RESULT_OK) {
                    try {
                        Diary diary = (Diary) data.getSerializableExtra("writediary_newdiary");
                        diaryListAdapter.add(diary, 0);
                        rv_diary_list.scrollToPosition(0); // 把列表移动到顶端
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                break;
//            case 2:
//                if(resultCode == RESULT_OK) {
//                    Diary diary = (Diary) data.getSerializableExtra("viewdiaryactivity_diary_to_mainactivity");
//                    diaryListAdapter.add(diary, 0);
//                    rv_diary_list.scrollToPosition(0); // 把列表移动到顶端
//                }
//                break;
            default:
                break;
        }
    }

    /** 内部类（可以轻松地访问外部类中的私有属性。（内部类的最大好处）） **/
    class MainBottomView {

        Context context;

        private PopupWindow popupWindow;

        View popupWindowView;

        public MainBottomView (Context context, int position) {
            this.context = context;
            initPopupWindow(position);
        }

        /** 初始化 **/
        public void initPopupWindow(int position) {

            popupWindowView = LayoutInflater.from(context).inflate(R.layout.bottomview_main, null);
            popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            popupWindow.setAnimationStyle(R.style.BottomSelectAnimationShow);
            // 菜单背景色。
            ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(0x03, 0xA8, 0x9E));
            popupWindow.setBackgroundDrawable(colorDrawable);
            // 这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
            popupWindow.showAtLocation(LayoutInflater.from(context).inflate(R.layout.activity_main, null),
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
            // 删除：不是真正的删除，先放入回收站
            popupWindowView.findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 设置日记 回收站 标志位 为1
                    String time = diaryList.get(position).getTime();
                    SQLiteUtil.updateIsRecycleBin(1, time);
                    // 删除日记
                    diaryListAdapter.remove(position);
                    diaryListAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, R.string.has_deleted, Toast.LENGTH_SHORT).show();
                    dismiss();
                    fab_new_diary.show();
                }
            });
            // 置顶
            popupWindowView.findViewById(R.id.ll_top).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, R.string.has_placed_top, Toast.LENGTH_SHORT).show();
                    dismiss();
                    fab_new_diary.show();
                }
            });
            // 设为私密
            popupWindowView.findViewById(R.id.ll_secret).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 设置日记 私密 标志位 为1
                    String time = diaryList.get(position).getTime();
                    SQLiteUtil.updateIsSecret(1, time);
                    // 在首页移除日记
                    diaryListAdapter.remove(position);
                    diaryListAdapter.notifyDataSetChanged();
                    Toast.makeText(context, R.string.has_setted_secret, Toast.LENGTH_SHORT).show();
                    dismiss();
                    fab_new_diary.show();
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
                fab_new_diary.show(); // 点击PopupWindow外面区域，悬浮按钮重新显示。
            }
        }

        public void dismiss() {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
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
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            startTime = currentTime;
        } else {
            finish();
        }
    }
    */
}
