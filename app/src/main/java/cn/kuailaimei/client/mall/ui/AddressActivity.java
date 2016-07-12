package cn.kuailaimei.client.mall.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.AppBarActivity;

/**
 * Created by lu on 2016/7/11.
 */
public class AddressActivity extends AppBarActivity {
    private EditText receiverName;
    private EditText receiverPhone;
    private EditText receiverAddress;
    private EditText receiverAddressDetail;
    private LinearLayout setDefaultAddress;
//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        this.setDefaultAddress = (LinearLayout) findViewById(R.id.set_default_address);
        this.receiverAddress = (EditText) findViewById(R.id.receiver_address);
        this.receiverAddressDetail = (EditText) findViewById(R.id.receiver_address_detail);
        this.receiverPhone = (EditText) findViewById(R.id.receiver_phone);
        this.receiverName = (EditText) findViewById(R.id.receiver_name);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}