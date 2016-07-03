package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class StoreSelectionRequest implements RequestData {

    /**
     * cId : 10001
     * index : 1
     * latitude : 22.62
     * longitude : 114.07
     * bottomPrice : 30
     * size : 10
     */

    private String cId;
    private int index;
    private String latitude;
    private String longitude;
    private String bottomPrice;
    private int size;

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

    public String getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(String bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
