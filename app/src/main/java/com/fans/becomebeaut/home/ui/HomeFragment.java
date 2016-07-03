package com.fans.becomebeaut.home.ui;


import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.HomePageBanner;
import com.fans.becomebeaut.api.entity.NearShop;
import com.fans.becomebeaut.api.entity.ServicesBean;
import com.fans.becomebeaut.api.request.HomeDataRequest;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.HomePageResponse;
import com.fans.becomebeaut.common.SP;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.common.widget.CirclePageIndicator;
import com.fans.becomebeaut.common.widget.LoopViewPager;
import com.fans.becomebeaut.common.widget.OnRippleCompleteListener;
import com.fans.becomebeaut.common.widget.RippleFrameLayout;
import com.fans.becomebeaut.common.widget.RippleLinearLayout;
import com.fans.becomebeaut.common.widget.RippleView;
import com.fans.becomebeaut.home.adapter.HomeDataAdapter;
import com.fans.becomebeaut.map.BaiduMapHelper;
import com.fans.becomebeaut.map.LocationCallBack;
import com.fans.becomebeaut.shop.ui.ShopHomeActivity;
import com.fans.becomebeaut.utils.ToastMaster;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.util.List;

/**
 * Created by lu on 2016/6/16.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnRippleCompleteListener {

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
    private RippleLinearLayout msglayout;
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
    private HomeDataAdapter mAdapter;
    private RippleLinearLayout consumerrecordlayout;
    private BaiduMapHelper baiduMapHelper;
    public double latitude;//纬度
    public double longitude;//经度
    private List<ServicesBean> serviceList;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        SP sp = SP.getDefaultSP();
        String locationStr = sp.getString(Constants.BAI_DU_MAP, null);

        if (locationStr != null) {
            String[] locations;
            locations = locationStr.split(Constants.BAI_DU_SPLIT);
            try {
                latitude = Double.parseDouble(locations[0]);
                longitude = Double.parseDouble(locations[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        msglayout = (RippleLinearLayout) contentView.findViewById(R.id.msg_layout);
        shoplistview = (ListView) contentView.findViewById(R.id.shop_list_view);

        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_home_page, null);
        initHeader(header);
        initEvent();
        shoplistview.addHeaderView(header);
        mAdapter = new HomeDataAdapter(getActivity());
        shoplistview.setAdapter(mAdapter);
        shoplistview.setOnItemClickListener(this);
        baiduMapHelper = new BaiduMapHelper();
    }

    private void initEvent() {
        msglayout.setOnRippleCompleteListener(this);
        consumerrecordlayout.setOnRippleCompleteListener(this);
        wantbeautylayout.setOnRippleCompleteListener(this);
        wantsalonlayout.setOnRippleCompleteListener(this);
    }

    private void startLocation() {
        baiduMapHelper.startLocation(getActivity(), new LocationCallBack() {
            @Override
            public void onLocationSuccess(BDLocation location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                SP sp = SP.getDefaultSP();
                sp.putString(Constants.BAI_DU_MAP, location.getLatitude() + Constants.BAI_DU_SPLIT + location.getLongitude());
                requestHomeData();
                baiduMapHelper.stopLoaction();
            }

            @Override
            public void onLocationFail(String result) {
                ToastMaster.shortToast("获取定位信息失败");
                requestHomeData();
                baiduMapHelper.stopLoaction();
            }
        });
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        startLocation();
        showUnReadMessageIcon();
    }

    private void showUnReadMessageIcon() {

    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        if ((pagerAdapter != null && pagerAdapter.getCount() == 0) || (mAdapter != null && mAdapter.getCount() == 0)) {
            startLocation();
        }
    }

    private void requestHomeData() {
        HomeDataRequest homeDataRequest = new HomeDataRequest();
        homeDataRequest.setLatitude(String.valueOf(latitude));
        homeDataRequest.setLongitude(String.valueOf(longitude));
        Request request = new Request(homeDataRequest);
        request.sign();
        ApiFactory.getHomeData(request).subscribe(new ProgressSubscriber<ApiResponse<HomePageResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<HomePageResponse> homePageResponseApiResponse) {
                HomePageResponse homePageResponse = homePageResponseApiResponse.getData();
                if (homePageResponse != null) {
                    refreshHeaderUI(homePageResponse);
                    mAdapter.setList(homePageResponse.getStoreList());
                    serviceList = homePageResponse.getServiceList();
                }
            }
        });
    }


    private void refreshHeaderUI(HomePageResponse homePageResponse) {
        HomePageResponse.LastStoreBean lastStoreBean = homePageResponse.getLastStore();
        if (lastStoreBean != null && lastStoreBean.getName() != null) {
            consumerrecordlayout.setVisibility(View.VISIBLE);
            consumeritemstv.setText(lastStoreBean.getServiceName());
            consumerdatetv.setText(lastStoreBean.getAddDate());
            shopiv.setImageUri(R.mipmap.ic_shop_default, lastStoreBean.getIcon());
            shopnametv.setText(lastStoreBean.getName());
//                distancetv.setText(lastStoreBean.get);
            shopaddresstv.setText(lastStoreBean.getAddress());
        } else {
            consumerrecordlayout.setVisibility(View.GONE);
        }

        initPager(homePageResponse.getBanners());
    }

    private void initHeader(View header) {
        // 轮播图mm
        homePager = (LoopViewPager) header.findViewById(R.id.home_pager);
        indicator = (CirclePageIndicator) header.findViewById(R.id.home_pager_indicator);
        consumerrecordlayout = (RippleLinearLayout) header.findViewById(R.id.consumer_record_layout);
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
        if (bannerList == null) return;
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
        if (ViewUtils.isFastDoubleClick()) return;
        int index = position > 0 && position - 1 >= 0 ? position - 1 : position;
//        List<HomePageBanner> pagerAdapter.getItemList();
        NearShop shop = mAdapter.getList().get(position);

//        NearShop
//        adapterView.ge
        //TODO 进入店铺首页
        ShopHomeActivity.launch(getActivity(), String.valueOf(shop.getId()));
    }

    @Override
    public void onComplete(View v) {
        if (ViewUtils.isFastDoubleClick()) return;
        switch (v.getId()) {
            case R.id.msg_layout:
                ToastMaster.shortToast("消息");
                break;
            case R.id.want_beauty_layout:
                String sid = getServiceId("美容");
                BeautyActivity.launch(getActivity(), sid, "我要美容");
                break;
            case R.id.want_salon_layout:
                String serviceId = getServiceId("美发");
                BeautyActivity.launch(getActivity(), serviceId, "我要美发");
                break;
            case R.id.consumer_record_layout:
                ToastMaster.shortToast("消费过的店铺");
                break;

        }
    }

    private String getServiceId(String name) {

        if (serviceList != null) {
            for (ServicesBean service : serviceList) {
                if (name.equals(service.getName())) {
                    return service.getId();
                }
            }
        }
        return name.equals("美发") ? "10001" : "10000";
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
            return bannerList != null ? bannerList.size() : 0;
        }

        public List<HomePageBanner> getItemList() {
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
