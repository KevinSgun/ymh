package cn.kuailaimei.client.home.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.ShopListBean;
import cn.kuailaimei.client.api.request.NearStoreRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.NearStoreListResposne;
import cn.kuailaimei.client.common.SP;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.map.BaiduMapHelper;
import cn.kuailaimei.client.map.LocationCallBack;
import cn.kuailaimei.client.shop.ui.ShopHomeActivity;

/**
 * Created by lu on 2016/6/17.
 */
public class NearbyFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener {
    private LinearLayout maplayout;
    /**
     * MapView 是地图主控件
     */
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private BaiduMapOptions options;    //地图基础配置
    //    private double mLat = 40.056858;                //纬度
//    private double mLng = 116.308194;                //经度
    private LatLng mLatLng;             //经纬度值对象
    private Marker mMarker;             //覆盖标注图标
    private String mMarkerTitle;        //标注图标的文字
    private BDLocation mLocation;       //百度定位位置对象
    private InfoWindow mInfoWindow;
    private boolean isLocationStart;    //判断附近网点是否已经获取过
    private int currentType;            //联网请求标识
    private ArrayList<Marker> mMarkers; //标注对象
    private List<ShopListBean> storeList;
    private BaiduMapHelper baiduMapHel;
    private FrameLayout searchLayout;
    private static final float ZOOM = 14.0f;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            startLocation();
            return false;
        }
    });
    private BitmapDescriptor bdA;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_near_map;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        maplayout = (LinearLayout) contentView.findViewById(R.id.map_layout);
//        searchLayout = (FrameLayout) contentView.findViewById(R.id.search_layout);
        mMarkers = new ArrayList<Marker>();
        SP sp = SP.getDefaultSP();
        String locationStr = sp.getString(Constants.BAI_DU_MAP, null);
        double mLat = 0;
        double mLng = 0;
        if (locationStr != null) {
            String[] locations;
            locations = locationStr.split(Constants.BAI_DU_SPLIT);
            try {
                mLat = Double.parseDouble(locations[0]);
                mLng = Double.parseDouble(locations[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (mLat != 0 && mLng != 0)
            mLatLng = new LatLng(mLat, mLng);

        initMapView();
        setMapZoom(ZOOM);

        maplayout.addView(mMapView);
        mBaiduMap.setOnMarkerClickListener(this);

//        searchLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        baiduMapHel = new BaiduMapHelper();
        mHandler.sendEmptyMessageDelayed(11, 200);
    }

    private void getShopNet() {
        if (storeList == null || storeList.size() == 0) {
            NearStoreRequest nearStoreRequest = new NearStoreRequest();
            nearStoreRequest.setLongitude(String.valueOf(mLocation.getLongitude()));
            nearStoreRequest.setLatitude(String.valueOf(mLocation.getLatitude()));
            Request request = new Request(nearStoreRequest);
            request.sign();
            ApiFactory.getNearest(request).subscribe(new ProgressSubscriber<ApiResponse<NearStoreListResposne>>(this) {
                @Override
                protected void onNextInActive(ApiResponse<NearStoreListResposne> nearStoreListResposneApiResponse) {
                    NearStoreListResposne resposne = nearStoreListResposneApiResponse.getData();
                    if (resposne != null) {
                        storeList = resposne.getStoreList();
                        showShopNet();
                    }
                }
            });
        }
    }

    public void startLocation() {
        baiduMapHel.startLocation(getActivity(), new LocationCallBack() {
            @Override
            public void onLocationSuccess(BDLocation location) {
                if (location != null) {
                    mLocation = location;
                    mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    SP sp = SP.getDefaultSP();
                    sp.putString(Constants.BAI_DU_MAP, location.getLatitude() + Constants.BAI_DU_SPLIT + location.getLongitude());

                    MyLocationData locData = new MyLocationData.Builder().latitude(location.getLatitude())
                            .longitude(location.getLongitude()).build();
                    //设置图标在地图上的位置
                    mBaiduMap.setMyLocationData(locData);

                    setLoaction();
                    getShopNet();
                    baiduMapHel.stopLoaction();
                }
            }

            @Override
            public void onLocationFail(String result) {
                baiduMapHel.stopLoaction();
            }
        });
    }

    public void initMapView() {
        options = new BaiduMapOptions();
        options.zoomControlsEnabled(false); //隐藏缩放控件
        mMapView = new TextureMapView(getActivity(), options);
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.clear();
        mBaiduMap.setMyLocationEnabled(true);
    }

    /**
     * 定位到某个位置 传递经纬度值
     */
    public void setLoaction() {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(mLatLng, ZOOM);
        mBaiduMap.animateMapStatus(u);
    }

    /**
     * 设定地图缩放级别
     *
     * @param zoom
     */
    public void setMapZoom(float zoom) {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(zoom);
        mBaiduMap.setMapStatus(msu);
    }


    private void showShopNet() {
        if (storeList != null && storeList.size() > 0) {
            mBaiduMap.clear();
            mMarkers.clear();
            for (ShopListBean storeListBean : storeList)
                setMarker(storeListBean);
            isLocationStart = true;
        }
    }

    public void setMarker(ShopListBean store) {
        double mLat = 0;
        double mLng = 0;
        try {
            mLat = Double.parseDouble(store.getLatitude());
            mLng = Double.parseDouble(store.getLongitude());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Marker marker = initOyerLay(mLat, mLng, 2, store.getId() + Constants.BAI_DU_SPLIT + store.getStatus());
        mMarkers.add(marker);
    }

    /**
     * 初始化覆盖图 图标
     *
     * @param lat           维度
     * @param lng           经度
     * @param animationType 0无动画 1从天上掉下 2从地上生长
     */
    public Marker initOyerLay(double lat, double lng, int animationType, String title) {
        if(bdA == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_map_local);
            bdA = BitmapDescriptorFactory.fromBitmap(bm);
        }

        // 初始化覆盖物位置 及配置信息
        LatLng llA = new LatLng(lat, lng);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(false);
        // 设置覆盖物出现动画
        if (animationType == 0) {
            ooA.animateType(MarkerOptions.MarkerAnimateType.none);
        } else if (animationType == 1) {
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
        } else {
            ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
        }
        // 添加图标到地图中展示
        Marker mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
        mMarker.setTitle(title);

        return mMarker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (ViewUtils.isFastDoubleClick()) return false;
        String str = marker.getTitle();
        if (str != null) {
            String[] markerStr = str.split(Constants.BAI_DU_SPLIT);
            if ("0".equals(markerStr[1])) {
                CommonDialog dialog = new CommonDialog(getActivity(), "该店铺正在审核中，敬请期待");
                dialog.show();
            } else {
                ShopHomeActivity.launch(getActivity(), markerStr[0]);
            }
        }

        return true;
    }

    @Override
    public void onPause() {
        maplayout.setVisibility(View.GONE);
        if (mMapView != null) mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        maplayout.setVisibility(View.VISIBLE);
        if (mMapView != null) mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRefreshData() {
        maplayout.setVisibility(View.VISIBLE);
        super.onRefreshData();
        startLocation();
    }

}

