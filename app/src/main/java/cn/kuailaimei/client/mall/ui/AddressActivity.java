package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.UpdateAddress;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.common.widget.ToolBarHelper;

/**
 * Created by lu on 2016/7/16.
 */
public class AddressActivity extends AppBarActivity {

    private static final int ADD_ADDRESS = 1;
    private static final int EDIT_ADDRESS = 2;
    private EditText receiverName;
    private EditText receiverPhone;
    private RippleButton receiverAddress;
    private EditText receiverAddressDetail;
    private RippleLinearLayout setDefaultAddress;
    private RadioButton setDefaultRadio;
    private boolean isDefault;
    private Address address;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            if (TextUtils.isEmpty(receiverName.getText())) {
                ToastMaster.popToast(getContext(), "请输入收货人姓名");
                return;
            } else if (TextUtils.isEmpty(receiverPhone.getText())) {
                ToastMaster.popToast(getContext(), "请输入收货人电话");
                return;
            } else if (TextUtils.isEmpty(receiverAddress.getText())) {
                ToastMaster.popToast(getContext(), "请选择所在区域");
                return;
            } else if (TextUtils.isEmpty(receiverAddressDetail.getText())) {
                ToastMaster.popToast(getContext(), "请输入详细地址");
                return;
            }
            UpdateAddress updateAddress = new UpdateAddress();
            updateAddress.setId(address == null ? "0" : String.valueOf(address.getId()));
            updateAddress.setCityname(receiverAddress.getText().toString());
            updateAddress.setPhone(receiverPhone.getText().toString());
            updateAddress.setContact(receiverName.getText().toString());
            updateAddress.setAddress(receiverAddress.getText().toString());
            updateAddress.setStatus(isDefault ? "1" : "0");
            ApiFactory.updateAddress(new Request(updateAddress)).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                @Override
                protected void onNextInActive(ApiResponse apiResponse) {
                    ToastMaster.popToast(getContext(), "添加成功");
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    ToastMaster.popToast(getContext(), "添加失败");
                    finish();
                }
            });
        }
    }

    @Override
    protected void initView() {

        setRightText("保存");
//        if()

        this.setDefaultAddress = (RippleLinearLayout) findViewById(R.id.set_default_address);
        setDefaultAddress.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                if (setDefaultRadio.isChecked()) {
                    setDefaultRadio.setChecked(false);
                } else {
                    setDefaultRadio.setChecked(true);
                }
            }
        });
        this.receiverAddressDetail = (EditText) findViewById(R.id.receiver_address_detail);
        this.receiverAddress = (RippleButton) findViewById(R.id.receiver_address);
        receiverAddress.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                SetProvinceAdressActivity.luanchForResult(AddressActivity.this, true);
            }
        });
        this.receiverPhone = (EditText) findViewById(R.id.receiver_phone);
        this.receiverName = (EditText) findViewById(R.id.receiver_name);
        setDefaultRadio = (RadioButton) findViewById(R.id.set_default_radio);
        setDefaultRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefault = isChecked;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ActivityExtra.SELECT_PROVINCE_NAME && resultCode == RESULT_OK) {
            String aname = data.getStringExtra("aname");
            String cname = data.getStringExtra("cname");
            String pname = data.getStringExtra("pname");
            receiverAddress.setText(pname + " " + cname + " " + aname);
        }
    }

    @Override
    protected void initData() {
        int addressMode = getIntent().getIntExtra(Constants.ActivityExtra.ADDRESS_MODE, ADD_ADDRESS);
//        getIntent().
        if (addressMode == ADD_ADDRESS) {
            setTitle("新建地址");
        } else {
            setTitle("编辑地址");

            address = getIntent().getParcelableExtra(Constants.ActivityExtra.ADDRESS);
            receiverAddress.setText(address.getCityname());
            receiverAddressDetail.setText(address.getAddress());
            receiverName.setText(address.getContact());
            receiverPhone.setText(address.getPhone());
            setDefaultRadio.setChecked(address.getStatus() == 1);

        }
    }

    public static void launcForAdd(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra(Constants.ActivityExtra.ADDRESS_MODE, ADD_ADDRESS);
        context.startActivity(intent);//
    }

    public static void launcForEdit(Context context, Address address) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra(Constants.ActivityExtra.ADDRESS_MODE, EDIT_ADDRESS);
        intent.putExtra(Constants.ActivityExtra.ADDRESS, address);
        context.startActivity(intent);//
    }
}
