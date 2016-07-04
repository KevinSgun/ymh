package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/1.
 */
public class ShopListRequest implements RequestData {

    /**
     * cId : 10001
     * index : 1
     * latitude : 22.62
     * longitude : 114.07
     * size : 10
     */

    private String cId;
    private int index=1;
    private String latitude;
    private String longitude;
    private int size=10;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
