package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.Pagination;
import com.fans.becomebeaut.api.entity.Store;
import com.fans.becomebeaut.api.request.RequestData;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class SampleStoreList implements RequestData {


    /**
     * index : 1
     * pages : 1
     * rows : 1
     * size : 10
     */

    private Pagination pagination;
    private List<Store> storeList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
