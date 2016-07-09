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
package cn.kuailaimei.client.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Shop;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.shop.ui.ShopHomeActivity;

import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

public class ShopRecycleViewAdapter extends RecyclerView.Adapter<ShopRecycleViewAdapter.ShopViewHolder> implements IDataAdapter<List<Shop>> {
    private List<Shop> storeList = new ArrayList<Shop>();
    private LayoutInflater inflater;
    private Context mContext;

    public ShopRecycleViewAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopViewHolder(inflater.inflate(R.layout.item_shop_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        final Shop store = storeList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopHomeActivity.launch(mContext,store.getId());
            }
        });
        TextView shopName = holder.shopName;
        TextView shopPrice = holder.shopPrice;
        TextView distance = holder.distance;
        TextView shopAddress = holder.shopAddress;
        RemoteImageView icon = holder.shopIv;
        shopName.setText(store.getName());
        shopPrice.setText("￥"+store.getBottomPrice()+"起");

        distance.setText(Utils.formarttDistance(store.getDistances()));
        shopAddress.setText(store.getAddress());
        icon.setImageUri(R.mipmap.ic_shop_default, store.getIcon());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public void notifyDataChanged(List<Shop> data, boolean isRefresh) {
        if (isRefresh) {
            storeList.clear();
        }
        storeList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public List<Shop> getData() {
        return storeList;
    }

    @Override
    public boolean isEmpty() {
        return storeList.isEmpty();
    }

    class ShopViewHolder extends ViewHolder {

        TextView shopName;
        TextView shopAddress;
        TextView shopPrice;
        TextView distance;
        RemoteImageView shopIv;

        public ShopViewHolder(View view) {
            super(view);
            shopName = (TextView) view.findViewById(R.id.shop_name_tv);
            shopAddress = (TextView) view.findViewById(R.id.shop_address_tv);
            shopPrice = (TextView) view.findViewById(R.id.shop_price);
            distance = (TextView) view.findViewById(R.id.distance_tv);
            shopIv = (RemoteImageView) view.findViewById(R.id.shop_iv);

        }
    }

}
