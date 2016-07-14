package cn.kuailaimei.client.api;

import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.entity.Employee;
import cn.kuailaimei.client.api.request.ModifyPsdRequest;
import cn.kuailaimei.client.api.request.OrderListRequest;
import cn.kuailaimei.client.api.request.PageRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SIDRequest;
import cn.kuailaimei.client.api.response.AssistantListResposne;
import cn.kuailaimei.client.api.response.DesignerDetail;
import cn.kuailaimei.client.api.response.ExchangeDetailResponse;
import cn.kuailaimei.client.api.response.ExchangeListResponse;
import cn.kuailaimei.client.api.response.FilePathResponse;
import cn.kuailaimei.client.api.response.GetwayResponse;
import cn.kuailaimei.client.api.response.HomePageResponse;
import cn.kuailaimei.client.api.response.MyFavoriteResponse;
import cn.kuailaimei.client.api.response.NearStoreListResposne;
import cn.kuailaimei.client.api.response.OrderListResponse;
import cn.kuailaimei.client.api.response.OrderPayListResponse;
import cn.kuailaimei.client.api.response.OrderPayResult;
import cn.kuailaimei.client.api.response.ScoreListResponse;
import cn.kuailaimei.client.api.response.ShopDetailResponse;
import cn.kuailaimei.client.api.response.ShopListResponse;
import cn.kuailaimei.client.api.response.UserHomeInfoResponse;
import cn.kuailaimei.client.api.response.UserInfoResponse;
import cn.kuailaimei.client.api.service.AccountService;
import cn.kuailaimei.client.api.service.GatewayService;
import cn.kuailaimei.client.api.service.StoreService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {

    private static Map<String, Object> mCache = new HashMap();


    private static GatewayService gatewayService() {
        return getService(GatewayService.class);
    }

    private static AccountService getAccountService() {
        return getService(AccountService.class);
    }

    private static StoreService getStoreService() {
        return getService(StoreService.class);
    }

    public static <T> T getService(Class cls) {
        String key = cls.getSimpleName();
        Object target = mCache.get(key);
        if (target == null) {
            target = RetrofitClient.getInstance().create(cls);
            mCache.put(key, target);
        }
        return (T) target;
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public static Observable<GetwayResponse> gatewayLogin(String username, String password) {
        Map<String, String> args = new HashMap<>();
        args.put("grant_type", "password");
        args.put("client_id", "3");
        args.put("client_secret", "de15b55a94cd4da1bc060207273a8c8c");
        args.put("type", "0");
        args.put("username", username);
        args.put("password", password);
        return gatewayLogin(args);//通过compose()操作符复用 subscribeOn() 和 observeOn() 的逻辑
    }

    public static Observable<GetwayResponse> gatewayLogin(Map args) {
        return gatewayService().gatewayLogin(args).compose(SchedulersCompat.<GetwayResponse>applyExecutorSchedulers());
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse> getVerifyCode(Request request) {
        return getAccountService().getVerifyCode(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<UserInfoResponse>> requestRegister(Request request) {
        return getAccountService().requestRegister(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<UserInfoResponse>> requestLogin(Request request) {
        return getAccountService().requestLogin(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse> requestResetPsd(Request request) {
        return getAccountService().requestResetPsd(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取首页数据
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<HomePageResponse>> getHomeData(Request request) {
        return getStoreService().getHomeData(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取附近店铺数据
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<NearStoreListResposne>> getNearest(Request request) {
        return getStoreService().getNearest(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 修改我的资料
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse> updateProfile(Request request) {
        return getStoreService().updateProfile(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我要美容美发,获取店铺等
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ShopListResponse>> getHomeSalonShops(Request request) {
        return getStoreService().getHomeSalonShops(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我要美容美发店铺筛选
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ShopListResponse>> getSeletedShops(Request request) {
        return getStoreService().getSeletedShops(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }
    /**
     * 获取收货地址
     *
     * @param request
     * @return
     */

    public static Observable<ApiResponse<List<Address>>> getAddressList(Request request) {
        return getStoreService().getAddressList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public static Observable<FileUploadResponse<FilePathResponse>> upload(String type, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                //添加文件参数
                .addFormDataPart("files", file.getName(), fileBody)
                //添加Form参数
                .addFormDataPart("id", "type")//
                .addFormDataPart("name", "type")
                .addFormDataPart("value", "1")
                .build();
        return getStoreService().upload(type, multipartBody).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 文件上传
     *
     * @param parts
     * @return
     */
    public static Observable<FileUploadResponse<FilePathResponse>> upload(List<MultipartBody.Part> parts) {
        return getStoreService().upload(parts).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 用户首页（VIP）数据
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<UserHomeInfoResponse>> getVipUserHome(Request request) {
        return getStoreService().getVipUserHome(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 意见反馈
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse> feedback(Request request) {
        return getStoreService().feedback(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 积分（美券明细列表）
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ScoreListResponse>> getScoreList(Request request) {
        return getStoreService().getScoreList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的收藏列表
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<MyFavoriteResponse>> getMyFavorite(Request request) {
        return getStoreService().getMyFavorite(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 店铺详细信息
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ShopDetailResponse>> getShopDetail(Request request) {
        return getStoreService().getShopDetail(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的订单列表
     *
     * @param request
     * @return
     * @see OrderListRequest
     */
    public static Observable<ApiResponse<OrderListResponse>> getOrderList(PageRequest request) {
        return getStoreService().getOrderList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     * @see ModifyPsdRequest
     */
    public static Observable<ApiResponse> requestModifyPsd(Request request) {
        return getAccountService().requestModifyPsd(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 删除收藏店铺
     *
     * @param request
     * @return
     * @see SIDRequest
     */
    public static Observable<ApiResponse> deleteFavorite(Request request) {
        return getStoreService().deleteFavorite(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 提交店铺订单
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.CommitOrderRequest
     */
    public static Observable<ApiResponse<OrderPayResult>> commitShopOrder(Request request) {
        return getStoreService().commitShopOrder(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取支付类型列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.Request
     */
    public static Observable<ApiResponse<OrderPayListResponse>> getOrderPayList(Request request) {
        return getStoreService().getOrderPayList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 取消订单
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.OrderIDRequest
     */
    public static Observable<ApiResponse> cancelOrder(Request request) {
        return getStoreService().cancelOrder(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 未支付订单支付
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.PayRequest
     */
    public static Observable<ApiResponse<OrderPayResult>> doOrderRePay(Request request) {
        return getStoreService().doOrderRePay(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 发表评论
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.OrderCommentRequest
     */
    public static Observable<ApiResponse> orderComment(Request request) {
        return getStoreService().orderComment(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师详细信息
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<DesignerDetail>> getDesignerDetail(Request request) {
        return getStoreService().getDesignerDetail(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     *  35 积分商品列表
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ExchangeListResponse>> getExchangList(Request request) {
        return getStoreService().getExchangList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 36.积分商品详情
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse<ExchangeDetailResponse>> getExchangeDetail(Request request) {
        return getStoreService().getExchangeDetail(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }
    /**
     *44 获取助理列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.client.api.request.SIDRequest
     */
    public static  Observable<ApiResponse<AssistantListResposne>> getAssistantList(@Body Request request){
        return getStoreService().getAssistantList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

}
