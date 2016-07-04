package cn.kuailaimei.client.mine.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.OrderItem;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.utils.ViewHolderUtil;
import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class OrderAdapter extends ListAdapter<OrderItem> implements IDataAdapter<List<OrderItem>> {

    public OrderAdapter(Context context, List<OrderItem> list) {
        super(context);
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(convertView == null){
           convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order,null);
       }
        OrderItem item = mList.get(position);
        TextView ordernumtv = ViewHolderUtil.get(convertView,R.id.order_num_tv);
        TextView orderdatetv = ViewHolderUtil.get(convertView,R.id.order_date_tv);
        TextView orderstatus = ViewHolderUtil.get(convertView,R.id.order_status);
        RemoteImageView shopiconiv = ViewHolderUtil.get(convertView,R.id.shop_icon_iv);
        TextView  shopnametv = ViewHolderUtil.get(convertView,R.id.shop_name_tv);
        TextView consumeritemstv = ViewHolderUtil.get(convertView,R.id.consumer_items_tv);
        TextView consumertechniciantv = ViewHolderUtil.get(convertView,R.id.consumer_technician_tv);
        TextView ordertotalmoneytv = ViewHolderUtil.get(convertView,R.id.order_total_money_tv);
        final Button leftbtn = ViewHolderUtil.get(convertView,R.id.left_btn);
        final Button rightbtn = ViewHolderUtil.get(convertView,R.id.right_btn);
        if(item != null){
            shopiconiv.setBitmapTransformation(new RoundedCornersTransformation(mContext, ViewUtils.getDimenPx(R.dimen.w20)));
            shopiconiv.setImageUri(R.mipmap.ic_shop_default,item.getSIcon());

            ordernumtv.setText(item.getOrderId());
            orderdatetv.setText(item.getAddDate());
            orderstatus.setText(item.getMsg());
            shopnametv.setText(item.getSName());
            consumeritemstv.setText(item.getServiceName());
            consumertechniciantv.setText(item.getDesignerName());
            ordertotalmoneytv.setText(String.format(mContext.getString(R.string.rmb),item.getAmount()));
            final int status = item.getStatus();
            if(status == OrderItem.WAIT_PAY){
                leftbtn.setVisibility(View.VISIBLE);
                leftbtn.setText("取消订单");
                rightbtn.setText("马上付款");
            }else if(status == OrderItem.WAIT_COMMENT){
                leftbtn.setVisibility(View.GONE);
                rightbtn.setText("发表评价");
            }else if(status == OrderItem.COMPLETE){
                leftbtn.setVisibility(View.GONE);
                rightbtn.setText("再次购买");
            }
            leftbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(status == OrderItem.WAIT_PAY){
                        CommonDialog dialog = new CommonDialog(mContext,"确定取消订单吗");
                        dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                //TODO 取消订单
                            }
                        });
                        dialog.show();
                    }
                }
            });

            rightbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(status == OrderItem.WAIT_PAY){
                        //TODO 马上付款
//                        PayActivity
                    }else if(status == OrderItem.WAIT_COMMENT){
                        //TODO 发表评价
//                        CommentActivity
                    }else if(status == OrderItem.COMPLETE){
                        //TODO 再次购买
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public void notifyDataChanged(List<OrderItem> orderItems, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(orderItems);
        notifyDataSetChanged();
    }

    @Override
    public List<OrderItem> getData() {
        return mList;
    }

}
