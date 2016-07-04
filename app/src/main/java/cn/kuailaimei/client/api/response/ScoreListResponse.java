package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.Pagination;
import cn.kuailaimei.client.api.entity.ScoreBean;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreListResponse {

    /**
     * integral : 100
     * items : [{"access":1,"date":"2016-06-19 21:18:08","id":1,"orderno":"333333","points":150,"remark":"消费赠送积分","uid":11903},{"access":1,"date":"2016-06-19 21:19:24","id":2,"orderno":"888888","points":50,"remark":"消费赠送积分","uid":11903},{"access":0,"date":"2016-06-19 21:20:11","id":3,"orderno":"1111111","points":50,"remark":"兑换商品扣除","uid":11903}]
     * pagination : {"index":1,"pages":1,"rows":3,"size":10}
     */

    private int integral;
    /**
     * index : 1
     * pages : 1
     * rows : 3
     * size : 10
     */

    private Pagination pagination;
    /**
     * access : 1
     * date : 2016-06-19 21:18:08
     * id : 1
     * orderno : 333333
     * points : 150
     * remark : 消费赠送积分
     * uid : 11903
     */

    private List<ScoreBean> items;

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ScoreBean> getItems() {
        return items;
    }

    public void setItems(List<ScoreBean> items) {
        this.items = items;
    }
}
