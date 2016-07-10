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
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.mall.ui.GoodsDetailActivity;

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.ExchangeViewHolder> implements IDataAdapter<List<ExchangeItem>> {
    private List<ExchangeItem> storeList = new ArrayList<ExchangeItem>();
    private LayoutInflater inflater;
    private Context mContext;

    public ExchangeListAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public ExchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeViewHolder(inflater.inflate(R.layout.item_exchange, parent, false));
    }

    @Override
    public void onBindViewHolder(ExchangeViewHolder holder, int position) {
        final ExchangeItem item = storeList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShopHomeActivity.launch(mContext,store.getId());
                GoodsDetailActivity.launch(mContext,String.valueOf(item.getId()));
            }
        });
        TextView name = holder.name;
        TextView price = holder.price;
        TextView oldPrice = holder.oldPrice;
        TextView desp = holder.desp;
        RemoteImageView icon = holder.icon;
        name.setText(item.getName());
        price.setText("￥" + item.getPrice()+item.getScore() + "美劵");
        oldPrice.setText("官方原价：￥"+item.getOldprice());
        desp.setText(item.getSubtitle());
        icon.setImageUri(R.mipmap.ic_shop_default, item.getPortrait());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public void notifyDataChanged(List<ExchangeItem> data, boolean isRefresh) {
        if (isRefresh) {
            storeList.clear();
        }
        storeList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public List<ExchangeItem> getData() {
        return storeList;
    }

    @Override
    public boolean isEmpty() {
        return storeList.isEmpty();
    }

    class ExchangeViewHolder extends ViewHolder {

        TextView name;
        TextView price;
        TextView oldPrice;
        TextView desp;
        RemoteImageView icon;

        public ExchangeViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            oldPrice = (TextView) view.findViewById(R.id.old_price);
            desp = (TextView) view.findViewById(R.id.desp);
            icon = (RemoteImageView) view.findViewById(R.id.icon);

        }
    }

}
