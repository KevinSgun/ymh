package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class ReviewListRequest implements RequestData{


    /**
     * id : 10000
     * index : 1
     * sType : 1
     * size : 10
     */

    private String id;
    private String index;
    private String sType;
    private String size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
