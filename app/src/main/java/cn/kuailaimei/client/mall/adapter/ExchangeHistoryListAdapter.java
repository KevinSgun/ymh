/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package cn.kuailaimei.client.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.ExchangeHistoryItem;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.mall.ui.GoodsDetailActivity;
import cn.kuailaimei.client.mall.ui.OrderActivity;

public class ExchangeHistoryListAdapter extends RecyclerView.Adapter<ExchangeHistoryListAdapter.ExchangeHistoryViewHolder> implements IDataAdapter<List<ExchangeHistoryItem>> {
    private List<ExchangeHistoryItem> storeList = new ArrayList<ExchangeHistoryItem>();
    private LayoutInflater inflater;
    private Context mContext;

    public ExchangeHistoryListAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public ExchangeHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeHistoryViewHolder(inflater.inflate(R.layout.item_exchange_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ExchangeHistoryViewHolder holder, int position) {
        final ExchangeHistoryItem item = storeList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShopHomeActivity.launch(mContext,store.getId());
//                GoodsDetailActivity.launch(mContext, String.valueOf(item.getId()));
//                OrderActivity.la
//                ApiFactory.getOrderList()
                OrderActivity.launchForComplatePayment(mContext,item.getPo());
            }
        });
        TextView name = holder.name;
        TextView price = holder.price;

        TextView orderDate=holder.orderDate;
        TextView orderId=holder.orderid;
        TextView orderStatus=holder.orderStatus;
        holder.oldPriceLayout.setVisibility(View.GONE);
        holder.desp.setVisibility(View.GONE);

        RemoteImageView icon = holder.icon;
        name.setText(item.getName());
        if (item.getPrice() > 0) {
            price.setText(Utils.formartPrice(item.getPrice()) + "+" + item.getScore() + "美劵");

        } else {
            price.setText(item.getScore() + "美劵");
        }
        orderDate.setText(item.getDate());
        orderId.setText(item.getPo());
        orderStatus.setText("1".equals(item.getStatus())?"已付款":"待支付");
        icon.setImageUri(R.mipmap.ic_shop_default, item.getCover());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public void notifyDataChanged(List<ExchangeHistoryItem> data, boolean isRefresh) {
        if (isRefresh) {
            storeList.clear();
        }
        storeList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public List<ExchangeHistoryItem> getData() {
        return storeList;
    }

    @Override
    public boolean isEmpty() {
        return storeList.isEmpty();
    }

    class ExchangeHistoryViewHolder extends ViewHolder {
        TextView orderid;
        TextView orderDate;
        TextView orderStatus;
        TextView name;
        TextView price;
        LinearLayout oldPriceLayout;
        TextView desp;
        RemoteImageView icon;

        public ExchangeHistoryViewHolder(View view) {
            super(view);
            orderid= (TextView) view.findViewById(R.id.orderId);
            orderDate= (TextView) view.findViewById(R.id.orderDate);
            orderStatus= (TextView) view.findViewById(R.id.orderStatus);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            oldPriceLayout = (LinearLayout) view.findViewById(R.id.old_price_layout);
            desp = (TextView) view.findViewById(R.id.desp);
            icon = (RemoteImageView) view.findViewById(R.id.icon);

        }
    }

}
