package com.fans.becomebeaut.login.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class ModifyPassWordActivity extends AppBarActivity implements View.OnClickListener {
    private EditText inputnewpsdet;
    private EditText inputnewpsdagainet;
    private Button resetpsdbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_modify_psd;
    }

    @Override
    protected void initView() {
        setTitle("修改密码");

        inputnewpsdet = (EditText) findViewById(R.id.input_new_psd_et);
        inputnewpsdagainet = (EditText) findViewById(R.id.input_new_psd_again_et);
        resetpsdbtn = (Button) findViewById(R.id.reset_psd_btn);

        resetpsdbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.reset_psd_btn){

        }
    }
}
