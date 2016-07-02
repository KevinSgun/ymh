package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class UploadCreditRequest implements RequestData{


    /**
     * addressId : 1
     * amount : 12
     * gId : 1
     * name : 商品名字
     * payId : 1
     * goodPrice : 0
     * score : 100
     * stockId : 11195
     */

    private String addressId;
    private String amount;
    private String gId;
    private String name;
    private String payId;
    private String goodPrice;
    private String score;
    private String stockId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGId() {
        return gId;
    }

    public void setGId(String gId) {
        this.gId = gId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
