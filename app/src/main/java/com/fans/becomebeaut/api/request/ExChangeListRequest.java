package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeListRequest {
    /**
     * goodsType : 1
     * index : 1
     * size : 10
     */

    private String goodsType;
    private String index;
    private String size;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

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
}
