package com.zitech.framework.data.network.exception;


import com.zitech.framework.data.network.response.ApiResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultDataFunc<T> implements Func1<ApiResponse<T>, T> {

    @Override
    public T call(ApiResponse<T> httpResult) {
        if (httpResult.getBasic()==null&&httpResult.getBasic().getStatus() != 1) {
            throw new ApiException(httpResult.getBasic().getStatus(),httpResult.getBasic().getMsg());
        }
        return httpResult.getData();
    }

}