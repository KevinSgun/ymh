package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/1.
 */
public class UpdateAddress implements RequestData {

    /**
     * address : 西乡永丰社区
     * cityid : 19,1607,3639
     * cityname : 广东,深圳市,宝安区
     * contact : 刘先生
     * id : 0
     * phone : 15914087332
     * status : 1
     */

    private String address;
    private String cityid;
    private String cityname;
    private String contact;
    private String id;
    private String phone;
    private String status;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
