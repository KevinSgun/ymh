package cn.kuailaimei.client.mine.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ScoreBean;
import cn.kuailaimei.client.api.request.PageRequest;
import cn.kuailaimei.client.api.request.ScoreListRequest;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.mine.adapter.ScoreAdapter;
import cn.kuailaimei.client.mine.datasource.ScoreListDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreListFragment extends BaseFragment{
    private static final String QUERY_DATE = "query_date";
    private String queryDate;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<ScoreBean>> mvcHelper;
    private ScoreListDataSource dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_score_list;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Bundle bundle = getArguments();
        if(bundle!=null){
            queryDate = bundle.getString(QUERY_DATE);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        ScoreListRequest scoreListRequest = new ScoreListRequest();
        scoreListRequest.setDate(queryDate);
        PageRequest request = new PageRequest(scoreListRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<ScoreBean>>(swipeRefreshLayout);
        dataSource = new ScoreListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new ScoreAdapter(getActivity(),new ArrayList<ScoreBean>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static ScoreListFragment getInstance(String queryDate) {
        Bundle bundle = new Bundle();
        bundle.putString(QUERY_DATE,queryDate);
        ScoreListFragment fragment = new ScoreListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
