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
    private String index;
    private String latitude;
    private String longitude;
    private String bottomPrice;
    private String size;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
