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
package cn.kuailaimei.client.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Employee;

public class AssistantRecycleViewAdapter extends RecyclerView.Adapter<AssistantRecycleViewAdapter.DesignerViewHolder> {
    private List<Employee> employeeList = new ArrayList<Employee>();
    private LayoutInflater inflater;
    private Context mContext;
    private OnAssistantChoosedListener onAssistantChoosedListener;
    private Employee currentChecked;

    public void setOnAssistantChoosedListener(OnAssistantChoosedListener onAssistantChoosedListener) {
        this.onAssistantChoosedListener = onAssistantChoosedListener;
    }

    public interface OnAssistantChoosedListener {

        public void onAssistantChoosed(Employee employee);
    }

    public AssistantRecycleViewAdapter(Context context, List<Employee> employeeList) {
        super();
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.employeeList = employeeList;
    }

    @Override
    public DesignerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DesignerViewHolder(inflater.inflate(R.layout.item_assistant, parent, false));
    }

    @Override
    public void onBindViewHolder(DesignerViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        RemoteImageView avatar = holder.avatar;
        TextView name = holder.name;
        final TextView tag = holder.tag;
        TextView rate = holder.rate;
        final RadioButton choose = holder.choose;
        name.setText(employee.getAlias());
        tag.setText(employee.getPosition());
        // price.setText("￥" + employee.getBottomPrice() + "起");
        rate.setText(employee.getSatisfactory());
        avatar.setImageUri(employee.getPortrait());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                if (!choose.isChecked()) {
                    choose.setChecked(true);
                }

            }
        });
        choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (employee != currentChecked) {
                        if (currentChecked != null) {
                            currentChecked.setChecked(false);
                        }
                        employee.setChecked(true);
                        currentChecked = employee;
                        notifyDataSetChanged();
                        onAssistantChoosedListener.onAssistantChoosed(employee);
                    }

                }
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
        //        TextView price;
        RadioButton choose;

        public DesignerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            avatar = (RemoteImageView) view.findViewById(R.id.avatar);
            tag = (TextView) view.findViewById(R.id.tag);
            rate = (TextView) view.findViewById(R.id.rate);
            // price = (TextView) view.findViewById(R.id.price);
            choose = (RadioButton) view.findViewById(R.id.choose);
        }
    }

}
