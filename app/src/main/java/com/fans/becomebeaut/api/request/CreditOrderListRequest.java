package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class CreditOrderListRequest {


    /**
     * index : 1
     * size : 10
     * date : 7
     */

    private String index;
    private String size;
    private String date;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
