package cn.kuailaimei.client.api.response;

import java.util.List;

import cn.kuailaimei.client.api.entity.Evaluation;
import cn.kuailaimei.client.api.entity.EvaluationsHead;
import cn.kuailaimei.client.api.entity.Pagination;

/**
 * Created by ludaiqian on 16/8/2.
 */
public class EvaluationList {


    /**
     * allComment : 0
     * badCount : 0
     * goodCount : 0
     * perfectCount : 0
     * satisfactory : 100%
     */

    private EvaluationsHead head;
    /**
     * index : 1
     * pages : 1
     * rows : 2
     * size : 10
     */

    private Pagination pagination;
    /**
     * content : 22222
     * date : 2016-06-19 17:53:28
     * id : 2
     * uid : 11903
     * uname : 大大
     * portrait : http://e.hiphotos.3e95eed50352ac75cb7ae.jpg
     */

    private List<Evaluation> items;

    public EvaluationsHead getHead() {
        return head;
    }

    public void setHead(EvaluationsHead head) {
        this.head = head;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Evaluation> getItems() {
        return items;
    }

    public void setItems(List<Evaluation> items) {
        this.items = items;
    }
}
