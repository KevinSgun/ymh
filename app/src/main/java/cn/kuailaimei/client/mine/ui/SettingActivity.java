package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zitech.framework.utils.IoUtil;
import com.zitech.framework.utils.Utils;
import com.zitech.framework.widget.LoadingDialog;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.login.ui.LoginActivity;
import cn.kuailaimei.client.login.ui.ModifyPassWordActivity;

/**
 * Created by
 * ymh on 2016/7/3 0003.
 */
public class SettingActivity extends AppBarActivity implements View.OnClickListener {
    private LinearLayout modifypsdlayout;
    private LinearLayout clearcachelayout;
    private Button loginoutbtn;
    private LoadingDialog loadingDialog;

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
        if(User.get().notLogin()){
            loginoutbtn.setVisibility(View.GONE);
        }else{
            loginoutbtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.modify_psd_layout:
                //修改密码
                if(User.get().notLogin()){
                    LoginActivity.launch(this,false);
                }else{
                    ModifyPassWordActivity.launch(this);
                }

                break;
            case R.id.clear_cache_layout:
               //清除缓存
                if (loadingDialog == null)
                    loadingDialog = LoadingDialog.newInstance();
                CommonDialog dialog = new CommonDialog(this,"确定要清除所有缓存吗");
                dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        //TODO 清除缓存
                        double size = Utils.sizeOfDiskCache();
                        if (size > 0) {
                            loadingDialog.show(getSupportFragmentManager(),"clearCache");
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... params) {
                                    IoUtil.clearDir(Utils.getDiskCachePath(null));
                                    return null;
                                }

                                protected void onPostExecute(Void result) {
                                    loadingDialog.dismiss();
                                    ToastMaster.shortToast("清理缓存成功");
                                }
                            }.execute((Void) null);
                        } else {
                            ToastMaster.shortToast("清理缓存成功");
                        }
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
