package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lu on 2016/7/2.
 */
public class SkuItem implements Parcelable {
    /**
     * id : 10045
     * items : [{"id":10046,"items":[],"name":"8G","pid":0},{"id":10047,"items":[],"name":"16G","pid":0},{"id":10048,"items":[],"name":"32G","pid":0}]
     * name : 机身内存
     * pid : 0
     */

    private int id;
    private String name;
    private int pid;
    /**
     * id : 10046
     * items : []
     * name : 8G
     * pid : 0
     */


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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.pid);
    }

    public SkuItem() {
    }

    protected SkuItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.pid = in.readInt();
    }

    public static final Creator<SkuItem> CREATOR = new Creator<SkuItem>() {
        @Override
        public SkuItem createFromParcel(Parcel source) {
            return new SkuItem(source);
        }

        @Override
        public SkuItem[] newArray(int size) {
            return new SkuItem[size];
        }
    };
}
