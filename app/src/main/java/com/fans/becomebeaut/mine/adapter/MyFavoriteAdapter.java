package com.fans.becomebeaut.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.NearStore;
import com.fans.becomebeaut.common.ListAdapter;
import com.fans.becomebeaut.utils.ViewHolderUtil;
import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyFavoriteAdapter extends ListAdapter<NearStore> implements IDataAdapter<List<NearStore>> {

    public MyFavoriteAdapter(Context context, List<NearStore> list) {
        super(context);
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_shop,null);
        }
        NearStore item = mList.get(position);
        RemoteImageView shopiv = ViewHolderUtil.get(convertView,R.id.shop_iv);
        TextView shopnametv = ViewHolderUtil.get(convertView,R.id.shop_name_tv);
        TextView shopaddresstv = ViewHolderUtil.get(convertView,R.id.shop_address_tv);

        if(item != null){
            shopiv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w20)));
            shopiv.setImageUri(R.mipmap.ic_shop_default,item.getIcon());
            shopnametv.setText(item.getName());
            shopaddresstv.setText(item.getAddress());
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<NearStore> storeList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(storeList);
        notifyDataSetChanged();
    }

    @Override
    public List<NearStore> getData() {
        return mList;
    }
}
