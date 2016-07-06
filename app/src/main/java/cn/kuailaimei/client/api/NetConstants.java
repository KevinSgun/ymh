package cn.kuailaimei.client.api;

import com.zitech.framework.data.network.Constants;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class NetConstants extends Constants{

    /**普通api后缀*/
    public static final String COMMON_SUFFIX = "klm/api/";

    /**文件上传相关后缀*/
    public static final String MANAGE_SUFFIX = "klm/manage/";

    //1.获取验证码
    public static final String USERS_CODE = COMMON_SUFFIX+"users/code";

    //2.用户注册
    public static final String USERS_REGISTER = COMMON_SUFFIX+"users/register";

    //3.用户登陆
    public static final String USERS_LOGIN = COMMON_SUFFIX+"users/login";

    //4.修改昵称
    public static final String USERS_UPDATENICK = COMMON_SUFFIX+"users/updateNick";

    //5.修改头像
    public static final String USERS_UPDATEPORTRAIT = COMMON_SUFFIX+"users/updatePortrait";

//    //6.修改性别 命名有问题，待确认
//    public static final String USERS_CODE = COMMON_SUFFIX+"users/code";

    //7.修改密码
    public static final String USERS_UPDATEPASSWORD = COMMON_SUFFIX+"users/updatePassword";

    //8.找回密码
    public static final String USERS_FORGET = COMMON_SUFFIX+"users/forget";

    //9.添加或修改收货地址
    public static final String USERS_UPDATEADDRESS = COMMON_SUFFIX+"users/updateAddress";

    //10.获取收货地址
    public static final String USERS_ADDRESS = COMMON_SUFFIX+"users/address";

    //11.获取收货地址详细信息
    public static final String USERS_ADDRESSDETAIL = COMMON_SUFFIX+"users/addressDetail";

    //12.删除收货地址
    public static final String USERS_ADDRESSDELETE = COMMON_SUFFIX+"users/addressDelete";

    //13.设置默认收货地址
    public static final String USERS_ADDRESSSTATUS = COMMON_SUFFIX+"users/addressStatus";

    //14.我要反馈
    public static final String FEEDBACK = COMMON_SUFFIX+"feedback";

    //15.首页
    public static final String HOME_LOAD = COMMON_SUFFIX+"home/load";

    //16.我要美容美发
    public static final String HOME_SALON = COMMON_SUFFIX+"home/salon";

    //17.上传文件
    public static final String UPLOAD = MANAGE_SUFFIX+"upload";

    //18.我要美容美发店铺筛选
    public static final String STORE_LIST = COMMON_SUFFIX+"store/list";

    //19.消息接口
    public static final String MSG_LIST = COMMON_SUFFIX+"msg/list";

    //20.清空消息
    public static final String MSG_CLEAN = COMMON_SUFFIX+"msg/clean";

    //21.附近地图接口
    public static final String STORE_NEAR = COMMON_SUFFIX+"store/near";

    //22.修改我的资料
    public static final String USERS_UPDATEUSERINFO = COMMON_SUFFIX+"users/updateUserInfo";

    //23.店铺详细信息
    public static final String STORE_DETAILS = COMMON_SUFFIX+"store/details";

    //24.技师详细信息
    public static final String STORE_DESIGNERDETAILS = COMMON_SUFFIX+"store/designerDetails";

    //25.支付方式列表
    public static final String ORDER_PAYLIST = COMMON_SUFFIX+"store/order/payList";

    //26.提交订单
    public static final String ORDER_SUBMIT= COMMON_SUFFIX+"store/order/submit";

    //27.收藏店铺
    public static final String STORE_STOREUP= COMMON_SUFFIX+"store/storeUp";

    //28.删除收藏店铺
    public static final String STORE_DELSTOREUP = COMMON_SUFFIX+"store/delStoreUp";

    //29.我的收藏店铺
    public static final String STORE_STOREUPLIST = COMMON_SUFFIX+"store/storeUpList";

    //30.未支付订单支付
    public static final String ORDER_ORDERPAY = COMMON_SUFFIX+"store/order/orderPay";

    //31.取消订单
    public static final String ORDER_ORDERCANCEL = COMMON_SUFFIX+"store/order/orderCancel";

    //32.我的订单列表
    public static final String ORDER_LIST = COMMON_SUFFIX+"store/order/list";

    //33.评论列表
    public static final String ORDER_EVALUATELIST = COMMON_SUFFIX+"store/order/evaluateList";

    //34.我的积分明细列表
    public static final String SCORE_LIST = COMMON_SUFFIX+"score/list";

    //35.积分商品列表
    public static final String GOODS_FREELIST = COMMON_SUFFIX+"goods/freeList";

    //36.积分商品详情
    public static final String GOODS_DETAIL = COMMON_SUFFIX+"goods/detail";

    //37.订单评论
    public static final String ORDER_EVALUATE = COMMON_SUFFIX+"store/order/evaluate";

    //38.店铺简易主页信息
    public static final String STORE_STOREINDEX = COMMON_SUFFIX+"store/storeIndex";

    //39.提交积分商城订单
    public static final String SCORE_ORDER_SUBMIT = COMMON_SUFFIX+"score/order/submit";

    //40.积分订单待付款支付
    public static final String SCORE_ORDER_ORDERPAY = COMMON_SUFFIX+"score/order/orderPay";

    //41.积分商城取消订单
    public static final String SCORE_ORDER_ORDERCANCEL = COMMON_SUFFIX+"score/order/orderCancel";

    //42.积分商城订单列表我的兑换记录
    public static final String SCORE_ORDER_LIST = COMMON_SUFFIX+"score/order/list";

    //43.积分商城订单详情
    public static final String SCORE_ORDER_ORDERDETAIL = COMMON_SUFFIX+"score/order/orderDetail";

    //44.获取助理列表
    public static final String STORE_ASSISTANTLIST = COMMON_SUFFIX+"store/assistantList";
    //45 我的首页(会员)
    public static final String VIP_USER_HOME= COMMON_SUFFIX+"users/me";
//    http://192.168.0.240:88/mall/api/users/me
}
