package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ExchangeHistoryItem;
import cn.kuailaimei.client.api.request.ExchangeHistoryRequest;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.mall.adapter.ExchangeHistoryListAdapter;
import cn.kuailaimei.client.mall.datasource.ExchangeHistoryDataSource;

/**
 * Created by lu on 2016/7/17.
 */
public class ExchangeHistoryActivity extends AppBarActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView exchangeHistoryList;
    private MVCSwipeRefreshHelper<List<ExchangeHistoryItem>> mvcHelper;
    private boolean isFirst;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_exchange_history;
    }

    @Override
    protected void initView() {
        setTitle("兑换记录");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        exchangeHistoryList = (RecyclerView) findViewById(R.id.exchange_history_list);
        exchangeHistoryList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void requestData() {
        ExchangeHistoryRequest requestData = new ExchangeHistoryRequest();
        mvcHelper = new MVCSwipeRefreshHelper<List<ExchangeHistoryItem>>(swipeRefreshLayout);
        ExchangeHistoryDataSource dataSource = new ExchangeHistoryDataSource(requestData);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new ExchangeHistoryListAdapter(getContext()));
        // 加载数据
        mvcHelper.refresh();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirst) {
            isFirst = false;
        } else {
            mvcHelper.refresh();
        }
    }

    @Override
    protected void initData() {
        requestData();
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, ExchangeHistoryActivity.class);
        context.startActivity(intent);
    }
}
