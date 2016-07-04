package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class PageRequestData implements RequestData{
    /**
     * index : 1
     * size : 10
     */

    private int index =1;
    private int size = 10;



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
