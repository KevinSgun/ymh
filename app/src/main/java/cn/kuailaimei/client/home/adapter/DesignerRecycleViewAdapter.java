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

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Employee;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.login.ui.LoginActivity;
import cn.kuailaimei.client.shop.ui.DesignerHomeActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;

import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

public class DesignerRecycleViewAdapter extends RecyclerView.Adapter<DesignerRecycleViewAdapter.DesignerViewHolder> {
    private List<Employee> employeeList = new ArrayList<Employee>();
    private LayoutInflater inflater;
    private Context mContext;

    public DesignerRecycleViewAdapter(Context context, List<Employee> employeeList) {
        super();
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.employeeList = employeeList;
    }

    @Override
    public DesignerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DesignerViewHolder(inflater.inflate(R.layout.item_designer_order, parent, false));
    }

    @Override
    public void onBindViewHolder(DesignerViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.get().notLogin()) {
                    LoginActivity.launch((Activity) mContext, false);
                    return;
                }
                DesignerHomeActivity.launch(mContext, String.valueOf(employee.getUid()));
            }
        });
        RemoteImageView avatar = holder.avatar;
        TextView name = holder.name;
        TextView tag = holder.tag;
        TextView rate = holder.rate;
        TextView price = holder.price;
        Button order = holder.order;
        name.setText(employee.getAlias());
        tag.setText(employee.getPosition());
        price.setText("￥" + employee.getBottomPrice() + "起");
        rate.setText(employee.getSatisfactory());
        avatar.setImageUri(employee.getPortrait());
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastMaster.popToast(mContext, "设计师:" + employee.getAlias());
                if (User.get().notLogin()) {
                    LoginActivity.launch((Activity) mContext, false);
                    return;
                }
                DesignerHomeActivity.launch(mContext, String.valueOf(employee.getUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    class DesignerViewHolder extends ViewHolder {

        RemoteImageView avatar;
        TextView name;
        TextView tag;
        TextView rate;
        TextView price;
        Button order;

        public DesignerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            avatar = (RemoteImageView) view.findViewById(R.id.avatar);
            tag = (TextView) view.findViewById(R.id.tag);
            rate = (TextView) view.findViewById(R.id.rate);
            price = (TextView) view.findViewById(R.id.price);
            order = (Button) view.findViewById(R.id.order);
        }
    }

}
