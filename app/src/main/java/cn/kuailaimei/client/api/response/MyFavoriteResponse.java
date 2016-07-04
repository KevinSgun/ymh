package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.NearShop;
import cn.kuailaimei.client.api.entity.Pagination;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyFavoriteResponse {
    private Pagination pagination;
    private List<NearShop> storeList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<NearShop> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<NearShop> storeList) {
        this.storeList = storeList;
    }
}
