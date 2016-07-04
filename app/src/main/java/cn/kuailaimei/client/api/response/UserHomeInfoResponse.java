package cn.kuailaimei.client.api.response;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class UserHomeInfoResponse {

    /**
     * completed : 2
     * id : 11903
     * integral : 100000
     * name : 哈哈
     * noPay : 0
     * portrait : http://211.149.237.131:8080/klm/manage/resources/upload/store/1466512370021.jpg
     * waitComment : 2
     */

    private int completed;
    private int id;
    private int integral;
    private int waitComment;
    private int noPay;
    private String portrait;
    private String name;


    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoPay() {
        return noPay;
    }

    public void setNoPay(int noPay) {
        this.noPay = noPay;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getWaitComment() {
        return waitComment;
    }

    public void setWaitComment(int waitComment) {
        this.waitComment = waitComment;
    }
}
