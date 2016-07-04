package cn.kuailaimei.client.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.BaseActivity;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.utils.ToastMaster;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private long exitTime;

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
//        newMessageIconChat = findViewById(R.id.unread_message_count_chat);
//        newMessageIconNotify = findViewById(R.id.unread_message_count_notify);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
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

}

