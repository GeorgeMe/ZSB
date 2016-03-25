package com.dmd.zsb.api;

public class ApiConstants {

    public static final class Urls {

        public static final String API_BASE_URLS = "http://192.168.1.111:8080/TutorClient/";
        public static final String API_USER_INITDATA = "user_initdata.action";//注册
        public static final String API_USER_CHECKVERIFYCODE = "user_checkverifycode.action?";//检验认证码
        public static final String API_USER_SIGNUP = "user_signup.action?";//注册
        public static final String API_USER_SIGNIN = "user_signin.action?";//登陆
        public static final String API_USER_SIGNOUT = "user_signout.action?";//退出
        public static final String API_USER_BALANCE = "user_balance.action?";//余额
        public static final String API_USER_PROFILE = "user_profile.action?";//信息简介
        public static final String API_USER_LIST = "user_list.action?";//用户列表
        public static final String API_USER_DETAIL = "user_detail.action?";//用户列表
        public static final String API_USER_CHANGEAVATAR = "user_changeavatar.action?";//修改头像
        public static final String API_USER_LOGFILE = "user_logFile.action?";//上传日志
        public static final String API_USER_CHANGEPROFILE = "user_changeprofile.action?";//修改信息简介
        public static final String API_USER_CERTIFY = "user_certify.action?";//认证
        public static final String API_USER_CHANGEPASSWORD = "user_changepassword.action?";//修改密码
        public static final String API_USER_AUDITION = "user_changepassword.action?";//修改密码
        public static final String API_USER_FEEDBACK = "user_feedback.action?";//反馈


    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        public static final int PAGE_LIMIT = 20;
    }
}