package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeListRequest implements RequestData{
    /**
     * goodsType : 1
     * index : 1
     * size : 10
     */

    private String goodsType;
    private int index;
    private int size;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
