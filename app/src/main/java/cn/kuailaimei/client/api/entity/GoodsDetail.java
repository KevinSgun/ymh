package cn.kuailaimei.client.api.entity;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class GoodsDetail {

    /**
     * content : c
     * fare : 12
     * id : 1
     * inventory : 100
     * name : 0首付可分期Coolpad/酷派 8690-T00大神X7移动4G土豪金送手机套膜
     * oldprice : 100
     * photos : ["http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg"]
     * price : 0
     * sales : 0
     * score : 500
     * subtitle : 仅支持平台邮寄兑换
     * tooltip : 1.本商品只支持平台邮寄兑换(所需邮费12元)2.不支持商家现场兑换,每个用户每次只能兑换一个.
     */

    private String content;
    private int fare;
    private int id;
    private int inventory;
    private String name;
    private int oldprice;
    private int price;
    private int sales;
    private int score;
    private String subtitle;
    private String tooltip;
    private List<String> photos;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOldprice() {
        return oldprice;
    }

    public void setOldprice(int oldprice) {
        this.oldprice = oldprice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
