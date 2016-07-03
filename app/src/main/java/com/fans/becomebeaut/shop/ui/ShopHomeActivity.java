package com.fans.becomebeaut.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.ShopInfo;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.ShopDetailRequest;
import com.fans.becomebeaut.api.response.ShopDetailResponse;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.home.adapter.DesignerRecycleViewAdapter;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by lu on 2016/6/21.
 */
public class ShopHomeActivity extends AppBarActivity {
    private RemoteImageView shopImg;
    private TextView shopName;
    private TextView shopHours;
    private TextView shopAddress;
    private TextView shopPhonenumber;
    private TextView evaluationRate;
    private TextView allReviews;
    private TextView highPositiveReviews;
    private TextView positiveReviews;
    private TextView negativeReviews;
    //    private MutilRadioGroup reviewChooser;
    //    private CollapsingToolbarLayout collapsingtoolbar;
    private AppBarLayout appbarLayout;
    private ViewAnimator designersPager;
    private RadioGroup typeChooser;
    private RadioButton salonChoice;
    private RadioButton beautyChoice;
    private String id;
    private RecyclerView allOrSalonList;
    private RecyclerView beautyList;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void initView() {
        setTitle("快来美");
        this.beautyChoice = (RadioButton) findViewById(R.id.beauty_choice);
        this.salonChoice = (RadioButton) findViewById(R.id.salon_choice);
        this.designersPager = (ViewAnimator) findViewById(R.id.designers_pager);
        this.appbarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
//        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        this.reviewChooser = (MutilRadioGroup) findViewById(R.id.review_chooser);
        this.negativeReviews = (TextView) findViewById(R.id.negative_reviews);
        this.positiveReviews = (TextView) findViewById(R.id.positive_reviews);
        this.highPositiveReviews = (TextView) findViewById(R.id.high_positive_reviews);
        this.allReviews = (TextView) findViewById(R.id.all_reviews);
        this.evaluationRate = (TextView) findViewById(R.id.evaluation_rate);
        this.shopPhonenumber = (TextView) findViewById(R.id.shop_phone_number);
        this.shopAddress = (TextView) findViewById(R.id.shop_address);
        this.shopHours = (TextView) findViewById(R.id.shop_hours);
        this.shopName = (TextView) findViewById(R.id.shop_name);
        this.shopImg = (RemoteImageView) findViewById(R.id.shop_img);
        this.allOrSalonList = (RecyclerView) findViewById(R.id.salon_list);
        this.beautyList = (RecyclerView) findViewById(R.id.beauty_list);
        allOrSalonList.setLayoutManager(new GridLayoutManager(this, 2));
        beautyList.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void render(ShopDetailResponse response) {
        ShopInfo shopInfo = response.getStoreInfo();//.getPerfectCount();
        allReviews.setText("全部(" + shopInfo.getAllComment() + ")");
        highPositiveReviews.setText("很满意(" + shopInfo.getPerfectCount() + ")");
        positiveReviews.setText("满意(" + shopInfo.getGoodCount() + ")");
        negativeReviews.setText("不满意(" + shopInfo.getGoodCount() + ")");
        shopImg.setImageUri(shopInfo.getIcon());
        shopName.setText(shopInfo.getName());
        shopPhonenumber.setText(shopInfo.getPhone());
        evaluationRate.setText(shopInfo.getSatisfactory());
//        reviewChooser
//        salonList.setAdapter();
//        allOrSalonList=new
        DesignerRecycleViewAdapter adapter = new DesignerRecycleViewAdapter(this, response.getEmployeeList());
        allOrSalonList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
//        RequestData
        id = getIntent().getStringExtra(Constants.ActivityExtra.ID);
        ShopDetailRequest data = new ShopDetailRequest();
        data.setId(id);
//        data.set
        Request request = new Request(data);
        ApiFactory.getShopDetail(request).subscribe(new ProgressSubscriber<ApiResponse<ShopDetailResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<ShopDetailResponse> shopDetailResponseApiResponse) {
                ShopDetailResponse response = shopDetailResponseApiResponse.getData();
                render(response);

            }


        });
    }

    public static void launch(Context context, String id) {
        Intent intent = new Intent(context, ShopHomeActivity.class);
        intent.putExtra(Constants.ActivityExtra.ID, id);
        context.startActivity(intent);
    }
}
