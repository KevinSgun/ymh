package com.fans.becomebeaut.mine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.User;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.widget.CommonDialog;
import com.fans.becomebeaut.login.ui.ModifyPassWordActivity;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class SettingActivity extends AppBarActivity implements View.OnClickListener {
    private LinearLayout modifypsdlayout;
    private LinearLayout clearcachelayout;
    private Button loginoutbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitle("系统设置");
        modifypsdlayout = (LinearLayout) findViewById(R.id.modify_psd_layout);
        clearcachelayout = (LinearLayout) findViewById(R.id.clear_cache_layout);
        loginoutbtn = (Button) findViewById(R.id.login_out_btn);

        modifypsdlayout.setOnClickListener(this);
        clearcachelayout.setOnClickListener(this);
        loginoutbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.modify_psd_layout:
                //修改密码
                ModifyPassWordActivity.launch(this);
                break;
            case R.id.clear_cache_layout:
               //清除缓存
                CommonDialog dialog = new CommonDialog(this,"确定要清除所有缓存吗");
                dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        //TODO 清除缓存
                    }
                });
                dialog.show();
                break;
            case R.id.login_out_btn:
                //退出登录
                CommonDialog commonDialog = new CommonDialog(this,"确定要退出登录吗");
                commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        User.get().clear();
                        User.get().notifyChange();
                        finish();
                    }
                });
                commonDialog.show();
                break;
        }
    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,SettingActivity.class);
        act.startActivity(intent);
    }
}
