package cn.kuailaimei.client.login.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.ModifyPsdRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.utils.ToastMaster;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class ModifyPassWordActivity extends AppBarActivity implements View.OnClickListener {
    private EditText inputnewpsdet;
    private EditText inputnewpsdagainet;
    private Button resetpsdbtn;
    private EditText inputoldpsdet;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_modify_psd;
    }

    @Override
    protected void initView() {
        setTitle("修改密码");

        inputnewpsdet = (EditText) findViewById(R.id.input_new_psd_et);
        inputnewpsdagainet = (EditText) findViewById(R.id.input_new_psd_again_et);
        inputoldpsdet = (EditText) findViewById(R.id.input_old_psd_et);
        resetpsdbtn = (Button) findViewById(R.id.reset_psd_btn);

        resetpsdbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.reset_psd_btn){
            if(ViewUtils.isFastDoubleClick()) return;
            String original = inputoldpsdet.getText().toString();
            String newpsd = inputnewpsdet.getText().toString();
            String againpsd = inputnewpsdagainet.getText().toString();

            if(TextUtils.isEmpty(original)){
                ToastMaster.shortToast("原密码不能为空");
                return;
            }

            if(TextUtils.isEmpty(newpsd)){
                ToastMaster.shortToast("新密码不能为空");
                return;
            }
            if(newpsd.length()<6){
                ToastMaster.shortToast("新密码长度不能少于6位");
                return;
            }
            if(TextUtils.isEmpty(againpsd)){
                ToastMaster.shortToast("请再次输入新密码");
                return;
            }
            if(!newpsd.equals(againpsd)){
                ToastMaster.shortToast("两次输入新密码不一致");
                return;
           }

            ModifyPsdRequest modifyPsdRequest = new ModifyPsdRequest();
            modifyPsdRequest.setOriginal(original);
            modifyPsdRequest.setPassword(newpsd);
            Request request = new Request(modifyPsdRequest);
            request.sign();
            ApiFactory.requestModifyPsd(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                @Override
                protected void onNextInActive(ApiResponse apiResponse) {
                    ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                    finish();
                }
            });

        }
    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,ModifyPassWordActivity.class);
        act.startActivity(intent);
    }
}
