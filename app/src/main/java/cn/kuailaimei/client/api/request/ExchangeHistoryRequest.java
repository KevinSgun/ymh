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

    private int index=1;
    private int size=10;
    private String status="0";

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
