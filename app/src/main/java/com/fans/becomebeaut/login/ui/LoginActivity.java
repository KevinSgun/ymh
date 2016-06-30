package com.fans.becomebeaut.login.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.request.VerifyCodeRequest;
import com.fans.becomebeaut.common.ui.AppBarActivity;
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

    }

    private void initialize() {
        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputpsdet = (EditText) findViewById(R.id.input_psd_et);
        freeregistertv = (TextView) findViewById(R.id.free_register_tv);
        forgetpsdtv = (TextView) findViewById(R.id.forget_psd_tv);
        loginbtn = (Button) findViewById(R.id.login_btn);
        loginbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
        verifyCodeRequest.setApkind("register");
        verifyCodeRequest.setMobile("13265680639");
        Request request = new Request(verifyCodeRequest);
        ApiFactory.getVerifyCode(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                apiResponse.getData();
            }
        });
    }
}
