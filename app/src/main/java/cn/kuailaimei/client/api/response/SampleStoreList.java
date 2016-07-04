package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.Pagination;
import cn.kuailaimei.client.api.entity.Shop;
import cn.kuailaimei.client.api.request.RequestData;

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
    private List<Shop> storeList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
