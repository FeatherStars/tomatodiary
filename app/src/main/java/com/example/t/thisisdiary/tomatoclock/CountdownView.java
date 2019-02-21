package com.example.t.thisisdiary.tomatoclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.t.thisisdiary.R;


/**
 * 倒计时控件
 */
public class CountdownView extends View {

    private int width; // 控件宽

    private int height; // 控件高

    private int dialRadius; // 刻度盘半径

    private float hourScaleHeight = dp2px(6); // 小时刻度高

    private float minuteScaleHeight = dp2px(4); // 分钟刻度高

    private float arcWidth = dp2px(6); // 定制进度条宽？？

    private int time = 0; // 时间-分

    private Paint dialPaint; // 刻度盘画笔

    private Paint timePaint; // 时间画笔



    private OnCountdownListener onCountdownListener; // 时间改变监听

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 刻度盘画笔
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true); // 抗锯齿
        dialPaint.setColor(getResources().getColor(R.color.colorPrimary));
        dialPaint.setStyle(Paint.Style.STROKE); // 只绘制圆形轮廓（描边）
        dialPaint.setStrokeCap(Paint.Cap.ROUND); // 设置笔刷类型：圆形

        // 时间画笔
        timePaint = new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setColor(getResources().getColor(R.color.colorPrimary));
        timePaint.setTextSize(sp2px(33)); // ??
        timePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    // onSizeChanged() 在控件大小发生改变时调用。所以这里初始化会被调用一次  作用：获取控件的宽和高度
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = height = Math.min(h, w); // 控件宽、高
        dialRadius = (int) (width / 2 - dp2px(10)); // 刻度盘半径
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**绘制刻度盘**/
    private void drawDial(Canvas canvas) {
        // 绘制外层圆盘
        dialPaint.setStrokeWidth(dp2px(2));
        canvas.drawCircle(width / 2, height / 2, dialRadius, dialPaint); // 参数：圆心的x坐标，圆心的y坐标，圆的半径，绘制用的画笔

        // 将坐标原点移到控件中心
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.save();

        // 绘制小时刻度
        for (int i = 0; i < 12; i++) {
            // 定时时间为0时正常绘制小时刻度
            // 小时刻度没有被定时进度条覆盖时正常绘制小时刻度
            if (time == 0 || time > time / 5) {
                // 参数：开始点x、y坐标，结束点x、y坐标
                canvas.drawLine(0, -dialRadius, 0, -dialRadius + hourScaleHeight, dialPaint);
            }
            canvas.rotate(30);
        }

        // 绘制分钟刻度
        dialPaint.setStrokeWidth(dp2px(1));
        for(int i = 0; i < 60; i++) {
            // 小时刻度位置不绘制分钟刻度
            // 分钟刻度没有被定时进度条覆盖时正常绘制分钟刻度
            if(i % 5 == 0 || i > time) {
                canvas.drawLine(0, -dialRadius, 0, -dialRadius + minuteScaleHeight, dialPaint);
            }
            canvas.rotate(6);
        }
    }

    /**绘制定时进度条**/
    private void drawArc(Canvas canvas) {
        if(time > 0) {
            // 绘制起始标志
            dialPaint.setStrokeWidth(dp2px(3));
            canvas.drawLine(0, -dialRadius - hourScaleHeight, 0, -dialRadius + hourScaleHeight, dialPaint);
            // TODO:以后再补充带圆形的时钟，先用数字表示
            // TODO：参考文档：https://www.jianshu.com/p/18c6f5d306b0
        }
    }

    /**绘制时间**/
    private void drawTime(Canvas canvas) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**以刻度盘圆心为坐标圆点，建立坐标系，求出(targetX, targetY)坐标与x轴的夹角**/
    private float calcAngle(float targetX, float targetY) {
        return 1;
    }

    /**
     * 计算时间
     * @param angle 增加的角度
     */
    private void calcTime(float angle) {

    }

    /**
     * 设置倒计时
     * @param minutes 分钟
     */
    public void setCountdown(int minutes) {

    }

    /**设置倒计时监听**/
    public void setOnCountdownListener(OnCountdownListener onCountdownListener) {

    }

    /**倒计时监听接口**/
    public interface OnCountdownListener {

    }

    /**dp转px**/
    public int dp2px(float dp) {
        return 1;
    }

    /**sp转px**/
    public int sp2px(float sp) {
        return 1;
    }
}
