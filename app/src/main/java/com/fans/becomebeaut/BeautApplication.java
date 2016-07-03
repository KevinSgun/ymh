package com.fans.becomebeaut;

import com.baidu.mapapi.SDKInitializer;
import com.fans.becomebeaut.common.User;
import com.fans.becomebeaut.common.widget.MyLoadViewFactory;
import com.shizhefei.mvc.MVCHelper;
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
        // 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局,写法参照DeFaultLoadViewFactory
        MVCHelper.setLoadViewFractory(new MyLoadViewFactory());
    }

    public static BeautApplication getInstance() {
        return (BeautApplication) BaseApplication.getInstance();
    }

    public User getUser(){
        return user;
    }
}
