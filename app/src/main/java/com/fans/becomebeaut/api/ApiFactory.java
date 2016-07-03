package com.fans.becomebeaut.api;

import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.FilePathResponse;
import com.fans.becomebeaut.api.response.FileUploadResponse;
import com.fans.becomebeaut.api.response.GetwayResponse;
import com.fans.becomebeaut.api.response.HomePageResponse;
import com.fans.becomebeaut.api.response.NearStoreListResposne;
import com.fans.becomebeaut.api.response.UserInfoResponse;
import com.fans.becomebeaut.api.service.AccountService;
import com.fans.becomebeaut.api.service.GatewayService;
import com.fans.becomebeaut.api.service.StoreService;
import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
     * 文件上传
     *
     * @param file
     * @return
     */
    public static Observable<FileUploadResponse<FilePathResponse>> upload(File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                //添加文件参数
                .addFormDataPart("files", file.getName(), fileBody)
                //添加Form参数
                .addFormDataPart("id", "type")//
                .addFormDataPart("name", "type")
                .addFormDataPart("value", "1")
                .build();
        return getStoreService().upload(multipartBody).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
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
}
