package com.example.t.thisisdiary.Utils;

import java.util.regex.Pattern;

public class OtherUtils {

    /** 判断字符串内容是否为整数 **/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    // ToDO:看看上面是为啥

    /** 判断字符串内容是否为正整数 **/
    public static boolean isPositiveInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches() ;
    }

}
