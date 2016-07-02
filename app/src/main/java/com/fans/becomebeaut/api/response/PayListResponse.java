package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.PayItem;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class PayListResponse {

    /**
     * key : 微信
     * remark : 100%积分返还(商品兑换)
     * value : 1
     */

    private List<PayItem> payList;

    public List<PayItem> getPayList() {
        return payList;
    }

    public void setPayList(List<PayItem> payList) {
        this.payList = payList;
    }


}
