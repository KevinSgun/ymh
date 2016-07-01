package com.fans.becomebeaut;

import com.baidu.mapapi.SDKInitializer;
import com.fans.becomebeaut.common.User;
import com.zitech.framework.BaseApplication;

/**
 * Created by lu on 2016/6/12.
 */
public class BeautApplication extends BaseApplication {

    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        user = new User();
    }

    public static BeautApplication getInstance() {
        return (BeautApplication) BaseApplication.getInstance();
    }

    public User getUser(){
        return user;
    }
}
