package com.dmd.zsb.api;

public class ApiConstants {

    public static final class Urls {

        public static final String API_BASE_URLS = "http://192.168.1.105:8080/TutorClient/";
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
        public static final String API_USER_FEEDBACK = "user_feedback.action?";//反馈
        public static final String API_USER_EVALUATION = "user_evaluation.action?";//反馈
        public static final String API_USER_NICKNAME = "user_nickName.action?";//昵称
        public static final String API_USER_AUDITION = "user_audition.action?";//试听

        // public static final String API_USER_NICKNAME = "user_nickName.action?";//评价列表
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//评价
         public static final String API_USER_WALLET = "user_wallet.action?";//钱包
         public static final String API_USER_VOUCHERS = "user_vouchers.action?";//钱包
         public static final String API_USER_DEMAND = "user_demand.action?";//钱包
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//提现
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//提现进度
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//交易明细
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//我的订单列表
        // public static final String API_USER_NICKNAME = "user_nickName.action?";//我的需求列表


        public static final String API_ORDER_SAVEORDER = "order_saveOrder.action?";//保存订单
        public static final String API_ORDER_AROUND = "order_around.action?";//附近订单
        public static final String API_ORDER_MYORDER= "order_myorder.action?";//我的订单
        public static final String API_ORDER_GETORDERINFO = "order_getOrderInfo.action?";//获取订单信息
        public static final String API_ORDER_CANCEL = "order_cancel.action?";//取消订单
        public static final String API_ORDER_ACCEPT = "order_accept.action?";//接受订单
        public static final String API_ORDER_PAY = "order_pay.action?";//支付订单
        public static final String API_ORDER_CONFIRMPAY = "order_confirmpay.action?";//确认支付
        public static final String API_ORDER_WORKDONE = "order_workdone.action?";//完成订单
        public static final String API_ORDER_HISTORY = "order_history.action?";//订单状态
        public static final String API_ORDER_GETRECEIVEDORDERS = "order_getReceivedOrders.action?";//接受订单列表
        public static final String API_ORDER_UPDATEORDERSTATUS = "order_updateOrderStatus.action?";//修改订单状态

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        public static final int PAGE_LIMIT = 20;
    }
}