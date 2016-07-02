package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.Pagination;
import com.fans.becomebeaut.api.entity.ReviewHead;
import com.fans.becomebeaut.api.entity.ReviewItem;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class ReviewListResponse {
    /**
     * allComment : 0
     * badCount : 0
     * goodCount : 0
     * perfectCount : 0
     * satisfactory : 100%
     */

    private ReviewHead head;
    /**
     * index : 1
     * pages : 1
     * rows : 2
     * size : 10
     */

    private Pagination pagination;
    /**
     * content : 3333
     * date : 2016-06-19 17:52:18
     * id : 1
     * uid : 11903
     * uname : 大大
     * portrait : http://e.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg
     */

    private List<ReviewItem> items;

    public ReviewHead getHead() {
        return head;
    }

    public void setHead(ReviewHead head) {
        this.head = head;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ReviewItem> getItems() {
        return items;
    }

    public void setItems(List<ReviewItem> items) {
        this.items = items;
    }


}
