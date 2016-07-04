package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class HomeDataRequest implements RequestData{

    /**
     * latitude : 22.62
     * longitude : 114.07
     */

    private String latitude;
    private String longitude;

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
}
