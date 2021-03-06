package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.Sku;
import cn.kuailaimei.client.api.entity.Stock;
import cn.kuailaimei.client.api.request.IDRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.ExchangeDetailResponse;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.ui.BaseActivity;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.widget.CirclePageIndicator;
import cn.kuailaimei.client.common.widget.LoopViewPager;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.common.widget.RippleView;
import cn.kuailaimei.client.login.ui.LoginActivity;

/**
 * Created by lu on 2016/7/9.
 */
public class GoodsDetailActivity extends BaseActivity {

    private LoopViewPager bannerPager;
    private CirclePageIndicator bannerIndicator;
    private TextView goodsName;
    private TextView price;
    private TextView freight;
    private TextView availableNum;
    private TextView exchangedNum;
    private TextView tips;
    private RippleLinearLayout chooseSpec;
    private TextView description;
    private RippleLinearLayout contactSercie;
    private RippleButton exchangeNow;

    private Sku choosedSku;
    private TextView choosedSkuText;
    private Stock choosedStock;
    private GoodsDetail goodsDetail;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods;
    }

    @Override
    protected void initView() {
        this.exchangeNow = (RippleButton) findViewById(R.id.exchangeNow);
        this.contactSercie = (RippleLinearLayout) findViewById(R.id.contactSercie);
        this.description = (TextView) findViewById(R.id.description);
        this.chooseSpec = (RippleLinearLayout) findViewById(R.id.chooseSpec);
        this.tips = (TextView) findViewById(R.id.tips);
        this.exchangedNum = (TextView) findViewById(R.id.exchangedNum);
        this.availableNum = (TextView) findViewById(R.id.availableNum);
        this.freight = (TextView) findViewById(R.id.freight);
        this.price = (TextView) findViewById(R.id.price);
        this.goodsName = (TextView) findViewById(R.id.goodsName);
        this.bannerIndicator = (CirclePageIndicator) findViewById(R.id.banner_indicator);
        this.bannerPager = (LoopViewPager) findViewById(R.id.banner);
        this.choosedSkuText= (TextView) findViewById(R.id.choose_spec_tv);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void render(final ExchangeDetailResponse data) {
        final GoodsDetail detail = data.getGoods();
        this.goodsDetail=detail;

        if(!TextUtils.isEmpty(data.getPhone())){
            contactSercie.setOnRippleCompleteListener(new OnRippleCompleteListener() {
                @Override
                public void onComplete(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+data.getPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
//        data.getStock();
        description.setText(Html.fromHtml(detail.getContent()));
        tips.setText(detail.getTooltip());
        availableNum.setText("可兑换 " + detail.getInventory() + "件");
        exchangedNum.setText("已兑换 " + detail.getSales() + "件");
        if (detail.getPrice() > 0) {
            price.setText(Utils.formartPrice(detail.getPrice()) + "+" + detail.getScore() + "美劵");

        } else {
            price.setText(detail.getScore() + "美劵");
        }
        goodsName.setText(detail.getName());
        freight.setText("运费￥" + detail.getFare());
//        detail.getPhotos();
        GoodsBannerAdapter adapter = new GoodsBannerAdapter(detail.getPhotos());
        bannerPager.setAdapter(adapter);
        bannerIndicator.setViewPager(bannerPager);
        chooseSpec.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                ChooseSpecActivity.launchForResult((GoodsDetailActivity.this), detail, data.getSku(),data.getStock(), Constants.ActivityExtra.REQUEST_FOR_CHOOSE_SPEC);

            }
        });
        exchangeNow.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                if(choosedSku==null||choosedStock==null){
                    ChooseSpecActivity.launchForResult((GoodsDetailActivity.this), detail, data.getSku(),data.getStock(), Constants.ActivityExtra.REQUEST_FOR_CHOOSE_SPEC);
                }else{
                    tryOrder(getContext(), goodsDetail, choosedStock);
                }
            }
        });
    }

    private void tryOrder(Context context, GoodsDetail goodsDetail, Stock choosedStock) {
        if(User.get().notLogin()){
            LoginActivity.launch(this,false);
        }else {
            OrderActivity.launchForOrder(context, goodsDetail, choosedStock);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode== Constants.ActivityExtra.REQUEST_FOR_CHOOSE_SPEC){
            choosedStock=data.getParcelableExtra(Constants.ActivityExtra.CHOOSE_STOCK);
            tryOrder(this, goodsDetail, choosedStock);
        }
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra(Constants.ActivityExtra.ID);
        IDRequest requestData = new IDRequest();
        requestData.setId(id);
        Request request = new Request(requestData);

        ApiFactory.getExchangeDetail(request).subscribe(new ProgressSubscriber<ApiResponse<ExchangeDetailResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<ExchangeDetailResponse> exchangeDetailResponseApiResponse) {
                ExchangeDetailResponse data = exchangeDetailResponseApiResponse.getData();
                render(data);
            }
        });
    }

    class GoodsBannerAdapter extends PagerAdapter {
        List<String> bannerList;
        private OnGoodsBannerClickListener onGoodsBannerClickListener;

        public GoodsBannerAdapter(List<String> bannerList) {
            super();
            this.bannerList = bannerList;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            bannerPager.removeView((View) object);
        }

        @Override
        public int getCount() {
            return bannerList != null ? bannerList.size() : 0;
        }

        public List<String> getItemList() {
            return bannerList;
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            RippleView item = new RippleView(GoodsDetailActivity.this);
            RemoteImageView iv = new RemoteImageView(GoodsDetailActivity.this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setImageUri(Constants.ImageDefault.RECTANGLE_LANDSCAPE,bannerList.get(position).getImg());
            iv.setImageUri(bannerList.get(position));
            item.setOnRippleCompleteListener(new OnRippleCompleteListener() {

                @Override
                public void onComplete(View rippleView) {
                    if (onGoodsBannerClickListener != null)
                        onGoodsBannerClickListener.onExchangeItemClick(position);
                }
            });
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            item.addView(iv, params);
            ((LoopViewPager) container).addView(item);
            return item;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void setOnGoodsBannerClickListener(OnGoodsBannerClickListener onGoodsBannerClickListener) {
            this.onGoodsBannerClickListener = onGoodsBannerClickListener;
        }

    }

    public interface OnGoodsBannerClickListener {
        void onExchangeItemClick(int position);
    }

    public static void launch(Context context, String id) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.ID, id);
        context.startActivity(intent);
    }
}
