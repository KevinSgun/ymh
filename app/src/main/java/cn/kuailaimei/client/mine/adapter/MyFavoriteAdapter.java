package cn.kuailaimei.client.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.NearShop;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;
import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyFavoriteAdapter extends ListAdapter<NearShop> implements IDataAdapter<List<NearShop>> {

    public MyFavoriteAdapter(Context context, List<NearShop> list) {
        super(context);
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_shop,null);
        }
        NearShop item = mList.get(position);
        RemoteImageView shopiv = ViewHolderUtil.get(convertView,R.id.shop_iv);
        TextView shopnametv = ViewHolderUtil.get(convertView,R.id.shop_name_tv);
        TextView distancetv = ViewHolderUtil.get(convertView,R.id.distance_tv);
        TextView shopaddresstv = ViewHolderUtil.get(convertView,R.id.shop_address_tv);
        distancetv.setVisibility(View.GONE);
        if(item != null){
            shopiv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w20)));
            shopiv.setImageUri(R.mipmap.ic_shop_default,item.getIcon());
            shopnametv.setText(item.getCorp());
            shopaddresstv.setText(item.getAddress());
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<NearShop> storeList, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(storeList);
        notifyDataSetChanged();
    }

    @Override
    public List<NearShop> getData() {
        return mList;
    }
}
