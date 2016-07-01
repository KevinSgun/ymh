package com.fans.becomebeaut.login.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.request.LoginRequest;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.UserInfoResponse;
import com.fans.becomebeaut.common.User;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.home.ui.MainActivity;
import com.fans.becomebeaut.utils.StringUtils;
import com.fans.becomebeaut.utils.ToastMaster;
import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class LoginActivity extends AppBarActivity implements View.OnClickListener {
    private EditText inputphoneet;
    private EditText inputpsdet;
    private TextView freeregistertv;
    private TextView forgetpsdtv;
    private Button loginbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        toolbar.setLeftVisiable(View.GONE);
        setTitle(R.string.login);
        initialize();

    }

    @Override
    protected void initData() {
        if(!TextUtils.isEmpty(User.get().getMobile()))
            inputphoneet.setText(User.get().getMobile());
    }

    private void initialize() {
        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputpsdet = (EditText) findViewById(R.id.input_psd_et);
        freeregistertv = (TextView) findViewById(R.id.free_register_tv);
        forgetpsdtv = (TextView) findViewById(R.id.forget_psd_tv);
        loginbtn = (Button) findViewById(R.id.login_btn);
        loginbtn.setOnClickListener(this);
        freeregistertv.setOnClickListener(this);
        forgetpsdtv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.free_register_tv){
            //注册
            skipActivity(RegisterActivity.class);
        }else if(view.getId() == R.id.forget_psd_tv){
            //忘记密码
            showActivity(ForgetPassWordActivity.class);
        }else if(view.getId() == R.id.login_btn){
            //登录首页
            if(inputStatusIsCorrect()){

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserName(inputphoneet.getText().toString());
                loginRequest.setPassword(inputpsdet.getText().toString());
                Request request = new Request(loginRequest);
                request.sign();
                ApiFactory.requestLogin(request).subscribe(new ProgressSubscriber<ApiResponse<UserInfoResponse>>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse<UserInfoResponse> userInfoResponseApiResponse) {
                        Basic basic = userInfoResponseApiResponse.getBasic();
                        if(basic.getStatus() == 1){
                            User.get().storeFromUserInfo(userInfoResponseApiResponse.getData());
                            skipActivity(MainActivity.class);
                        }
                        ToastMaster.shortToast(basic.getMsg());
                    }
                });
            }
        }
    }

    private boolean inputStatusIsCorrect() {
        if(TextUtils.isEmpty(inputphoneet.getText().toString())){
            ToastMaster.shortToast("手机号不能为空");
            return false;
        }

        if(inputphoneet.getText().toString().length()!=11){
            ToastMaster.shortToast("请输入11位手机号");
            return false;
        }

        if(!StringUtils.isPhoneNum(inputphoneet.getText().toString())){
            ToastMaster.shortToast("手机号格式不正确");
            return false;
        }

        if(TextUtils.isEmpty(inputpsdet.getText().toString())){
            ToastMaster.shortToast("密码不能为空");
            return false;
        }

        return true;
    }
}
