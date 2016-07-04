package cn.kuailaimei.client.api.entity;

/**
 * Created by lu on 2016/7/2.
 */
public class StockItem {

    /**
     * code : 10045:10046;10049:10053
     * gid : 1
     * id : 11195
     * inventory : 1000
     * name : 机身内存:8G;手机颜色;土豪金
     * price : 208
     */

    private String code;
    private int gid;
    private int id;
    private int inventory;
    private String name;
    private int price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
