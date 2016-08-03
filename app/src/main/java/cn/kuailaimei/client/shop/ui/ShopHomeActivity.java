package cn.kuailaimei.client.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.ShopInfo;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SIDRequest;
import cn.kuailaimei.client.api.response.ShopDetailRequest;
import cn.kuailaimei.client.api.response.ShopDetailResponse;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.widget.FullyGridLayoutManager;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.common.widget.ToolBarHelper;
import cn.kuailaimei.client.home.adapter.DesignerRecycleViewAdapter;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;

import org.w3c.dom.Text;

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
    //    private ViewAnimator designersPager;
    private RadioGroup typeChooser;
    private RadioButton salonChoice;
    private RadioButton beautyChoice;
    private String id;
    private RecyclerView salonList;
    private TextView employeeNum;
    //    private RecyclerView beautyList;
    private RippleLinearLayout evaluations;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void initView() {
        setTitle("快来美");
        this.beautyChoice = (RadioButton) findViewById(R.id.beauty_choice);
        this.salonChoice = (RadioButton) findViewById(R.id.salon_choice);
//        this.designersPager = (ViewAnimator) findViewById(R.id.designers_pager);
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
        this.shopImg = (RemoteImageView) findViewById(R.id.img_store);
        this.salonList = (RecyclerView) findViewById(R.id.salon_list);
        this.typeChooser = (RadioGroup) findViewById(R.id.type_chooser);
        this.employeeNum = (TextView) findViewById(R.id.employee_num);
        this.evaluations = (RippleLinearLayout) findViewById(R.id.evaluations);
//        this.beautyList = (RecyclerView) findViewById(R.id.beauty_list);
        salonList.setLayoutManager(new FullyGridLayoutManager(this, 2));
//        beautyList.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private boolean isFavorite = false;
    private String sid;

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            if (!TextUtils.isEmpty(sid)) {
                if (isFavorite) {
                    SIDRequest req = new SIDRequest();
                    req.setsId(sid);
                    ApiFactory.deleteFavorite(new Request(req)).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            ToastMaster.shortToast("取消收藏成功");
                            isFavorite = false;
                            setRightImg(R.mipmap.mark);
                        }
                    });
                } else {
                    SIDRequest req = new SIDRequest();
                    req.setsId(sid);
                    ApiFactory.favorite(new Request(req)).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            ToastMaster.shortToast("添加收藏成功");
                            isFavorite = true;
                            setRightImg(R.mipmap.marked);
                        }
                    });
                }
            }
        }
    }

    private void render(final ShopDetailResponse response) {
        final ShopInfo shopInfo = response.getStoreInfo();//.getPerfectCount();
        sid = String.valueOf(shopInfo.getId());
        allReviews.setText("全部(" + shopInfo.getAllComment() + ")");
        highPositiveReviews.setText("很满意(" + shopInfo.getPerfectCount() + ")");
        positiveReviews.setText("满意(" + shopInfo.getGoodCount() + ")");
        negativeReviews.setText("不满意(" + shopInfo.getGoodCount() + ")");
        shopImg.setImageUri(shopInfo.getIcon());
        shopName.setText(shopInfo.getName());
        shopPhonenumber.setText(shopInfo.getPhone());
        int num = response.getEmployeeList() != null ? response.getEmployeeList().size() : 0;
        employeeNum.setText("服务人员推荐(" + num + ")");
        evaluationRate.setText(shopInfo.getSatisfactory());
        this.shopImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDetailActivity.launch(ShopHomeActivity.this, shopInfo);
            }
        });
        if (shopInfo.getIsStoreUp() == 1) {
            isFavorite = true;
            setRightImg(R.mipmap.marked);
        } else {
            isFavorite = false;
            setRightImg(R.mipmap.mark);
        }
        evaluations.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                EvaluateListActivity.launch(getContext(),String.valueOf(shopInfo.getId()));
            }
        });
//        reviewChooser
//        salonList.setAdapter();
//        allOrSalonList=new
        DesignerRecycleViewAdapter adapter = new DesignerRecycleViewAdapter(this, response.getEmployeeList());
        salonList.setAdapter(adapter);
//        allOrSalonList.setonI

        typeChooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (response.getServiceList() != null && response.getServiceList().size() == 2) {

                    if (checkedId == salonChoice.getId()) {
                        if (response.getServiceList().get(0).getName().contains("美发")) {
                            requestData(id, response.getServiceList().get(0).getId());
                        } else {
                            requestData(id, response.getServiceList().get(1).getId());
                        }
                    } else {
                        if (response.getServiceList().get(0).getName().contains("美容")) {
                            requestData(id, response.getServiceList().get(0).getId());
                        } else {
                            requestData(id, response.getServiceList().get(1).getId());
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void initData() {
//        RequestData
        id = getIntent().getStringExtra(Constants.ActivityExtra.ID);
        requestData(id, null);

    }

    private void requestData(String id, String cid) {
        ShopDetailRequest data = new ShopDetailRequest();
        data.setId(id);
        data.setCId(cid);
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
