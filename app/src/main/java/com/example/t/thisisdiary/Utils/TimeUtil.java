package com.example.t.thisisdiary.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /** 取得当前系统时间 **/
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());//new Date()为获取当前系统时间
    }
}
