package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.request.RequestData;

/**
 * Created by lu on 2016/7/2.
 */
public class ShopDetailRequest implements RequestData {

    /**
     * cId : 10000
     * id : 10000
     */

    private String cId;
    private String id;

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
