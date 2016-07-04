package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.entity.CreditOrderDetail;
import cn.kuailaimei.client.api.entity.CreditOrderDetailInfo;
import cn.kuailaimei.client.api.entity.GoodsItem;

/**
 * Created by lu on 2016/7/2.
 */
public class CreditOrderDetailResponse {
    /**
     * address : 广东深圳市宝安区西乡永丰社区
     * contact : 刘先生
     * id : 4
     * phone : 15914087332
     */

    private Address address;
    /**
     * cover : http://img4.imgtn.bdimg.com/it/
     * gid : 3
     * id : 2658
     * name : 0首付可分期Coolpad/酷派
     * price : 0
     * quantity : 1
     * score : 500
     * spec : 机身内存:8G;手机颜色:土豪金
     */

    private GoodsItem goods;
    /**
     * expressName : 顺丰
     * logisticsStatus : 已发货
     * myScore : 10000
     * tno :
     */

    private CreditOrderDetailInfo infoBean;
    /**
     * amount : 12
     * date : 2016-06-21 01:49:20
     * fare : 12
     * id : 0
     * msg : 待付款
     * po : 20160621391259
     * price : 0
     * status : 1
     * tno : 0
     * score : 100
     * uid : 0
     */

    private CreditOrderDetail order;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GoodsItem getGoods() {
        return goods;
    }

    public void setGoods(GoodsItem goods) {
        this.goods = goods;
    }

    public CreditOrderDetailInfo getInfoBean() {
        return infoBean;
    }

    public void setInfoBean(CreditOrderDetailInfo infoBean) {
        this.infoBean = infoBean;
    }

    public CreditOrderDetail getOrder() {
        return order;
    }

    public void setOrder(CreditOrderDetail order) {
        this.order = order;
    }


}
