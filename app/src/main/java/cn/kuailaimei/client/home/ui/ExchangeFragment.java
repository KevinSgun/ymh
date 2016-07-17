package cn.kuailaimei.client.home.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;
import com.zitech.framework.widget.SlidingTabs;
import com.zitech.framework.widget.TabsFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.ExchangeBanner;
import cn.kuailaimei.client.api.request.ExchangeListRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.ExchangeListResponse;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.CirclePageIndicator;
import cn.kuailaimei.client.common.widget.LoopViewPager;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.common.widget.RippleView;
import cn.kuailaimei.client.mall.ui.ExchangeHistoryActivity;
import cn.kuailaimei.client.mall.ui.ExchangeListFragment;
import cn.kuailaimei.client.mall.adapter.ExchangeListAdapter;
import cn.kuailaimei.client.mall.ui.ScoreOrderDetailActivity;
import cn.kuailaimei.client.mine.ui.MyScoreActivity;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class ExchangeFragment extends BaseFragment {

    private RippleLinearLayout exchangeHistory;
    private RippleLinearLayout userAccount;
    private LoopViewPager bannerPager;
    private CollapsingToolbarLayout collapsingToolbar;
    private SlidingTabs slidingTabs;
    private ViewPager viewPager;
    private CirclePageIndicator bannerIndicator;

    //    private
    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        this.viewPager = (ViewPager) contentView.findViewById(R.id.viewpager);
        this.slidingTabs = (SlidingTabs) contentView.findViewById(R.id.sliding_tabs);
        this.collapsingToolbar = (CollapsingToolbarLayout) contentView.findViewById(R.id.collapsing_toolbar);
        this.bannerPager = (LoopViewPager) contentView.findViewById(R.id.banner);
        this.userAccount = (RippleLinearLayout) contentView.findViewById(R.id.user_account);
        this.exchangeHistory = (RippleLinearLayout) contentView.findViewById(R.id.exchange_history);
        this.bannerIndicator = (CirclePageIndicator) contentView.findViewById(R.id.banner_indicator);
        userAccount.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                MyScoreActivity.launch(getActivity(), 0);
            }
        });
        exchangeHistory.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                ExchangeHistoryActivity.launch(getContext());
            }
        });
//        ApiFactory.get

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();

        ExchangeListRequest requestData = new ExchangeListRequest();
        requestData.setGoodsType(String.valueOf(1));
        Request request = new Request(requestData);
        request.sign();
        ApiFactory.getExchangList(request).subscribe(new ProgressSubscriber<ApiResponse<ExchangeListResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<ExchangeListResponse> exchangeListResponseApiResponse) {
                ExchangeListResponse data = exchangeListResponseApiResponse.getData();
                List<ExchangeBanner> banners = data.getBanner();

                ExcahgneBannerAdapter adapter = new ExcahgneBannerAdapter(banners);
                adapter.setOnExchangeBannerClickListener(new OnExchangeBannerClickListener() {
                    @Override
                    public void onExchangeItemClick(int position) {

                    }
                });
                bannerPager.setAdapter(adapter);
                bannerIndicator.setViewPager(bannerPager);

            }
        });
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ExchangeListFragment.newInstanceOfFree());
        fragments.add(ExchangeListFragment.newInstanceOfBrand());
        String[] titles = {"免费兑换", "品牌兑换"};
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this.getFragmentManager(), titles,
                fragments);
        viewPager.setAdapter(adapter);
        slidingTabs.setViewPager(viewPager);
    }

    class ExcahgneBannerAdapter extends PagerAdapter {
        List<ExchangeBanner> bannerList;
        private OnExchangeBannerClickListener onExchangeBannerClickListener;

        public ExcahgneBannerAdapter(List<ExchangeBanner> bannerList) {
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

        public List<ExchangeBanner> getItemList() {
            return bannerList;
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            if (getActivity() == null) return null;
            RippleView item = new RippleView(getActivity());
            RemoteImageView iv = new RemoteImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv.setImageUri(Constants.ImageDefault.RECTANGLE_LANDSCAPE,bannerList.get(position).getImg());
            iv.setImageUri(bannerList.get(position).getImg());
            item.setOnRippleCompleteListener(new OnRippleCompleteListener() {

                @Override
                public void onComplete(View rippleView) {
                    if (onExchangeBannerClickListener != null)
                        onExchangeBannerClickListener.onExchangeItemClick(position);
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

        public void setOnExchangeBannerClickListener(OnExchangeBannerClickListener onExchangeBannerClickListener) {
            this.onExchangeBannerClickListener = onExchangeBannerClickListener;
        }

    }

    public interface OnExchangeBannerClickListener {
        void onExchangeItemClick(int position);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_exchange;
    }
}
