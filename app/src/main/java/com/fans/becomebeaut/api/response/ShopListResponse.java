package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.Pagination;
import com.fans.becomebeaut.api.entity.PricesBean;
import com.fans.becomebeaut.api.entity.ServicesBean;
import com.fans.becomebeaut.api.entity.Shop;

import java.util.List;

/**
 * Created by lu on 2016/7/1.
 */
public class ShopListResponse {

    /**
     * index : 1
     * pages : 1
     * rows : 1
     * size : 10
     */

    private Pagination pagination;
    /**
     * id : 10034
     * keyword : 30
     * name : 30以内
     */

    private List<PricesBean> prices;
    /**
     * id : 10002
     * keyword :
     * name : 洗剪吹
     */

    private List<ServicesBean> services;
    private List<Shop> storeList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<PricesBean> getPrices() {
        return prices;
    }

    public void setPrices(List<PricesBean> prices) {
        this.prices = prices;
    }

    public List<ServicesBean> getServices() {
        return services;
    }

    public void setServices(List<ServicesBean> services) {
        this.services = services;
    }

    public List<Shop> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Shop> storeList) {
        this.storeList = storeList;
    }
}
