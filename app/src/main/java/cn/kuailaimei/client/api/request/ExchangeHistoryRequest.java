package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class ExchangeHistoryRequest implements RequestData {

    /**
     * index : 1
     * size : 10
     * status : 0
     */

    private String index;
    private String size;
    private String status;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
