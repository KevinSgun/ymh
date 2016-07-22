package cn.kuailaimei.client.mall.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zitech.framework.data.network.IContext;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.request.IDRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.mall.ui.AddressActivity;

/**
 * Created by lu on 2016/7/17.
 */
public class ChooseAddressAdapter extends ListAdapter<Address> {
    private Address currentChoosed;

    public interface OnAddressCheckChangedListener{
        public void onAddressChecjChanged(Address address);
    }
    private  OnAddressCheckChangedListener onAddressCheckChangedListener;
    public ChooseAddressAdapter(Context context) {
        super(context);
    }

    public void setOnDefaultAddressChangedListener(OnAddressCheckChangedListener onAddressCheckChangedListener) {
        this.onAddressCheckChangedListener = onAddressCheckChangedListener;
    }

    @Override
    public void setList(List<Address> list) {
        super.setList(list);

        if (list != null) {
            for (Address address : list) {
                if (address.isDefault()) {
                    currentChoosed = address;
                    break;
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_address, null);
        }
        TextView receiverName = ViewHolderUtil.get(convertView, R.id.receiver_name);
        TextView receiverPhone = ViewHolderUtil.get(convertView, R.id.receiver_phone);
        final TextView receiverAddress = ViewHolderUtil.get(convertView, R.id.receiver_address);
        RippleLinearLayout edit = ViewHolderUtil.get(convertView, R.id.edit);
        final RippleLinearLayout delete = ViewHolderUtil.get(convertView, R.id.delete);
        final RadioButton choosed = ViewHolderUtil.get(convertView, R.id.choosed);
        RippleLinearLayout chooseAddressLayout = ViewHolderUtil.get(convertView, R.id.choose_address_layout);
        final Address item = mList.get(position);
        receiverName.setText(item.getContact());
        receiverAddress.setText(item.getCityname() + item.getAddress());
        receiverPhone.setText(item.getPhone());
        choosed.setChecked(item.isDefault());
        chooseAddressLayout.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                if (item != currentChoosed) {
                    if (currentChoosed != null) {
                        currentChoosed.setStatus(0);
                    }
                    currentChoosed = item;
                    currentChoosed.setStatus(1);
                    notifyDataSetChanged();
                    if(onAddressCheckChangedListener!=null){
                        onAddressCheckChangedListener.onAddressChecjChanged(currentChoosed);
                    }
                }
            }
        });
        edit.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                AddressActivity.launcForEdit(getContext(), item);
            }
        });
        delete.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                CommonDialog dialog=new CommonDialog(mContext,"确定删除地址?");
                dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        IDRequest request=new IDRequest();
                        request.setId(String.valueOf(item.getId()));
                        ApiFactory.deleteAddress(new Request(request)).subscribe(new ProgressSubscriber<ApiResponse>((IContext) mContext) {
                            @Override
                            protected void onNextInActive(ApiResponse apiResponse) {
                                mList.remove(item);
                                notifyDataSetChanged();
                            }
                        });
                        dialog.show();
                    }
                });
            }
        });
        return convertView;
    }

    public Address getChoosedAddress() {
        return currentChoosed;
    }
}
