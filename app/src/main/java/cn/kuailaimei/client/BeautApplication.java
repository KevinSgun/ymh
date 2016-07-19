package cn.kuailaimei.client;

import com.baidu.mapapi.SDKInitializer;
import com.shizhefei.mvc.MVCHelper;
import com.zitech.framework.BaseApplication;

import cn.jpush.android.api.JPushInterface;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.widget.MyLoadViewFactory;

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

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

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
