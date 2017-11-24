package com.project.newmusicplayer.Util;

import java.text.SimpleDateFormat;

/**
 * Created by 정인섭 on 2017-11-16.
 */

public class TimeConverter {

    public static String converter(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(time);

    }

    public static String miliToSec(int mili) {
        int sec = mili / 1000;
        int min = sec / 60;
        sec = sec % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    public static String getSec(int sec){
        int min = sec/60;
        sec = sec % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    public static int miliToSecInt(int mili){
        return mili/1000;
    }

    public static String miliToSec(String mili){
        return miliToSec(Integer.parseInt(mili));
    }
}
