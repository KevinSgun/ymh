package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable{
    public static final int ALL = 0;//全部
    public static final int WAIT_PAY = 1;//待付款
    public static final int WAIT_CONFIRM = 2;//待确认
    public static final int WAIT_COMMENT = 3;//待评价
    public static final int COMPLETE = 4;//已完成

    private int sId;
    private float amount;
    private int status;
    private String designerName;
    private String msg;
    private String orderId;
    private String sIcon;
    private String addDate;
    private String sName;
    private String serviceName;
    private String designerId;

    protected OrderItem(Parcel in) {
        sId = in.readInt();
        amount = in.readFloat();
        status = in.readInt();
        designerName = in.readString();
        msg = in.readString();
        orderId = in.readString();
        sIcon = in.readString();
        addDate = in.readString();
        sName = in.readString();
        serviceName = in.readString();
        designerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sId);
        dest.writeFloat(amount);
        dest.writeInt(status);
        dest.writeString(designerName);
        dest.writeString(msg);
        dest.writeString(orderId);
        dest.writeString(sIcon);
        dest.writeString(addDate);
        dest.writeString(sName);
        dest.writeString(serviceName);
        dest.writeString(designerId);
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

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getsIcon() {
        return sIcon;
    }

    public void setsIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }
}