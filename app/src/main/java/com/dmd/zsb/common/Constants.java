package com.dmd.zsb.common;

/**
 *
 */
public class Constants {

    public static final String USER_ROLE="parent";
    public static final String PLATFORM="android";

    //短信验证key
    public static final String SMSAPPKEY = "f3fc6baa9ac4";
    public static final String SMSAPPSECRET = "7f3dedcb36d92deebcb373af921d635a";

    public static final int EVENT_BEGIN = 0X100;
    public static final int EVENT_REFRESH_DATA = EVENT_BEGIN + 10;
    public static final int EVENT_LOAD_MORE_DATA = EVENT_BEGIN + 20;
    public static final int EVENT_START_PLAY_MUSIC = EVENT_BEGIN + 30;
    public static final int EVENT_STOP_PLAY_MUSIC = EVENT_BEGIN + 40;

    public static final int EVENT_RECOMMEND_COURSES_HOME = 100;
    public static final int EVENT_RECOMMEND_COURSES_SEEK = 101;


}
