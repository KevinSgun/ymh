package cn.kuailaimei.client.mall.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.request.IDRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.common.widget.ToolBarHelper;
import cn.kuailaimei.client.mall.adapter.ChooseAddressAdapter;

/**
 * Created by lu on 2016/7/17.
 */
public class ChooseAddressActivity extends AppBarActivity {

    private ListView userAddress;
    private ViewAnimator addressViewAnimator;
    private RippleButton addAddress;
    private RippleButton confirm;
    private ChooseAddressAdapter addressAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_address;
    }
    @Override
    protected void initView() {
        setTitle("选择地址");
        userAddress = (ListView) findViewById(R.id.userAddress);
        addressViewAnimator = (ViewAnimator) findViewById(R.id.addressViewAnimator);
        addAddress = (RippleButton) findViewById(R.id.addAddress);
        confirm = (RippleButton) findViewById(R.id.confirm);
        confirm.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                if(addressAdapter.getChoosedAddress()!=null){
                    Intent data=new Intent();
                    data.putExtra(Constants.ActivityExtra.ADDRESS,addressAdapter.getChoosedAddress());
                    setResult(RESULT_OK,data);
                    finish();
                }else{
                    ToastMaster.shortToast("请选择收获地址");
                }

            }
        });
        addAddress.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                AddressActivity.launcForAdd(getContext());
            }
        });
        confirm.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                Address address = addressAdapter.getChoosedAddress();
                if (address == null) {
                    ToastMaster.popToast(getContext(), "请选择收货地址");
                    return;
                }
                Intent data = new Intent();
                data.putExtra(Constants.ActivityExtra.ADDRESS, address);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        setRightImg(R.mipmap.ic_add);
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            AddressActivity.launcForAdd(getContext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Request request = new Request(null);
        ApiFactory.getAddressList(request).subscribe(new ProgressSubscriber<ApiResponse<List<Address>>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<List<Address>> listApiResponse) {
                List<Address> addressList = listApiResponse.getData();
                if (addressList != null && addressList.size() > 0) {
                    addressViewAnimator.setDisplayedChild(1);
                    addressAdapter = new ChooseAddressAdapter(getContext());
                    addressAdapter.setList(addressList);
                    addressAdapter.setOnDefaultAddressChangedListener(new ChooseAddressAdapter.OnAddressCheckChangedListener() {
                        @Override
                        public void onAddressChecjChanged(Address address) {
                            //choosedAddress=address;
//                            IDRequest id = new IDRequest();
//                            id.setId(String.valueOf(address.getId()));
//                            ApiFactory.setDefaultAddress(new Request(id)).subscribe(new ProgressSubscriber<ApiResponse>() {
//                                @Override
//                                protected void onNextInActive(ApiResponse apiResponse) {
//
//                                }
//                            });
                        }


                    });
                    userAddress.setAdapter(addressAdapter);


                } else {
                    addressViewAnimator.setDisplayedChild(0);
                }

            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void launchForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, ChooseAddressActivity.class);
        context.startActivityForResult(intent, requestCode);
    }
}
