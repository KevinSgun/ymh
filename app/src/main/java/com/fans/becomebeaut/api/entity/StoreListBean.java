package com.fans.becomebeaut.api.entity;

/**
 * Created by ymh on 2016/7/2 0002.
 */
public class StoreListBean {

    /**
     * address : 广东深圳市南山区园西工业区25栋2单元605
     * bottomPrice : 0
     * corp : 深圳皇朝国际理发中心
     * distances : 504
     * icon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * id : 10000
     * latitude : 22.61667
     * longitude : 114.06667
     * name : 皇朝国际
     * status : 1
     */

    private String address;
    private String bottomPrice;
    private String corp;
    private String distances;
    private String icon;
    private int id;
    private String latitude;
    private String longitude;
    private String name;
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
