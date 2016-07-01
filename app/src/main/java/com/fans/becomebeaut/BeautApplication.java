package com.fans.becomebeaut;

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
        user = new User();
    }

    public static BeautApplication getInstance() {
        return (BeautApplication) BaseApplication.getInstance();
    }

    public User getUser(){
        return user;
    }
}
