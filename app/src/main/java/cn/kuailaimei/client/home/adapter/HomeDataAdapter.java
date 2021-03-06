package cn.kuailaimei.client.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.NearShop;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class HomeDataAdapter extends ListAdapter<NearShop> {

    public HomeDataAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop,null);
        }
        NearShop item = mList.get(position);
        RemoteImageView shopiv = ViewHolderUtil.get(convertView,R.id.shop_iv);
        TextView shopnametv = ViewHolderUtil.get(convertView,R.id.shop_name_tv);
        TextView distancetv = ViewHolderUtil.get(convertView,R.id.distance_tv);
        TextView shopaddresstv = ViewHolderUtil.get(convertView,R.id.shop_address_tv);

        if(item != null){
            shopiv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w20)));
            shopiv.setImageUri(R.mipmap.ic_shop_default,item.getIcon());
            shopnametv.setText(item.getCorp());
            distancetv.setText(Utils.formarttDistance(item.getDistances()));
            shopaddresstv.setText(item.getAddress());
        }
        return convertView;
    }

}
