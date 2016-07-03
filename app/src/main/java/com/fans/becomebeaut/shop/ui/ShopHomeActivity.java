package com.fans.becomebeaut.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.widget.MutilRadioGroup;
import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by lu on 2016/6/21.
 */
public class ShopHomeActivity extends AppBarActivity {
    private RemoteImageView imgStore;
    private TextView shopName;
    private TextView shophours;
    private TextView shopaddress;
    private TextView shopphonenumber;
    private TextView evaluationrate;
    private RadioButton allreviews;
    private RadioButton highpositivereviews;
    private RadioButton positivereviews;
    private RadioButton negativereviews;
    private MutilRadioGroup reviewchooser;
    private CollapsingToolbarLayout collapsingtoolbar;
    private AppBarLayout appbarlayout;
    private ViewPager designerspager;
    private RadioGroup typeChooser;
    private RadioButton salonchoice;
    private RadioButton beautychoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        this.beautychoice = (RadioButton) findViewById(R.id.beauty_choice);
        this.salonchoice = (RadioButton) findViewById(R.id.salon_choice);
        this.designerspager = (ViewPager) findViewById(R.id.designers_pager);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        this.reviewchooser = (MutilRadioGroup) findViewById(R.id.review_chooser);
        this.negativereviews = (RadioButton) findViewById(R.id.negative_reviews);
        this.positivereviews = (RadioButton) findViewById(R.id.positive_reviews);
        this.highpositivereviews = (RadioButton) findViewById(R.id.high_positive_reviews);
        this.allreviews = (RadioButton) findViewById(R.id.all_reviews);
        this.evaluationrate = (TextView) findViewById(R.id.evaluation_rate);
        this.shopphonenumber = (TextView) findViewById(R.id.shop_phone_number);
        this.shopaddress = (TextView) findViewById(R.id.shop_address);
        this.shophours = (TextView) findViewById(R.id.shop_hours);
        this.shopName = (TextView) findViewById(R.id.shop_name);
        this.imgStore = (RemoteImageView) findViewById(R.id.img_store);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, ShopHomeActivity.class);
        context.startActivity(intent);
    }
}
