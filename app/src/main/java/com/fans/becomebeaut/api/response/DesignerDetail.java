package com.fans.becomebeaut.api.response;


import com.fans.becomebeaut.api.entity.Designer;
import com.fans.becomebeaut.api.entity.DesignerService;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class DesignerDetail {

    /**
     * agent : 10000
     * alias : １号设计师
     * commentCount : 5
     * id : 11902
     * orderCount : 0
     * orderRate : 100%
     * portrait : http://e.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg
     * position : 设计总监
     * reserveCount : 0
     * satisfactory : 40%
     * signature :
     * sumScore : 2
     */

    private Designer designer;
    /**
     * isGroup : 0
     * content : 有一个ie好的服务流程
     * name : 总监单剪
     * price : 50.00
     * cid : 10003
     * mid : 11902
     * sid : 10000
     */

    private List<DesignerService> serviceList;

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public List<DesignerService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<DesignerService> serviceList) {
        this.serviceList = serviceList;
    }




}
