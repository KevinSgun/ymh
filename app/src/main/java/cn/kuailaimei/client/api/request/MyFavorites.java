package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class MyFavorites implements RequestData{

    private int index;
    private int size;

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
