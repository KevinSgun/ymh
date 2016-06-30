package com.fans.becomebeaut.api;

import com.fans.becomebeaut.api.response.GetwayResponse;
import com.fans.becomebeaut.api.service.AccountService;
import com.fans.becomebeaut.api.service.GatewayService;
import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.request.ApiRequest;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {

    private static Map<String, Object> mCache = new HashMap();


    private static GatewayService gatewayService() {
        return getService(GatewayService.class);
    }

    private static AccountService getAccountService(){
        return getService(AccountService.class);
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
     * @param token
     * @return
     */
    public static Observable<ApiResponse> getVerifyCode(String token, ApiRequest request){
        return getAccountService().getVerifyCode(token,request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


}
