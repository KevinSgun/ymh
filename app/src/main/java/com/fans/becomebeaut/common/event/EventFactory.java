package com.fans.becomebeaut.common.event;

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
}
