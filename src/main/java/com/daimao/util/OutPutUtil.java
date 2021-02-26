package com.daimao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OutPutUtil {
    /**
     * 对文件长度进行处理
     */
    public static String formatLength(Long length) {
        if(length < 1024) {
            return length + "Byte";
        }
        return length/1024 + "KB";
    }

    /**
     * 时间戳转换为yyyy-MM-dd HH:MM:ss的日期格式
     */
    public static String formatTimestamp(Long lastModifiedTimestamp) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        Date date = new Date(lastModifiedTimestamp);
        return format.format(date);
    }
}
