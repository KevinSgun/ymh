package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.CreditOrderItem;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class CreditOrderListResponse {
    private List<CreditOrderItem> items;

    public List<CreditOrderItem> getItems() {
        return items;
    }

    public void setItems(List<CreditOrderItem> items) {
        this.items = items;
    }
}
