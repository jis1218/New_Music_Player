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
}
