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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t.thisisdiary.R;
import com.example.t.thisisdiary.Utils.SQLiteUtil;
import com.example.t.thisisdiary.Utils.UserInformation;
import com.example.t.thisisdiary.base.BaseActivity;
import com.example.t.thisisdiary.bean.Tomato;

public class CountdownActivity extends BaseActivity {

    private int tomatotime;

    // 倒计时
    private TextView tv_minute; // 分

    private TextView tv_hour; // 时

    private TextView tv_second; // 秒

    private CountdownActivity.CountDown countDown = new CountdownActivity.CountDown();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_countdown, null);
        return view;
    }

    @Override
    public void initData() {

        // 使用Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_countdown);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); // 取得ActionBar实例（虽然具体实现是由Toolbar来完成的）
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 让导航按钮显示出来
            // actionBar.setHomeAsUpIndicator(R.drawable.icon_more); // 设置导航按钮图标
        }

        tomatotime = Integer.parseInt(UserInformation.getTomatoTime(CountdownActivity.this));

        tv_minute = (TextView) findViewById(R.id.tv_minute);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_second = (TextView) findViewById(R.id.tv_second);
        countDown = new CountDown();
        countDown.startCountDown();
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

    private void completeTomato() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CountdownActivity.this);
        builder.setTitle("恭喜完成一个番茄！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = getIntent();
                String time = intent.getStringExtra("tomataClockActivity->CountdownActivity");
                Tomato tomato = SQLiteUtil.querytTomatoByTime(time);
                SQLiteUtil.deleteTomato(time);
                tomato.setTomatoNumber(tomato.getTomatoNumber() - 1);
                tomato.setIsCompleted(tomato.getTomatoNumber() == 0 ? 1 : 0);
                SQLiteUtil.saveTomato(tomato.getTaskName(), tomato.getTomatoNumber(), tomato.getTime(), tomato.getIsCompleted());
                finish();
                //onBackPressed();
            }
        });
        if(!isFinishing()) {
            builder.show();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(CountdownActivity.this);
        builder.setTitle("番茄尚未完成，直接返回此番茄将被舍弃，确认返回？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /** 倒计时 内部类**/
    class CountDown {
        private int hour = tomatotime / 60;
        private int minute = tomatotime % 60;
        private int second = 0;
        private boolean isRun = true;

        Handler timeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    computeTime();
                    // 时分秒，不足10时，补0
                    if (hour < 10) {
                        tv_hour.setText("0" + hour);
                    } else {
                        tv_hour.setText(hour + "");
                    }
                    if (minute < 10) {
                        tv_minute.setText("0" + minute);
                    } else {
                        tv_minute.setText(minute + "");
                    }
                    if (second < 10) {
                        tv_second.setText("0" + second);
                    } else {
                        tv_second.setText(second + "");
                    }
                }
            }
        };

        private void startCountDown() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRun) {
                        try {
                            Thread.sleep(1000);
                            Message message = Message.obtain();
                            message.what = 1;
                            timeHandler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        private void computeTime() {
            second--;
            if (second < 0) {
                minute--;
                second = 59;
                if (minute < 0) {
                    hour--;
                    minute = 59;
                    if (hour < 0) {
                        // 倒计时结束
                        hour = 0;
                        minute = 0;
                        second = 0;
                        isRun = false;
                        //Toast.makeText(CountdownActivity.this, "恭喜完成一个番茄！", Toast.LENGTH_SHORT).show();
                        completeTomato();
                    }
                }
            }
        }
    }

}
