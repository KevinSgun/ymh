package cn.kuailaimei.client.api.service;

import cn.kuailaimei.client.api.NetConstants;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.UserInfoResponse;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import cn.kuailaimei.client.api.request.LoginRequest;
import cn.kuailaimei.client.api.request.ModifyPsdRequest;
import cn.kuailaimei.client.api.request.RegisterRequest;
import cn.kuailaimei.client.api.request.ResetPsdRequest;
import cn.kuailaimei.client.api.request.VerifyCodeRequest;
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
     * @see {@link VerifyCodeRequest}
     */
    @POST(NetConstants.USERS_CODE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> getVerifyCode( @Body Request body);

    /**
     * 用户注册
     *
     * @param body
     * @return
     * @see {@link RegisterRequest}
     */
    @POST(NetConstants.USERS_REGISTER)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfoResponse>> requestRegister(@Body Request body);

    /**
     * 用户登录
     *
     * @param body
     * @return
     * @see {@link LoginRequest}
     */
    @POST(NetConstants.USERS_LOGIN)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfoResponse>> requestLogin(@Body Request body);

    /**
     * 忘记密码
     *
     * @param body
     * @return
     * @see {@link ResetPsdRequest}
     */
    @POST(NetConstants.USERS_FORGET)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> requestResetPsd(@Body Request body);

    /**
     * 修改密码
     *
     * @param body
     * @return
     * @see {@link ModifyPsdRequest}
     */
    @POST(NetConstants.USERS_UPDATEPASSWORD)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> requestModifyPsd(@Body Request body);


}
