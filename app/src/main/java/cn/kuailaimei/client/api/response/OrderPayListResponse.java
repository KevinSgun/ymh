package cn.kuailaimei.client.api.response;

import java.util.List;

import cn.kuailaimei.client.api.entity.PayListBean;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class OrderPayListResponse {

    /**
     * key : 微信
     * remark : 100%积分返还(商品兑换)
     * value : 1
     */

    private List<PayListBean> payList;

    public List<PayListBean> getPayList() {
        return payList;
    }

    public void setPayList(List<PayListBean> payList) {
        this.payList = payList;
    }

}
