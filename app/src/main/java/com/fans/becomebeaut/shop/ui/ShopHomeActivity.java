package com.fans.becomebeaut.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.widget.MutilRadioGroup;
import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by lu on 2016/6/21.
 */
public class ShopHomeActivity extends AppBarActivity {
    private RemoteImageView storeImg;
    private TextView shopName;
    private TextView shopHours;
    private TextView shopAddress;
    private TextView shopPhonenumber;
    private TextView evaluationRate;
    private RadioButton allReviews;
    private RadioButton highPositiveReviews;
    private RadioButton positiveReviews;
    private RadioButton negativeReviews;
    private MutilRadioGroup reviewChooser;
    //    private CollapsingToolbarLayout collapsingtoolbar;
    private AppBarLayout appbarLayout;
    private ViewPager designersPager;
    private RadioGroup typeChooser;
    private RadioButton salonChoice;
    private RadioButton beautyChoice;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void initView() {
        this.beautyChoice = (RadioButton) findViewById(R.id.beauty_choice);
        this.salonChoice = (RadioButton) findViewById(R.id.salon_choice);
        this.designersPager = (ViewPager) findViewById(R.id.designers_pager);
        this.appbarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
//        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.reviewChooser = (MutilRadioGroup) findViewById(R.id.review_chooser);
        this.negativeReviews = (RadioButton) findViewById(R.id.negative_reviews);
        this.positiveReviews = (RadioButton) findViewById(R.id.positive_reviews);
        this.highPositiveReviews = (RadioButton) findViewById(R.id.high_positive_reviews);
        this.allReviews = (RadioButton) findViewById(R.id.all_reviews);
        this.evaluationRate = (TextView) findViewById(R.id.evaluation_rate);
        this.shopPhonenumber = (TextView) findViewById(R.id.shop_phone_number);
        this.shopAddress = (TextView) findViewById(R.id.shop_address);
        this.shopHours = (TextView) findViewById(R.id.shop_hours);
        this.shopName = (TextView) findViewById(R.id.shop_name);
        this.storeImg = (RemoteImageView) findViewById(R.id.img_store);
    }

    @Override
    protected void initData() {
        ApiFactory.
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, ShopHomeActivity.class);
        context.startActivity(intent);
    }
}
