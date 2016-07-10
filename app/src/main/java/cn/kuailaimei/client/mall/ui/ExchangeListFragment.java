package cn.kuailaimei.client.mall.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.api.request.ExchangeListRequest;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.mall.adapter.ExchangeListAdapter;
import cn.kuailaimei.client.mall.datasource.ExchangeDataSource;

/**
 * Created by lu on 2016/7/9.
 */
public class ExchangeListFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MVCSwipeRefreshHelper<List<ExchangeItem>> mvcHelper;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_exchang_list;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.exhagne_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        requestData();
    }

    public void requestData() {
        ExchangeListRequest requestData = new ExchangeListRequest();
        requestData.setGoodsType(String.valueOf(1));
        mvcHelper = new MVCSwipeRefreshHelper<List<ExchangeItem>>(swipeRefreshLayout);
        ExchangeDataSource dataSource = new ExchangeDataSource(requestData);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new ExchangeListAdapter(getActivity()));
        // 加载数据
        mvcHelper.refresh();

    }

    public static ExchangeListFragment newInstance(int groupId) {
        ExchangeListFragment fragment = new ExchangeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FragmentExtra.GROUP_ID, groupId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ExchangeListFragment newInstanceOfFree() {
        return newInstance(1);
    }

    public static ExchangeListFragment newInstanceOfBrand() {
        return newInstance(2);
    }
}
