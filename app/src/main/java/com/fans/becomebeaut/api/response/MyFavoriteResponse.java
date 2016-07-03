package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.NearStore;
import com.fans.becomebeaut.api.entity.Pagination;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyFavoriteResponse {
    private Pagination pagination;
    private List<NearStore> storeList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<NearStore> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<NearStore> storeList) {
        this.storeList = storeList;
    }
}
