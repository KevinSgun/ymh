package cn.kuailaimei.client.api.entity;

/**
 * "expressName": "顺丰",//物流名字
 * "logisticsStatus": "已发货",//物流状态
 * "myScore": 10000,//我的当前的积分
 * "tno": "",//物流单号
 * "phone": "1234567"//客服电话
 */
public class CreditOrderDetailInfo {
    private String expressName;
    private String logisticsStatus;
    private int myScore;
    private String tno;
    private String phone;
    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
