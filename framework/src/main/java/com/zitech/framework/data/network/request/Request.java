package com.zitech.framework.data.network.request;

import com.zitech.framework.data.network.entity.Global;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class Request extends ApiRequest{
    private Global global;
    private RequestData data;
    private transient String method;

    public Request(RequestData data) {
        this.data = data;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public RequestData getData() {
        return data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }

    @Override
    String getMethod() {
        return method;
    }
}
