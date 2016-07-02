package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class OrderListRequest implements RequestData {


    /**
     * index : 1
     * status : 1
     * size : 10
     */

    private String index;
    private String status;
    private String size;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
