package com.example.t.thisisdiary.tomatoclock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.MyDatabaseHelper;
import com.example.t.thisisdiary.Utils.OtherUtils;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.Utils.TimeUtil;
import com.example.t.thisisdiary.Utils.UserInformation;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.bean.Tomato;
import com.example.t.thisisdiary.main.DiaryListAdapter;
import com.example.t.thisisdiary.setting.PersonInformationActivity;

import java.util.ArrayList;
import java.util.List;

public class TomatoClockActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<Tomato> tomatoList = new ArrayList<>();

    private ListView lv_tomato;

    private TomatoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_tomatoclock, null);
        return view;
    }

    @Override
    public void initData() {
        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tomatoclock);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        initTomatoes();

        adapter = new TomatoAdapter(TomatoClockActivity.this, R.layout.tomato_item, tomatoList);
        lv_tomato = (ListView) findViewById(R.id.lv_tomato);
        lv_tomato.setAdapter(adapter);

        lv_tomato.setOnItemClickListener(this); // ListView点击事件
    }

    private void initTomatoes() {
        tomatoList = SQLiteUtil.queryAllTomato();
        //Tomato tomato = new Tomato("dddddddd", 8);
        //tomatoList.add(tomato);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tomatoList.clear();
        tomatoList.addAll(SQLiteUtil.queryAllTomato());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Tomato tomato = tomatoList.get(position);
        if(tomato.getIsCompleted() == 1) {
            Toast.makeText(TomatoClockActivity.this, "此任务已完成！", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(TomatoClockActivity.this, CountdownActivity.class);
            intent.putExtra("tomataClockActivity->CountdownActivity", tomato.getTime());
            startActivity(intent);
        }
    }

    /** 加载Toolbar **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_tomatoclock, menu);
        return true;
    }

    /** 处理toolbar中各个按钮的点击事件 **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // HomeAsUp按钮的id永远是android.R.id.home
                onBackPressed();
                break;
            case R.id.toolbar_tomato_add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("添加一个任务");
                View view_tomato_add = getLayoutInflater().inflate(R.layout.alert_dialog_tomato_add, null);
                final EditText et_task_name = (EditText) view_tomato_add.findViewById(R.id.et_task_name);
                final EditText et_tomato_number = (EditText) view_tomato_add.findViewById(R.id.et_tomato_number);
                builder.setView(view_tomato_add);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(et_task_name.getText())) {
                            Toast.makeText(TomatoClockActivity.this, "任务内容不得为空", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(et_tomato_number.getText()) || !OtherUtils.isInteger(et_tomato_number.getText().toString())){
                            Toast.makeText(TomatoClockActivity.this, "番茄个数必须是整数", Toast.LENGTH_SHORT).show();
                        } else {
                            String taskName = et_task_name.getText().toString();
                            int tomatoNumber = Integer.parseInt(et_tomato_number.getText().toString());
                            String time = TimeUtil.getCurrentTime();
                            Tomato tomato = new Tomato();
                            tomato.setTaskName(taskName);
                            tomato.setTomatoNumber(tomatoNumber);
                            tomato.setTime(time);
                            tomato.setIsCompleted(0);
                            SQLiteUtil.saveTomato(taskName, tomatoNumber, time, 0);
                            tomatoList.add(tomato);
                            adapter.notifyDataSetChanged();
                            //lv_tomato.setSelection(0);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
        return true;
    }
}
