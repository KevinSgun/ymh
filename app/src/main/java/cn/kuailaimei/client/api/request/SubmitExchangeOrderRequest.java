package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/17.
 */
public class SubmitExchangeOrderRequest implements RequestData {


    /**
     * addressId : 1
     * amount : 12
     * gId : 1
     * name : 商品名字
     * goodPrice : 0
     * score : 100
     * stockId : 11195
     */

    private String addressId;
    private String amount;
    private String gId;
    private String name;
    private String goodPrice;
    private String score;
    private String stockId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getAmount() {
        return Integer.parseInt(amount);
    }

    public void setAmount(int amount) {
        this.amount = String.valueOf(amount);
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

    public int getGoodPrice() {
        return Integer.parseInt(goodPrice);
    }

    public void setGoodPrice(int goodPrice) {
        this.goodPrice = String.valueOf(goodPrice);
    }

    public int getScore() {
        return Integer.parseInt(score);
    }

    public void setScore(int score) {
        this.score = String.valueOf(score);
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
