package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lu on 2016/7/2.
 */
public class Address implements Parcelable {

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


    public boolean isDefault() {
        return status == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.cityid);
        dest.writeString(this.cityname);
        dest.writeString(this.contact);
        dest.writeInt(this.id);
        dest.writeString(this.phone);
        dest.writeInt(this.status);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.address = in.readString();
        this.cityid = in.readString();
        this.cityname = in.readString();
        this.contact = in.readString();
        this.id = in.readInt();
        this.phone = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
