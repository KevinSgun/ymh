package com.fans.becomebeaut.api.entity;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class NearStore {
    private int id;
    private int status;
    private String address;
    private String corp;
    private String distances;
    private String icon;
    private String name;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
