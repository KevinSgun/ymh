package com.fans.becomebeaut.api.service;

import com.fans.becomebeaut.api.NetConstants;
import com.fans.becomebeaut.api.entity.Address;
import com.fans.becomebeaut.api.entity.Message;
import com.fans.becomebeaut.api.request.CreditOrderListRequest;
import com.fans.becomebeaut.api.response.AssistantListResposne;
import com.fans.becomebeaut.api.response.CreditOrderDetailResponse;
import com.fans.becomebeaut.api.response.CreditOrderListResponse;
import com.fans.becomebeaut.api.response.ExchangeDetailResponse;
import com.fans.becomebeaut.api.request.OrderRequest;
import com.fans.becomebeaut.api.request.SIDRequest;
import com.fans.becomebeaut.api.request.IDRequest;
import com.fans.becomebeaut.api.request.NearStoreRequest;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.CreditResponse;
import com.fans.becomebeaut.api.response.DesignerDetail;
import com.fans.becomebeaut.api.response.ExchangeListResponse;
import com.fans.becomebeaut.api.response.HomePageResponse;
import com.fans.becomebeaut.api.response.NearStoreListResposne;
import com.fans.becomebeaut.api.response.OrderListResponse;
import com.fans.becomebeaut.api.response.OrderPayResult;
import com.fans.becomebeaut.api.response.PayListResponse;
import com.fans.becomebeaut.api.response.PayResult;
import com.fans.becomebeaut.api.response.ReviewListResponse;
import com.fans.becomebeaut.api.response.SampleStoreList;
import com.fans.becomebeaut.api.response.StoreDetailResponse;
import com.fans.becomebeaut.api.response.StoreHomeResponse;
import com.fans.becomebeaut.api.response.StoreListResponse;
import com.fans.becomebeaut.api.response.UploadCreditResponse;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by ymh on 2016/7/1 0001.
 * 首页及店铺相关
 */
public interface StoreService {
    /**
     * 获取首页数据
     *
     * @param body
     * @return
     * @see {@link com.fans.becomebeaut.api.request.HomeDataRequest}
     */
    @POST(NetConstants.HOME_LOAD)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<HomePageResponse>> getHomeData(@Body Request body);

    /**
     * 我要美容美发,获取店铺等
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.StoreListRequest
     */
    @POST(NetConstants.HOME_SALON)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreListResponse>> getHomeSalonStores(@Body Request body);


