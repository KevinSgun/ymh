package cn.kuailaimei.client.api.request;

/**
 * Created by ludaiqian on 16/8/2.
 */
public class EvaluationRequest implements RequestData {

    /**
     * id : 10000
     * index : 1
     * sType : 1
     * size : 10
     */

    private String id;
    private int index;
    private String sType;
    private int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
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
