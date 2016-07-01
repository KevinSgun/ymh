package com.fans.becomebeaut.api.service;

import com.fans.becomebeaut.api.NetConstants;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.HomePageResponse;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
}
