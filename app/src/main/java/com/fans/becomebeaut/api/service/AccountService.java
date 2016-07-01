package com.fans.becomebeaut.api.service;

import com.fans.becomebeaut.api.NetConstants;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.UserInfoResponse;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.service
 * @Description:(用一句话描述该文件做什么)
 * @date 2016/5/13 15:05
 */
public interface AccountService {

    /**
     * 获取验证码
     *
     * @param body
     * @return
     */
    @POST(NetConstants.USERS_CODE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> getVerifyCode( @Body Request body);

    /**
     * 用户注册
     *
     * @param body
     * @return
     */
    @POST(NetConstants.USERS_REGISTER)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfoResponse>> requestRegister(@Body Request body);

    /**
     * 用户注册
     *
     * @param body
     * @return
     */
    @POST(NetConstants.USERS_LOGIN)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfoResponse>> requestLogin(@Body Request body);

    /**
     * 忘记密码
     *
     * @param body
     * @return
     */
    @POST(NetConstants.USERS_FORGET)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> requestResetPsd(@Body Request body);

}
