package com.fans.becomebeaut.home.ui;


import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.HomePageBanner;
import com.fans.becomebeaut.api.request.HomeDataRequest;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.HomePageResponse;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.common.widget.CirclePageIndicator;
import com.fans.becomebeaut.common.widget.LoopViewPager;
import com.fans.becomebeaut.common.widget.OnRippleCompleteListener;
import com.fans.becomebeaut.common.widget.RippleFrameLayout;
import com.fans.becomebeaut.common.widget.RippleView;
import com.fans.becomebeaut.home.adapter.HomePageAdapter;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by lu on 2016/6/16.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private final static int AUTO = 10;
    private final static long DELAY_TIME = 4 * 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO:

                    int index = homePager.getCurrentItem();

                    homePager.setCurrentItem(index + 1);

                    mHandler.sendEmptyMessageDelayed(AUTO, DELAY_TIME);

                    break;

                default:
                    break;
            }
            return false;
        }
    });
    private LoopViewPager homePager;
    private CirclePageIndicator indicator;
    private HomePagerAdapter pagerAdapter;
    private LinearLayout msglayout;
    private ListView shoplistview;
    private FrameLayout header;
    private RippleFrameLayout wantbeautylayout;
    private RippleFrameLayout wantsalonlayout;
    private TextView consumeritemstv;
    private TextView consumerdatetv;
    private RemoteImageView shopiv;
    private TextView shopnametv;
    private TextView distancetv;
    private TextView shopaddresstv;
    private HomePageAdapter mAdapter;
    private LinearLayout consumerrecordlayout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        msglayout = (LinearLayout) contentView.findViewById(R.id.msg_layout);
        shoplistview = (ListView) contentView.findViewById(R.id.shop_list_view);

        View header  = LayoutInflater.from(getActivity()).inflate(R.layout.header_home_page,null);
        initHeader(header);
        shoplistview.addHeaderView(header);
        mAdapter = new HomePageAdapter(getActivity());
        shoplistview.setAdapter(mAdapter);
        shoplistview.setOnItemClickListener(this);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        HomeDataRequest homeDataRequest = new HomeDataRequest();
        homeDataRequest.setLatitude("22.62");
        homeDataRequest.setLongitude("114.07");
        Request request = new Request(homeDataRequest);
        request.sign();
        ApiFactory.getHomeData(request).subscribe(new ProgressSubscriber<HomePageResponse>(this) {
            @Override
            protected void onNextInActive(HomePageResponse homePageResponse) {
                if(homePageResponse != null){
                    refreshHeaderUI(homePageResponse);
                    mAdapter.setList(homePageResponse.getStoreList());
                }
            }

        });

    }


    private void refreshHeaderUI(HomePageResponse homePageResponse) {
        HomePageResponse.LastStoreBean lastStoreBean = homePageResponse.getLastStore();
       if(lastStoreBean!=null) {
           consumerrecordlayout.setVisibility(View.VISIBLE);
           consumeritemstv.setText(lastStoreBean.getServiceName());
           consumerdatetv.setText(lastStoreBean.getAddDate());
           shopiv.setImageUri(R.mipmap.ic_shop_default, lastStoreBean.getIcon());
           shopnametv.setText(lastStoreBean.getName());
//                distancetv.setText(lastStoreBean.get);
           shopaddresstv.setText(lastStoreBean.getAddress());
       }else{
           consumerrecordlayout.setVisibility(View.GONE);
       }

        initPager(homePageResponse.getBanners());
    }

    private void initHeader(View header) {
        // 轮播图mm
        homePager = (LoopViewPager) header.findViewById(R.id.home_pager);
        indicator = (CirclePageIndicator) header.findViewById(R.id.home_pager_indicator);
        consumerrecordlayout = (LinearLayout) header.findViewById(R.id.consumer_record_layout);
        wantbeautylayout = (RippleFrameLayout) header.findViewById(R.id.want_beauty_layout);
        wantsalonlayout = (RippleFrameLayout) header.findViewById(R.id.want_salon_layout);
        consumeritemstv = (TextView) header.findViewById(R.id.consumer_items_tv);
        consumerdatetv = (TextView) header.findViewById(R.id.consumer_date_tv);
        shopiv = (RemoteImageView) header.findViewById(R.id.shop_iv);
        shopnametv = (TextView) header.findViewById(R.id.shop_name_tv);
        distancetv = (TextView) header.findViewById(R.id.distance_tv);
        shopaddresstv = (TextView) header.findViewById(R.id.shop_address_tv);
    }

    private void initPager(List<HomePageBanner> bannerList) {
        if(bannerList == null) return;
        pagerAdapter = new HomePagerAdapter(bannerList);
        homePager.setAdapter(pagerAdapter);
        indicator.setViewPager(homePager);
        pagerAdapter.setOnPagerItemClickListener(new OnPagerItemClickListener() {

            @Override
            public void onPagerItemClick(int position) {
                HomePageBanner banner = pagerAdapter.getItemList().get(position);

            }

        });
        if (bannerList.size() > 1 && !mHandler.hasMessages(AUTO)) {
            mHandler.sendEmptyMessageDelayed(AUTO, DELAY_TIME);
        } else {
            mHandler.removeMessages(AUTO);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int index = position > 0 && position - 1 >= 0 ? position - 1 : position;
        //TODO 进入店铺首页
    }

    class HomePagerAdapter extends PagerAdapter {
        List<HomePageBanner> bannerList;
        private OnPagerItemClickListener onPagerItemClickListener;

        public HomePagerAdapter(List<HomePageBanner> bannerList) {
            super();
            this.bannerList = bannerList;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            homePager.removeView((View) object);
        }

        @Override
        public int getCount() {
            return bannerList.size();
        }

        public List<HomePageBanner> getItemList() {
            return bannerList;
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            if(getActivity() == null) return null;
            RippleView item = new RippleView(getActivity());
            RemoteImageView iv = new RemoteImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            iv.setImageUri(Constants.ImageDefault.RECTANGLE_LANDSCAPE,bannerList.get(position).getImg());
            iv.setImageUri(bannerList.get(position).getImg());
            item.setOnRippleCompleteListener(new OnRippleCompleteListener() {

                @Override
                public void onComplete(View rippleView) {
                    if (onPagerItemClickListener != null)
                        onPagerItemClickListener.onPagerItemClick(position);
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

        public void setOnPagerItemClickListener(OnPagerItemClickListener onPagerItemClickListener) {
            this.onPagerItemClickListener = onPagerItemClickListener;
        }

    }

    public interface OnPagerItemClickListener {
        void onPagerItemClick(int position);
    }
}
