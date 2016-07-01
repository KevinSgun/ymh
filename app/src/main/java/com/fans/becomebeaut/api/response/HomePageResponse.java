package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.HomePageBanner;
import com.fans.becomebeaut.api.entity.StoreListBean;

import java.util.List;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class HomePageResponse {
    /**
     * msgCount : 0
     * banners : [{"id":3,"img":"http://localhost/mall/manage/resources/upload/banner/banner_2.jpg","status":0,"url":"http://www.baidu.com"}]
     * lastStore : {"icon":"http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","id":10000,"name":"皇朝国际","serviceName":"单剪","addDate":"2016-06-15 12:12:12","address":"广东深圳市南山区园西工业区25栋2单元605"}
     * serviceList : [{"id":10001,"name":"美发"},{"id":10000,"name":"美容"}]
     * storeList : [{"address":"广东深圳市南山区园西工业区25栋2单元605","corp":"深圳皇朝国际理发中心","distances":"504","icon":"http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","id":10000,"name":"","status":1},{"address":"广东,广州市,花都区华利路57号08号铺","corp":"深圳剪艺人生","distances":"18337","icon":"http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","id":10003,"name":"","status":1},{"address":"广东深圳市南山区园西工业区25栋2单元605","corp":"深圳古贝发艺分公司","distances":"19018","icon":"http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","id":10001,"name":"","status":1},{"address":"广东,广州市,花都区人民路88号","corp":"瑞士发廊安徽分公司","distances":"927358","icon":"http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg","id":10002,"name":"","status":1}]
     */

    private int msgCount;
    /**
     * icon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * id : 10000
     * name : 皇朝国际
     * serviceName : 单剪
     * addDate : 2016-06-15 12:12:12
     * address : 广东深圳市南山区园西工业区25栋2单元605
     */

    private LastStoreBean lastStore;
    /**
     * id : 3
     * img : http://localhost/mall/manage/resources/upload/banner/banner_2.jpg
     * status : 0
     * url : http://www.baidu.com
     */

    private List<HomePageBanner> banners;
    /**
     * id : 10001
     * name : 美发
     */

    private List<ServiceListBean> serviceList;
    /**
     * address : 广东深圳市南山区园西工业区25栋2单元605
     * corp : 深圳皇朝国际理发中心
     * distances : 504
     * icon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * id : 10000
     * name :
     * status : 1
     */

    private List<StoreListBean> storeList;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public LastStoreBean getLastStore() {
        return lastStore;
    }

    public void setLastStore(LastStoreBean lastStore) {
        this.lastStore = lastStore;
    }

    public List<HomePageBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<HomePageBanner> banners) {
        this.banners = banners;
    }

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
    }

    public List<StoreListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreListBean> storeList) {
        this.storeList = storeList;
    }

    public static class LastStoreBean {
        private String icon;
        private int id;
        private String name;
        private String serviceName;
        private String addDate;
        private String address;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class ServiceListBean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
