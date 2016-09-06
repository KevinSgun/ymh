package cn.kuailaimei.client.login.ui;

import android.os.Handler;
import android.os.Message;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.BaseActivity;
import cn.kuailaimei.client.home.ui.MainActivity;

/**
 * Created by ymh on 2016/7/15 0015.
 */
public class SplashActivity extends BaseActivity {
    private static final int GO_MAIN = 0x11;
    private static final int GO_LOGIN = 0x12;
    private static final long DELAY_TIME = 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    launchMainActivity();
                    break;
                case GO_LOGIN:
//                    launchLoginActivity();
                    break;
            }
            return false;
        }
    });

    private void launchMainActivity() {
        skipActivity(MainActivity.class);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessageDelayed(GO_MAIN, DELAY_TIME);
    }
}
