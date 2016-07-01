package com.fans.becomebeaut.login.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.request.VerifyCodeRequest;
import com.fans.becomebeaut.common.ui.ValidateActivity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class RegisterActivity extends ValidateActivity {
    private EditText inputphoneet;
    private EditText inputverifyet;
    private EditText setpsdet;
    private TextView agreeprotocoltv;
    private TextView logindirecttv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setTitle("注册");
        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputverifyet = (EditText) findViewById(R.id.input_verify_et);
        setpsdet = (EditText) findViewById(R.id.set_psd_et);
        agreeprotocoltv = (TextView) findViewById(R.id.agree_protocol_tv);
        logindirecttv = (TextView) findViewById(R.id.login_direct_tv);

        agreeprotocoltv.setOnClickListener(this);
        logindirecttv.setOnClickListener(this);
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
        return VerifyCodeRequest.REGISTER;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
       if(view.getId() == R.id.agree_protocol_tv){

        }else if(view.getId() == R.id.login_direct_tv){

        }
    }
}
