package com.fans.becomebeaut.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.common.widget.MutilRadioGroup;
import com.fans.becomebeaut.utils.ToastMaster;

public class MainActivity extends AppBarActivity implements View.OnTouchListener,MutilRadioGroup.OnCheckedChangeListener {

    @Override
    protected void initView() {
        homeLayout = findViewById(R.id.main_menu_home_layout);
        nearbylayout = findViewById(R.id.main_menu_nearby);
        profileLayout = findViewById(R.id.main_menu_profile_layout);
        radioGroup = (MutilRadioGroup) findViewById(R.id.main_radio_group);
//        newMessageIconChat = findViewById(R.id.unread_message_count_chat);
//        newMessageIconNotify = findViewById(R.id.unread_message_count_notify);

        homeLayout.setOnTouchListener(this);
        nearbylayout.setOnTouchListener(this);
        profileLayout.setOnTouchListener(this);
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
        showUnReadMessageIcon();
    }

    private void showUnReadMessageIcon() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getContentViewId() {
        return 0;
    }
    private View homeLayout;
    private View nearbylayout;
    private View profileLayout;
    private MutilRadioGroup radioGroup;
    private long exitTime;
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
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.main_menu_home_layout:
                    radioGroup.check(R.id.main_menu_home);
                    break;
                case R.id.main_menu_nearby_layout:
                    radioGroup.check(R.id.main_menu_nearby);
                    break;
                case R.id.main_menu_exchange_layout:
                    radioGroup.check(R.id.main_menu_exchange);
                    break;
                case R.id.main_menu_profile_layout:
                    radioGroup.check(R.id.main_menu_profile);
                    break;
                default:
                    break;

            }
        }
        return true;
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
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.main_menu_home:
                showFragment(HomeFragment.class);
                break;
            case R.id.main_menu_profile:
                showFragment(ProfileFragment.class);
                break;
            case R.id.main_menu_nearby:
                showFragment(NearbyFragment.class);
                break;
            default:
                break;
        }
    }

}

