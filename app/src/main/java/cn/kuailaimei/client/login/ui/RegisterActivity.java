package cn.kuailaimei.client.login.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.RegisterRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.VerifyCodeRequest;
import cn.kuailaimei.client.api.response.UserInfoResponse;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.ui.ValidateActivity;
import cn.kuailaimei.client.common.widget.ToolBarHelper;
import cn.kuailaimei.client.home.ui.MainActivity;
import cn.kuailaimei.client.common.utils.StringUtils;
import cn.kuailaimei.client.common.utils.ToastMaster;
import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

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
        setRightText("下一步");
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

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if(position == ToolBarHelper.ITEM_RIGHT){
            if(!inputStatusIsCorrect()) return;
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setMobile(inputphoneet.getText().toString());
            registerRequest.setCode(inputverifyet.getText().toString());
            registerRequest.setPassword(setpsdet.getText().toString());
            Request request = new Request(registerRequest);
            request.sign();
            ApiFactory.requestRegister(request).subscribe(new ProgressSubscriber<ApiResponse<UserInfoResponse>>(this) {
                @Override
                protected void onNextInActive(ApiResponse<UserInfoResponse> registerResponseApiResponse) {
                    Basic basic = registerResponseApiResponse.getBasic();
                    if(basic.getStatus() == 1){
                        User.get().storeFromUserInfo(registerResponseApiResponse.getData());
                        skipActivity(MainActivity.class);
                    }

                    ToastMaster.shortToast(basic.getMsg());
                }
            });

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

        if(TextUtils.isEmpty(setpsdet.getText().toString())){
            ToastMaster.shortToast("密码码不能为空");
            return false;
        }

        return true;
    }
}
