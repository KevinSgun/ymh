package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.request.RequestData;

/**
 * Created by lu on 2016/7/2.
 */
public class StoreDetailRequest implements RequestData{

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
