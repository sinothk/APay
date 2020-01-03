package com.sinothk.pay.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String StringToTimestamp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);

        int times = 0;
        try {
            times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (times == 0) {
            System.out.println("String转10位时间戳失败");
        }
        return times + "";

    }
}
