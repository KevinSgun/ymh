package com.fans.becomebeaut.login.ui;

import android.widget.Button;
import android.widget.EditText;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.request.VerifyCodeRequest;
import com.fans.becomebeaut.common.ui.ValidateActivity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class ForgetPassWordActivity extends ValidateActivity {
    private EditText inputphoneet;
    private EditText inputverifyet;
    private Button getverifybtn;
    private EditText setnewpsdet;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_forget_psd;
    }

    @Override
    protected void initView() {
        setBarTitle("忘记密码");

        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputverifyet = (EditText) findViewById(R.id.input_verify_et);
        getverifybtn = (Button) findViewById(R.id.get_verify_btn);
        setnewpsdet = (EditText) findViewById(R.id.set_new_psd_et);
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
}
