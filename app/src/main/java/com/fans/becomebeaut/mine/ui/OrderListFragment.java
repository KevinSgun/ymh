package com.fans.becomebeaut.mine.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.OrderItem;
import com.fans.becomebeaut.api.request.OrderListRequest;
import com.fans.becomebeaut.api.request.PageRequest;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.common.widget.MVCSwipeRefreshHelper;
import com.fans.becomebeaut.mine.adapter.OrderAdapter;
import com.fans.becomebeaut.mine.datasource.OrderListDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class OrderListFragment extends BaseFragment{
    private static final String STATUS = "status";
    private String status = "0";
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<OrderItem>> mvcHelper;
    private OrderListDataSource dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my_order_list;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Bundle bundle = getArguments();
        if(bundle!=null){
            status = bundle.getString(STATUS);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        OrderListRequest orderListRequest = new OrderListRequest();
        orderListRequest.setStatus(status);
        PageRequest request = new PageRequest(orderListRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<OrderItem>>(swipeRefreshLayout);
        dataSource = new OrderListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new OrderAdapter(getActivity(),new ArrayList<OrderItem>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static OrderListFragment getInstance(String status) {
        Bundle bundle = new Bundle();
        bundle.putString(STATUS,status);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
