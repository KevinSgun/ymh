package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreListRequest extends PageRequestData{

    /**
     * index : 1
     * size : 10
     * date : 7
     */
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
