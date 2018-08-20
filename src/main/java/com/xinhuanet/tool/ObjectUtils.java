package com.xinhuanet.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjectUtils {

    public static SimpleDateFormat simpleDateFormat;

    static {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Date判断是否为空
     *
     * @param date
     * @return
     */
    public static String isNotNull(Date date) {
        if (date != null) {
            return date.getTime() + "";
        }
        return "";
    }

    public static String DateTimeFormat(Date date) {
        String format = simpleDateFormat.format(date);
        return format;
    }

}
