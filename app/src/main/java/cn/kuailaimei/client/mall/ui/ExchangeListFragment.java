package cn.kuailaimei.client.mall.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.BaseFragment;

/**
 * Created by lu on 2016/7/9.
 */
public class ExchangeListFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView exchangeList;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_exchang_list;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.refresh_layout);
        exchangeList = (RecyclerView) contentView.findViewById(R.id.exhagne_list);
    }
}
