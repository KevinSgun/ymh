package cn.kuailaimei.client.common.event;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class EventFactory {

    /**
     * 用户信息改变
     */
    public static class UserDataChange{

    }

    /**
     * 地图定位信息
     */
    public static class MapLocationInfo{
        public double latitude;//纬度
        public double longitude;//经度
    }

    /**
     * 订单数量信息改变
     */
    public static class OrderCountDataChange{

    }

    /**
     * 订单列表信息改变
     */
    public static class OrderListDataChange{
        public String status = "-1";
    }
}
