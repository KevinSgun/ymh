package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.ExchangeBanner;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.api.entity.Pagination;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeListResponse {
    /**
     * index : 1
     * pages : 1
     * rows : 10
     * size : 10
     */

    private Pagination pagination;
    /**
     * id : 3
     * img : http://localhost/mall/manage/resources/upload/banner/banner_2.jpg
     * status : 0
     * url : http://www.baidu.com
     */

    private List<ExchangeBanner> banner;
    /**
     * id : 1
     * inventory : 100
     * mold : 1
     * name : tangle teezer梳子 英国按摩美发梳子
     * oldprice : 100
     * portrait : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * price : 0
     * sales : 0
     * score : 500
     * subtitle : 仅支持平台邮寄兑换
     */

    private List<ExchangeItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ExchangeBanner> getBanner() {
        return banner;
    }

    public void setBanner(List<ExchangeBanner> banner) {
        this.banner = banner;
    }

    public List<ExchangeItem> getItems() {
        return items;
    }

    public void setItems(List<ExchangeItem> items) {
        this.items = items;
    }


}
