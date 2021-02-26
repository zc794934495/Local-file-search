package com.daimao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LogUtil {
    // 类型... 可变参数列表
    // Object... 代表，任意类型，任意长度都可以传入
    public static void log(String format, Object... args) {
        String message = String.format(format, args);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String now = dateFormat.format(date);
        System.out.printf("%s: %s\n", now, message);
    }

}
