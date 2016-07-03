package com.fans.becomebeaut.mall.ui;

import android.app.Activity;
import android.content.Intent;

import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;

/**
 * 积分商城订单详情
 * Created by ymh on 2016/7/4 0004.
 */
public class ScoreOrderDetailActivity extends AppBarActivity{
    private String orderId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        orderId = getIntent().getStringExtra(Constants.ActivityExtra.ORDER_ID);
        setTitle("订单详情");
    }

    @Override
    protected void initData() {

    }

    public static void launch(Activity act,String orderId){
        Intent intent = new Intent(act,ScoreOrderDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.ORDER_ID,orderId);
        act.startActivity(intent);
    }
}
