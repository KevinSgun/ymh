package com.fans.becomebeaut.login.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.request.ResetPsdRequest;
import com.fans.becomebeaut.api.request.VerifyCodeRequest;
import com.fans.becomebeaut.common.ui.ValidateActivity;
import com.fans.becomebeaut.utils.StringUtils;
import com.fans.becomebeaut.utils.ToastMaster;
import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class ForgetPassWordActivity extends ValidateActivity {
    private EditText inputphoneet;
    private EditText inputverifyet;
    private Button commitbtn;
    private EditText setnewpsdet;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_psd;
    }

    @Override
    protected void initView() {
        setTitle("忘记密码");

        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputverifyet = (EditText) findViewById(R.id.input_verify_et);
        commitbtn = (Button) findViewById(R.id.commit_btn);
        setnewpsdet = (EditText) findViewById(R.id.set_new_psd_et);

        commitbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getValidateCodeButtonId() {
        return R.id.get_verify_btn;
    }

    @Override
    protected String getPhoneNumber() {
        return inputphoneet.getText().toString();
    }

    @Override
    protected String getValidateCodeType() {
        return VerifyCodeRequest.RESET_PSD;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.commit_btn){
            if(inputStatusIsCorrect()){
                ResetPsdRequest resetPsdRequest = new ResetPsdRequest();
                resetPsdRequest.setMobile(inputphoneet.getText().toString());
                resetPsdRequest.setCode(inputverifyet.getText().toString());
                resetPsdRequest.setPassword(setnewpsdet.getText().toString());
                Request request = new Request(resetPsdRequest);
                request.sign();
                ApiFactory.requestResetPsd(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse apiResponse) {
                        Basic basic = apiResponse.getBasic();
                        if(basic.getStatus() == 1){
                            finish();
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

        if(TextUtils.isEmpty(inputverifyet.getText().toString())){
            ToastMaster.shortToast("验证码不能为空");
            return false;
        }

        if(TextUtils.isEmpty(setnewpsdet.getText().toString())){
            ToastMaster.shortToast("密码码不能为空");
            return false;
        }

        return true;
    }
}
