package com.zitech.framework.data.network;


import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.exception.ApiException;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<ApiResponse<T>, ApiResponse> {

    @Override
    public ApiResponse call(ApiResponse<T> httpResult) {
        if(httpResult instanceof FileUploadResponse){
            FileUploadResponse response = (FileUploadResponse) httpResult;
            if (response.getRetCode()!= 0) {
                throw new ApiException(response.getRetCode(),response.getMsg());
            }
        }else{
            Basic basic = httpResult.getBasic();
            int status = -99;
            String msg = "";
            if(basic !=null){
                status = basic.getStatus();
                msg = basic.getMsg();
            }
            if (basic == null || status != 1) {
                throw new ApiException(status,msg);
            }
        }
        return httpResult;
    }

}