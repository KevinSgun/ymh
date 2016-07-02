package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.OrderItem;
import com.fans.becomebeaut.api.entity.Pagination;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class OrderListResponse {

    /**
     * index : 1
     * pages : 1
     * rows : 4
     * size : 10
     */

    private Pagination pagination;
    /**
     * addDate : 2016-06-19 16:18:53
     * amount : 80
     * designerName : １号设计师
     * msg : 待付款
     * orderId : 20160619180453
     * sIcon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * sId : 10000
     * sName : 深圳皇朝国际理发中心
     * serviceName : 单剪
     * status : 1
     */

    private List<OrderItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }


}
