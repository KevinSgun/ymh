package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.CountBean;
import com.fans.becomebeaut.api.entity.ShopListBean;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class NearStoreListResposne {
    /**
     * mfCount : 1
     * mrCount : 0
     */

    private CountBean count;
    /**
     * address : 广东深圳市南山区园西工业区25栋2单元605
     * bottomPrice : 0
     * corp : 深圳皇朝国际理发中心
     * distances : 504
     * icon : http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg
     * id : 10000
     * latitude : 22.61667
     * longitude : 114.06667
     * name : 皇朝国际
     * status : 1
     */

    private List<ShopListBean> storeList;

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public List<ShopListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<ShopListBean> storeList) {
        this.storeList = storeList;
    }


}
