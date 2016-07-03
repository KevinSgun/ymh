package com.fans.becomebeaut.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.ScoreBean;
import com.fans.becomebeaut.common.ListAdapter;
import com.fans.becomebeaut.utils.ViewHolderUtil;
import com.shizhefei.mvc.IDataAdapter;

import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreAdapter extends ListAdapter<ScoreBean> implements IDataAdapter<List<ScoreBean>> {
    private TextView coupontype;
    private TextView consumerdatetv;
    private TextView consumermoneytv;

    public ScoreAdapter(Context context,List<ScoreBean> list) {
        super(context);
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(convertView == null){
           convertView = LayoutInflater.from(mContext).inflate(R.layout.item_coupon,null);
       }
        ScoreBean item = mList.get(position);
        TextView coupontype = ViewHolderUtil.get(convertView,R.id.coupon_type);
        TextView consumerdatetv = ViewHolderUtil.get(convertView,R.id.consumer_date_tv);
        TextView consumermoneytv = ViewHolderUtil.get(convertView,R.id.consumer_money_tv);
        if(item!=null){
            coupontype.setText(item.getRemark());
            consumerdatetv.setText(item.getDate());
            if(item.getAccess() == 1){
                consumermoneytv.setTextColor(Color.RED);
                consumermoneytv.setText("+"+item.getPoints());
            }else{
                consumermoneytv.setTextColor(Color.GRAY);
                consumermoneytv.setText("-"+item.getPoints());
            }
        }
        return convertView;
    }

    private void initialize() {


    }

    @Override
    public void notifyDataChanged(List<ScoreBean> scoreBeen, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(scoreBeen);
        notifyDataSetChanged();
    }

    @Override
    public List<ScoreBean> getData() {
        return mList;
    }
}
