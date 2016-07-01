package com.fans.becomebeaut.map;

import com.baidu.location.BDLocation;

public interface LocationCallBack {

    public void onLocationSuccess(BDLocation location);

    public void onLocationFail(String result);
}