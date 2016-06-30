package com.zitech.framework.data.network.response;

import com.zitech.framework.data.network.entity.Basic;

/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.response
 * @Description: 响应基类
 * @date 2016/5/17 9:47
 */
public class ApiResponse<T> {

    Basic basic;
    T data;

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
