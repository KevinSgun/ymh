package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class OrderListRequest extends PageRequestData {


    /**
     * index : 1
     * status : 1
     * size : 10
     */

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
