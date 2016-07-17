package cn.kuailaimei.client.api.response;

import java.util.List;

import cn.kuailaimei.client.api.entity.ExchangeHistoryItem;
import cn.kuailaimei.client.api.entity.Pagination;

/**
 * Created by lu on 2016/7/17.
 */
public class ExchangeHistoryRespnse {
    /**
     * index : 1
     * pages : 1
     * rows : 2
     * size : 10
     */

    private Pagination pagination;
    /**
     * cover : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * date : 2016-06-21 01:49:23
     * msg : 待付款
     * name : 0首付可分期Coolpad/酷派 8690-T00大神X7移动4G土豪金送手机套膜
     * po : 20160621221258
     * score : 500
     * price : 0
     * status : 1
     */

    private List<ExchangeHistoryItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ExchangeHistoryItem> getItems() {
        return items;
    }

    public void setItems(List<ExchangeHistoryItem> items) {
        this.items = items;
    }


}
