package cn.kuailaimei.client.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Evaluation;
import cn.kuailaimei.client.api.entity.EvaluationsHead;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.api.request.ExchangeListRequest;
import cn.kuailaimei.client.api.response.EvaluationList;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.common.widget.RecyclerViewHeader;
import cn.kuailaimei.client.mall.adapter.ExchangeListAdapter;
import cn.kuailaimei.client.mall.datasource.ExchangeDataSource;
import cn.kuailaimei.client.shop.adapter.EvaluationListAdaper;
import cn.kuailaimei.client.shop.datasource.EvaluationDataSource;

/**
 * Created by ludaiqian on 16/8/1.
 */
public class EvaluateListActivity extends AppBarActivity {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private MVCSwipeRefreshHelper<List<Evaluation>> mvcHelper;
    private TextView allReviews;
    private TextView highPositiveReviews;
    private TextView positiveReviews;
    private TextView negativeReviews;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_evaluation_list;
    }

    @Override
    protected void initView() {
        setTitle("店铺评价");
//        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
        View header = LayoutInflater.from(this).inflate(R.layout.header_evaluation_list, null);
        allReviews = (TextView) header.findViewById(R.id.all_reviews);
        highPositiveReviews = (TextView) header.findViewById(R.id.high_positive_reviews);
        positiveReviews = (TextView) header.findViewById(R.id.positive_reviews);
        negativeReviews = (TextView) header.findViewById(R.id.negative_reviews);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        listView = (ListView) findViewById(R.id.comment_list);
        listView.addHeaderView(header);
    }

    @Override
    protected void initData() {
        requestData();
    }


    public void requestData() {
        String shopId = getIntent().getStringExtra(Constants.ActivityExtra.SHOP_ID);
        mvcHelper = new MVCSwipeRefreshHelper<List<Evaluation>>(swipeRefreshLayout);
        EvaluationDataSource dataSource = new EvaluationDataSource(shopId);
        dataSource.setOnDataLoadedListener(new EvaluationDataSource.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(EvaluationList data) {
                EvaluationsHead head = data.getHead();
                if(head!=null){
                    allReviews.setText("全部评价("+head.getAllComment()+")");
                    highPositiveReviews.setText("非常满意("+head.getPerfectCount()+")");
                    positiveReviews.setText("满意("+head.getGoodCount()+")");
                    negativeReviews.setText("不满意("+head.getBadCount()+")");

                }
            }
        });
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new EvaluationListAdaper(this));
        // 加载数据
        mvcHelper.refresh();

    }


    public static void launch(Context context, String shopId) {
        Intent intent = new Intent(context, EvaluateListActivity.class);
        intent.putExtra(Constants.ActivityExtra.SHOP_ID, shopId);
        context.startActivity(intent);
    }
}
