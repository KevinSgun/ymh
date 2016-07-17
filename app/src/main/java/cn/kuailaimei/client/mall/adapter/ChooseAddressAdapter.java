package cn.kuailaimei.client.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.mall.ui.AddressActivity;

/**
 * Created by lu on 2016/7/17.
 */
public class ChooseAddressAdapter extends ListAdapter<Address> {
    private Address currentChoosed;

    public ChooseAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_address, null);
        }
        TextView receiverName = ViewHolderUtil.get(convertView, R.id.receiver_name);
        TextView receiverPhone = ViewHolderUtil.get(convertView, R.id.receiver_phone);
        TextView receiverAddress = ViewHolderUtil.get(convertView, R.id.receiver_address);
        RippleLinearLayout edit = ViewHolderUtil.get(convertView, R.id.edit);
        RippleLinearLayout delete = ViewHolderUtil.get(convertView, R.id.delete);
        RadioButton choosed = ViewHolderUtil.get(convertView, R.id.choosed);
        RippleLinearLayout chooseAddressLayout = ViewHolderUtil.get(convertView, R.id.choose_address_layout);
        final Address item = mList.get(position);
        receiverName.setText(item.getContact());
        receiverAddress.setText(item.getCityname() + item.getAddress());
        receiverPhone.setText(item.getPhone());
        if (item.getStatus() == 1) {
            choosed.setChecked(true);
            if (currentChoosed != null) {
                currentChoosed.setStatus(0);
            }
            currentChoosed = item;
            notifyDataSetChanged();
        } else {
            choosed.setChecked(false);
        }
        chooseAddressLayout.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                item.setStatus(1);
                if (currentChoosed != null) {
                    currentChoosed.setStatus(0);
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

            }
        });
        return convertView;
    }

    public Address getChoosedAddress() {
        return currentChoosed;
    }
}
