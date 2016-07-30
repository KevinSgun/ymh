package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class Sku implements Parcelable {
    /**
     * "sku": [
     * {
     * "id": 10045,
     * "items": [
     * {
     * "id": 10046,
     * "items": [],
     * "name": "8G",
     * "pid": 0
     * },
     * {
     * "id": 10047,
     * "items": [],
     * "name": "16G",
     * "pid": 0
     * },
     * {
     * "id": 10048,
     * "items": [],
     * "name": "32G",
     * "pid": 0
     * }
     * ],
     * "name": "机身内存",
     * "pid": 0
     * },
     */

    private int id;
    private String name;
    private int pid;
    private ArrayList<SkuItem> items;

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

    public ArrayList<SkuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<SkuItem> items) {
        this.items = items;
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
        dest.writeTypedList(this.items);
    }

    public Sku() {
    }

    protected Sku(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.pid = in.readInt();
        this.items = in.createTypedArrayList(SkuItem.CREATOR);
    }

    public static final Parcelable.Creator<Sku> CREATOR = new Parcelable.Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel source) {
            return new Sku(source);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };
}
