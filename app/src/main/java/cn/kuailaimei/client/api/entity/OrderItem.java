package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable{
    public static final int WAIT_PAY = 1;//待付款
    public static final int WAIT_COMMENT = 2;//待评价
    public static final int COMPLETE = 3;//已完成

    private String addDate;
    private float amount;
    private String designerName;
    private String msg;
    private String orderId;
    private String sIcon;
    private int sId;
    private String sName;
    private String serviceName;
    private int status;

    protected OrderItem(Parcel in) {
        addDate = in.readString();
        amount = in.readFloat();
        designerName = in.readString();
        msg = in.readString();
        orderId = in.readString();
        sIcon = in.readString();
        sId = in.readInt();
        sName = in.readString();
        serviceName = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addDate);
        dest.writeFloat(amount);
        dest.writeString(designerName);
        dest.writeString(msg);
        dest.writeString(orderId);
        dest.writeString(sIcon);
        dest.writeInt(sId);
        dest.writeString(sName);
        dest.writeString(serviceName);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSIcon() {
        return sIcon;
    }

    public void setSIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}