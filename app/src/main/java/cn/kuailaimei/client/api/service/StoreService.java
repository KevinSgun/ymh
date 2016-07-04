package cn.kuailaimei.client.api.service;

import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;

import java.util.List;

import cn.kuailaimei.client.api.NetConstants;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.entity.Message;
import cn.kuailaimei.client.api.request.Feedback;
import cn.kuailaimei.client.api.request.HomeDataRequest;
import cn.kuailaimei.client.api.request.IDRequest;
import cn.kuailaimei.client.api.request.NearStoreRequest;
import cn.kuailaimei.client.api.request.OrderListRequest;
import cn.kuailaimei.client.api.request.PageRequestData;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SIDRequest;
import cn.kuailaimei.client.api.request.ScoreListRequest;
import cn.kuailaimei.client.api.request.ShopListRequest;
import cn.kuailaimei.client.api.request.ShopSelectionRequest;
import cn.kuailaimei.client.api.request.UpdateAddress;
import cn.kuailaimei.client.api.request.UpdateProfileRequest;
import cn.kuailaimei.client.api.response.DesignerDetail;
import cn.kuailaimei.client.api.response.FilePathResponse;
import cn.kuailaimei.client.api.response.HomePageResponse;
import cn.kuailaimei.client.api.response.MyFavoriteResponse;
import cn.kuailaimei.client.api.response.NearStoreListResposne;
import cn.kuailaimei.client.api.response.OrderListResponse;
import cn.kuailaimei.client.api.response.PayInfoResponse;
import cn.kuailaimei.client.api.response.ScoreListResponse;
import cn.kuailaimei.client.api.response.ShopDetailRequest;
import cn.kuailaimei.client.api.response.ShopDetailResponse;
import cn.kuailaimei.client.api.response.ShopListResponse;
import cn.kuailaimei.client.api.response.UserHomeInfoResponse;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
     * @see {@link HomeDataRequest}
     */
    @POST(NetConstants.HOME_LOAD)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<HomePageResponse>> getHomeData(@Body Request body);

    /**
     * 我要美容美发,获取店铺等
     *
     * @param body
     * @return
     * @see ShopListRequest
     */
    @POST(NetConstants.HOME_SALON)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ShopListResponse>> getHomeSalonShops(@Body Request body);


    /**
     * 添加或修改收货地址
     *
     * @param body
     * @return
     * @see UpdateAddress
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
     * @see {@link Feedback }
     */
    @POST(NetConstants.FEEDBACK)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> feedback(@Body Request body);


    /**
     * 我要美容美发店铺筛选
     *
     * @param body
     * @return
     * @see ShopSelectionRequest
     */
    @POST(NetConstants.STORE_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ShopListResponse>> getSeletedShops(@Body Request body);

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
     * 修改我的资料
     *
     * @param body
     * @return
     * @see UpdateProfileRequest
     */
    @POST(NetConstants.USERS_UPDATEUSERINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> updateProfile(@Body Request body);


    /**
     * 店铺详细信息
     *
     * @param body
     * @return
     * @see ShopDetailRequest
     */
    @POST(NetConstants.STORE_DETAILS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ShopDetailResponse>> getShopDetail(@Body Request body);


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
     * 我的首页(会员)
     *
     * @param body
     * @return
     * @see Request
     */
    @POST(NetConstants.VIP_USER_HOME)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserHomeInfoResponse>> getVipUserHome(@Body Request body);

    /**
     * 积分（美券）明细列表
     *
     * @param body
     * @return
     * @see ScoreListRequest
     */
    @POST(NetConstants.SCORE_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ScoreListResponse>> getScoreList(@Body Request body);

    /**
     * 我的收藏(店铺)列表
     *
     * @param body
     * @return
     * @see PageRequestData
     */
    @POST(NetConstants.STORE_STOREUPLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<MyFavoriteResponse>> getMyFavorite(@Body Request body);

    /**
     * 我的订单列表
     *
     * @param body
     * @return
     * @see OrderListRequest
     */
    @POST(NetConstants.ORDER_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderListResponse>> getOrderList(@Body Request body);

    /**
     * 删除收藏店铺
     *
     * @param body
     * @return
     * @see SIDRequest
     */
    @POST(NetConstants.STORE_DELSTOREUP)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> deleteFavorite(@Body Request body);

    /**
     * 提交店鋪訂單信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.client.api.request.CommitOrderRequest
     */
    @POST(NetConstants.ORDER_SUBMIT)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PayInfoResponse>> commitShopOrder(@Body Request body);

//-------------------------文件上传待修改调试------------------------------

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     *
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST(NetConstants.UPLOAD)
    Observable<FileUploadResponse<FilePathResponse>> upload(@Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST(NetConstants.UPLOAD)
    Observable<FileUploadResponse<FilePathResponse>> upload(@Query("type") String type, @Body MultipartBody multipartBody);
}
