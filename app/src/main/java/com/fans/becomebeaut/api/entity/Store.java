package com.fans.becomebeaut.api.entity;

/**
 * Created by lu on 2016/7/1.
 */
public class Store {
    private String address;//: "广东深圳市南山区园西工业区25栋2单元605",
    private String corp;//": "深圳皇朝国际理发中心",
    private String distances;//": "504",//距离以米为单位
    private String icon;//": "http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg",
    private String id;//": 10000,
    private String name;//": "",//店铺简称
    private String status;//": 1,
    private String bottomPrice;//":"12"//多少钱起

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getDistances() {
        return distances;
    }

    public void setDistances(String distances) {
        this.distances = distances;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }
}
