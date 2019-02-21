package com.example.t.thisisdiary.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.t.thisisdiary.R;

public class UserInformation {

    public final static String FILE_NAME = "tomato-diary"; // SharedPreferences文件的名称

    private final static String ACCOUNT_ID = "account_id"; // 账号ID，是唯一的

    public final static String USER_PASSWORD = "user_password";

    private final static String USER_NAME = "user_name";

    private final static String USER_SIGNATURE = "user_signature";

    public final static String USER_MAIL = "user_mail";

    public final static String USER_PHONE_NUMBER = "user_phone_number";

    public final static String USER_WECHAT = "user_wechat";

    public final static String USER_QQ = "user_qq";

    // 番茄时钟相关
    public final static String TOMATO_TIME = "tomato_time"; // 单位：分钟


    public static void setAccountId (Context context, String accountId) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(ACCOUNT_ID, accountId).commit();
    }

    public static String getAccountId (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(ACCOUNT_ID, "---");
    }

    public static void setUserName (Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_NAME, userName).commit();
    }

    public static String getUserName (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_NAME, context.getResources().getString(R.string.click_edit_username));
    }

    public static void setUserPassword (Context context, String userPassword) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_PASSWORD, userPassword).commit();
    }

    public static String getUserPassword (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_PASSWORD, "");
    }

    public static void setUserSignature (Context context, String userSignature) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_SIGNATURE, userSignature).commit();
    }

    public static String getUserSignature (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_SIGNATURE, context.getResources().getString(R.string.edit_signature));
    }

    public static void setUserMail (Context context, String userMail) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_MAIL, userMail).commit();
    }

    public static String getUserMail (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_MAIL, "---");
    }

    public static void setUserPhoneNumber (Context context, String userPhoneNumber) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_PHONE_NUMBER, userPhoneNumber).commit();
    }

    public static String getUserPhoneNumber (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_PHONE_NUMBER, "---");
    }

    public static void setUserWeChat (Context context, String userWeChat) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_WECHAT, userWeChat).commit();
    }

    public static String getUserWeChat (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_WECHAT, "---");
    }

    public static void setUserQQ (Context context, String userQQ) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_QQ, userQQ).commit();
    }

    public static String getUserQQ (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_QQ, "---");
    }

    // 番茄时钟相关
    public static void setTomatoTime (Context context, String tomatotime) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(TOMATO_TIME, tomatotime).commit();
    }

    public static String getTomatoTime (Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(TOMATO_TIME, "25");
    }
}