    /**
     * 添加或修改收货地址
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.UpdateAddress
     */
    @POST(NetConstants.USERS_UPDATEADDRESS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> updateAddress(@Body Request body);

    /**
     * 获取收货地址
     *
     * @param body
     * @return
     */
    @POST(NetConstants.USERS_ADDRESS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<Address>>> getAddressList(@Body Request body);

    /**
     * 获取收货地址详细信息
     *
     * @param body
     * @return
     * @see IDRequest
     */
    @POST(NetConstants.USERS_ADDRESSDETAIL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Address>> getAddressDetail(@Body Request body);

    /**
     * 删除收货地址
     *
     * @param body
     * @return
     * @see IDRequest
     */
    @POST(NetConstants.USERS_ADDRESSDELETE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> deleteAddress(@Body Request body);

    /**
     * 设置默认收货地址
     *
     * @param body
     * @return
     * @see IDRequest
     */
    @POST(NetConstants.USERS_ADDRESSSTATUS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> setDefaultAddress(@Body Request body);

    /**
     * 我要反馈
     *
     * @param body
     * @return
     * @see IDRequest
     */
    @POST(NetConstants.FEEDBACK)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> feedback(@Body Request body);


    /**
     * 我要美容美发店铺筛选
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.StoreSelectionRequest
     */
    @POST(NetConstants.STORE_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<SampleStoreList>> getSeletedStores(@Body Request body);

    /**
     * 消息接口
     *
     * @param body
     * @return
     */
    @POST(NetConstants.MSG_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<Message>>> getMessages(@Body Request body);

    /**
     * 消息接口
     *
     * @param body
     * @return
     */
    @POST(NetConstants.MSG_CLEAN)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> clearMessages(@Body Request body);

    /**
     * 附近地图接口
     *
     * @param body
     * @return
     * @see NearStoreRequest
     */
    @POST(NetConstants.STORE_NEAR)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<NearStoreListResposne>> getNearest(@Body Request body);

    /**
     * 修改我的资料
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.UpdateProfileRequest
     */
    @POST(NetConstants.USERS_UPDATEUSERINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> updateProfile(@Body Request body);


    /**
     * 店铺详细信息
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.response.StoreDetailRequest
     */
    @POST(NetConstants.STORE_DETAILS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreDetailResponse>> getStoreDetail(@Body Request body);


    /**
     * 技师详细信息
     *
     * @param body
     * @return
     * @see IDRequest
     */
    @POST(NetConstants.STORE_DESIGNERDETAILS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<DesignerDetail>> getDesignerDetail(@Body Request body);

    /**
     * 支付方式列表
     *
     * @param body
     * @return
     */
    @POST(NetConstants.ORDER_PAYLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PayListResponse>> getPayList(@Body Request body);

    /**
     * 订单支付
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.PayRequest
     */
    @POST(NetConstants.ORDER_SUBMIT)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PayResult>> pay(@Body Request body);


    /**
     * 收藏店铺
     *
     * @param body
     * @return
     * @see SIDRequest
     */
    @POST(NetConstants.STORE_STOREUP)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> addFavorite(@Body Request body);

    /**
     * 删除收藏店铺
     *
     * @param body
     * @return
     * @see SIDRequest
     */
    @POST(NetConstants.STORE_DELSTOREUP)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> removeFavorite(@Body Request body);

    /**
     * 我的收藏店铺
     *
     * @param body
     * @return
     * @see SIDRequest
     */
    @POST(NetConstants.STORE_STOREUPLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<SampleStoreList>> myFavoriteStores(@Body Request body);

    /**
     * 提交店铺订单信息
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.SubmitStoreRequest
     */
    @POST(NetConstants.ORDER_SUBMIT)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderPayResult>> submitStoreOrder(@Body Request body);

    /**
     * 未支付订单支付
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.ORDER_ORDERPAY)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderPayResult>> payUnpaiedOrder(@Body Request body);


    /**
     * 取消订单
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.ORDER_ORDERCANCEL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> cancleOrder(@Body Request body);

    /**
     * 我的订单列表
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.OrderListRequest
     */
    @POST(NetConstants.ORDER_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderListResponse>> getOrderList(@Body Request body);

    /**
     * 评论列表
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.ReviewListRequest
     */
    @POST(NetConstants.ORDER_EVALUATELIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ReviewListResponse>> getReviewList(@Body Request body);

    /**
     * 我的积分明细列表
     *
     * @param body
     * @return
     * @see CreditOrderListRequest
     */
    @POST(NetConstants.SCORE_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<CreditResponse>> getCreditList(@Body Request body);

    /**
     * 积分商品列表
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.ExchangeListRequest
     */
    @POST(NetConstants.GOODS_FREELIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ExchangeListResponse>> getExchangeList(@Body Request body);

    /**
     * 积分商品详情
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.IDRequest
     */
    @POST(NetConstants.GOODS_DETAIL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ExchangeDetailResponse>> getExchangeDetail(@Body Request body);

    /**
     * 订单评论
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.GoodsReveiwRequest
     */
    @POST(NetConstants.ORDER_EVALUATE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> reveiw(@Body Request body);

    /**
     * 店铺简易主页信息
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.IDRequest
     */
    @POST(NetConstants.STORE_STOREINDEX)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreHomeResponse>> getStoreHome(@Body Request body);

    /**
     * 提交积分商城订单
     *
     * @param body
     * @return
     * @see com.fans.becomebeaut.api.request.UploadCreditRequest
     */
    @POST(NetConstants.SCORE_ORDER_SUBMIT)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UploadCreditResponse>> uploadCredit(@Body Request body);


    /**
     * 积分订单待付款支付
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.SCORE_ORDER_ORDERPAY)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderPayResult>> payCreditOrder(@Body Request body);

    /**
     * 积分商城取消订单
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.SCORE_ORDER_ORDERCANCEL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> cancleCreditOrder(@Body Request body);

    /**
     * 积分商城订单列表我的兑换记录
     *
     * @param body
     * @return
     * @see CreditOrderListRequest
     */
    @POST(NetConstants.SCORE_ORDER_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<CreditOrderListResponse> creditOrderList(@Body Request body);

    /**
     * 积分商城订单详情
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.SCORE_ORDER_ORDERDETAIL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<CreditOrderDetailResponse> creditOrderDetail(@Body Request body);

    /**
     * 获取助理列表
     *
     * @param body
     * @return
     * @see OrderRequest
     */
    @POST(NetConstants.STORE_ASSISTANTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<AssistantListResposne> getAssistantList(@Body Request body);
//-------------------------文件上传待修改调试------------------------------

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     *
     * @param parts 每个part
     * @return 状态信息状态信息
     */
    @Multipart
    @POST(NetConstants.UPLOAD)
    Observable<ApiResponse> upload(@Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST(NetConstants.UPLOAD)
    Observable<ApiResponse> upload(@Body MultipartBody multipartBody);
}
