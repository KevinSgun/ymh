package cn.kuailaimei.client.api.response;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class PayInfoResponse {

    /**
     * integral : 80 支付成功可以得到积分
     * orderId : 20160619340225 平台訂單號
     */

    private OrderBean order;
    /**
     * payInfo : partner="2088021343718597"&seller_id="zmbapp@163.com"&out_trade_no="20160619340225"&subject="单剪"&body="单剪"&total_fee="0.01"&notify_url="http://www.zhimaapp.com:8080/dateExp/result_zfb.do"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&return_url="m.alipay.com"&sign="gcMrD%2F8rAe%2BG6%2F0lIJFdj2a%2BNZB3EMEQQqEhFKLChJ84C856cqlipFP%2FXicFrBJvA%2FdXjOZ5PVLsBYrVLBghjGS%2Bb96UdVus%2BnbtjypNrS8O1vdcHAHkc3%2FYwFahzlvmJVD2%2Bpr1Gg7UKvdzYDJoqa9nnWX5HAbybvD7d10H4mo%3D"&sign_type="RSA"
     */

    private PayInfoBean payInfo;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public PayInfoBean getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfoBean payInfo) {
        this.payInfo = payInfo;
    }

    public static class OrderBean {
        private int integral;
        private String orderId;

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class PayInfoBean {
        private String payInfo;

        public String getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(String payInfo) {
            this.payInfo = payInfo;
        }
    }
}
