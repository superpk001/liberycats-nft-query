package com.libertycats.util;

import com.libertycats.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author A赚哥小迷弟-鱼蛋
 * @version 1.0
 * 2024/8/20 12:35
 **/
public class MyDateUtil {

    /**
     * 获取年月的字符格式
     *
     * @return
     */
    public static String getYearMothStr() {
        return new SimpleDateFormat("yyyyMM").format(new Date());
    }

    /**
     * 获取年月日时分秒的字符格式
     *
     * @return
     */
    public static String getNowDateStr() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 获取白皮书之日
     *
     * @return
     * @throws Exception
     */
    public static Date getWhitePaperDate() throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(Constants.whitePaperDate);
    }

    /**
     * * 获取指定的时间戳对应的时间
     *
     * @param timestamp 时间戳
     * @return String 年-月-日 时:分:秒
     */
    public static Date timestampToDate(String timestamp) {
        Date date = null;
        try {
            long temp = Long.valueOf(timestamp.trim());
            date = new Date(temp * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
