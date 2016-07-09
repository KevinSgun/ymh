package cn.kuailaimei.client.mine.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.OrderItem;
import cn.kuailaimei.client.api.request.OrderListRequest;
import cn.kuailaimei.client.api.request.PageRequest;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.mine.adapter.OrderAdapter;
import cn.kuailaimei.client.mine.datasource.OrderListDataSource;

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
    protected void onPreInflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onPreInflate(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
    }

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

    @Subscribe
    public void onMainThreadOrderInfoChange(EventFactory.OrderListDataChange data){
        if("-1".equals(data.status)||("0".equals(status)||data.status.equals(status))&&mvcHelper!=null){
            mvcHelper.refresh();
            EventBus.getDefault().post(new EventFactory.OrderCountDataChange());
        }
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
