package com.fans.becomebeaut.api.entity;

/**
 * Created by lu on 2016/7/2.
 */
public class Address {

    /**
     * address : 源兴科技大厦负一楼停车场
     * cityid : 19,1607,3639
     * cityname : 广东,深圳市,福田区
     * contact : 刘先生
     * id : 2
     * phone : 15914087331
     * status : 0
     */

    private String address;
    private String cityid;
    private String cityname;
    private String contact;
    private int id;
    private String phone;
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
