package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.ShopInfo;

/**
 * Created by lu on 2016/7/2.
 */
public class ShopHomeResponse {
    private ShopInfo storeInfo;

    public ShopInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(ShopInfo storeInfo) {
        this.storeInfo = storeInfo;
    }
}
