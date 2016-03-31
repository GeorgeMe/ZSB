package com.dmd.zsb.utils;


import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.api.ApiConstants;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UriHelper {

    private static volatile UriHelper instance = null;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;


    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance) {
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }
    //页码处理
    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    private String urlToString(boolean flag,String action,JsonObject json){
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(ApiConstants.Urls.API_BASE_URLS);
        stringbuffer.append(action);
        if (flag){
            stringbuffer.append("json=");
            stringbuffer.append(json);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SSS");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        TLog.d("UriHelper",time+" 请求 ："+stringbuffer.toString().trim());
        return stringbuffer.toString().trim();
    }

    //初始化数据
    public String InitData(JsonObject json){
        return urlToString(false,ApiConstants.Urls.API_USER_INITDATA,json);
    }

    //验证码
    public String checkVerifyCode(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHECKVERIFYCODE,json);
    }
    //注册
    public String signUp(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNUP,json);
    }
    //登陆
    public String signIn(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNIN,json);
    }
    //退出
    public String signOut(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNOUT,json);
    }
    //账户余额
    public String balance(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BALANCE,json);
    }
    //用户简介
    public String profile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_PROFILE,json);
    }
    //用户列表
    public String userList(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_LIST,json);
    }
    //用户详情
    public String userDetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_DETAIL,json);
    }
    //修改头像
    public String changeAvatar(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEAVATAR,json);
    }
    //上传日志
    public String logFile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_LOGFILE,json);
    }
    //修改用户简介
    public String changeProfile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPROFILE,json);
    }
    //认证
    public String certify(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CERTIFY,json);
    }
    //修改密码
    public String changePassword(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPASSWORD,json);
    }
    //试听
    public String onAudition(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_AUDITION,json);
    }
    //反馈
    public String seedFeedback(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FEEDBACK,json);
    }
    //评论
    public String evaluation(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_EVALUATION,json);
    }
    //昵称
    public String nickName(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_NICKNAME,json);
    }
    //钱包
    public String wallet(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_WALLET,json);
    }

    //需求
    public String demand(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_DEMAND,json);
    }

    //代金券
    public String vouchers(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_VOUCHERS,json);
    }

    //保存订单信息
    public String saveOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_SAVEORDER,json);
    }
    //附近订单
    public String aroundOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_AROUND,json);
    }
    //我的订单
    public String myorder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_MYORDER,json);
    }

    //订单信息
    public String getOrderInfo(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_GETORDERINFO,json);
    }
    //取消订单
    public String cancelOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CANCEL,json);
    }
    //接受订单
    public String acceptOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_ACCEPT,json);
    }
    //支付订单
    public String payOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_PAY,json);
    }
    //确认支付
    public String confirmpay(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CONFIRMPAY,json);
    }
    //完成订单
    public String workdone(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_WORKDONE,json);
    }
    //订单状态
    public String orderHistory(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_HISTORY,json);
    }
    //接受订单列表
    public String getReceivedOrders(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_GETRECEIVEDORDERS,json);
    }
    //修改订单状态
    public String updateOrderStatus(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_UPDATEORDERSTATUS,json);
    }
}
