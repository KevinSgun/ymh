package cn.kuailaimei.client.home.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ta.utdid2.android.utils.StringUtils;
import com.zitech.framework.Session;
import com.zitech.framework.utils.LogUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.kuailaimei.client.BeautApplication;
import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.SP;
import cn.kuailaimei.client.common.ui.BaseActivity;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.utils.ToastMaster;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private long exitTime;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "cn.kuailaimei.store.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.main_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        jPushInit();
        registerMessageReceiver();
        int position = getIntent().getIntExtra(Constants.ActivityExtra.CHECK_POSITION, 0);
        if (position == 0) {
            showFragment(HomeFragment.class);
        } else if (position == 2) {
            // showFragment(RecentMessagesFragment.class);
//            radioGroup.check(R.id.main_menu_chat);
        } else {
//            showFragment(MyHomeFragment.class);
        }

    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void jPushInit(){
        JPushInterface.init(getApplicationContext());
        if(!SP.getDefaultSP().getBoolean(Constants.IS_BINDING_JPUSH_ALIAS,false))
            bindJGPushAliasToBackground();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                LogUtils.i("-----JPush------"+showMsg.toString());
                ToastMaster.longToast(showMsg.toString());
            }
        }
    }


    /**
     * 绑定别名到激光推送后台
     */
    public void bindJGPushAliasToBackground() {
        String alias = Session.getInstance().getAndroidId();
        //使用registerid的话看MyJPushReceiver这个类，这个可以不绑定
        JPushInterface.setAliasAndTags(BeautApplication.getInstance().getApplicationContext(), alias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int responseCode, String s, Set<String> set) {
                if(responseCode == 0){
                    LogUtils.d("bindJGPushSericeToBackgroud,绑定别名至激光推送后台成功！");
                    SP.getDefaultSP().putBoolean(Constants.IS_BINDING_JPUSH_ALIAS,true);
                }else {
                    LogUtils.d("bindJGPushSericeToBackgroud,绑定别名至激光推送后台失败！responseCode : " + responseCode + "");
                }
            }
        });
    }

//    private View newMessageIconChat;

//    private View newMessageIconNotify;

    private static final int CHECK_UPDATE = 0x10;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CHECK_UPDATE:
                    checkUpdate();
                    break;
            }
            return false;
        }
    });

    public Fragment showFragment(Class<? extends Fragment> fragmentClass) {
        return BaseFragment.replace(getSupportFragmentManager(), R.id.content_frame, fragmentClass);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
                ToastMaster.shortToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void checkUpdate() {
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.main_menu_home:
                showFragment(HomeFragment.class);
                break;
            case R.id.main_menu_profile:
                showFragment(ProfileFragment.class);
                break;
            case R.id.main_menu_exchange:
                showFragment(ExchangeFragment.class);
                break;
            case R.id.main_menu_nearby:
                showFragment(NearbyFragment.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


}

