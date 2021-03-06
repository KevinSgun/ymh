package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.Employee;
import cn.kuailaimei.client.api.entity.ServicesBean;
import cn.kuailaimei.client.api.entity.ShopInfo;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class ShopDetailResponse {

    /**
     * id : 10000
     * name : 皇朝国际
     * corp : 深圳皇朝国际理发中心
     * phone : 0755-88888888
     * address : 广东深圳市南山区园西工业区25栋2单元605
     * icon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * allComment : 0
     * perfectCount : 0
     * goodCount : 0
     * badCount : 0
     * isStoreUp : 0
     * latitude : 22.61667
     * longitude : 114.06667
     * start : 8:00
     * expired : 21:30
     * satisfactory : 40%
     */

    private ShopInfo storeInfo;
    /**
     * alias : １号设计师
     * bottomPrice : 19
     * commentCount : 5
     * portrait : http://e.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg
     * position : 设计总监
     * satisfactory : 40%
     * sumScore : 2
     * uid : 11902
     */

    private List<Employee> employeeList;
    private List<ServicesBean> serviceList;


    public ShopInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(ShopInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<ServicesBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServicesBean> serviceList) {
        this.serviceList = serviceList;
    }
}


