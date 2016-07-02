package com.fans.becomebeaut.home.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
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
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.StoreListBean;
import com.fans.becomebeaut.api.request.NearStoreRequest;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.response.NearStoreListResposne;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.map.BaiduMapHelper;
import com.fans.becomebeaut.map.LocationCallBack;
import com.fans.becomebeaut.utils.ToastMaster;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

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
    private List<StoreListBean> storeList;

    //    private ArrayList<NetWorkPointVO> mCompanys;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_near_map;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);

        maplayout = (LinearLayout) contentView.findViewById(R.id.map_layout);

        mMarkers = new ArrayList<Marker>();
//        mCompanys = getIntent().getParcelableArrayListExtra("Company");
        try {
//            mLat = Double.parseDouble(mCompanys.get(0).getLnglatY());
//            mLng = Double.parseDouble(mCompanys.get(0).getLnglatX());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
//        mLatLng = new LatLng(mLat, mLng);

        initMapView();
        setMapZoom(14.0f);

        maplayout.addView(mMapView);
        mBaiduMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        startLocation();
    }

    private void getShopNet() {
        NearStoreRequest nearStoreRequest = new NearStoreRequest();
        nearStoreRequest.setLongitude(String.valueOf(mLocation.getLongitude()));
        nearStoreRequest.setLatitude(String.valueOf(mLocation.getLatitude()));
        Request request = new Request(nearStoreRequest);
        request.sign();
        ApiFactory.getNearest(request).subscribe(new ProgressSubscriber<ApiResponse<NearStoreListResposne>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<NearStoreListResposne> nearStoreListResposneApiResponse) {
                NearStoreListResposne resposne = nearStoreListResposneApiResponse.getData();
                if(resposne!=null) {
                    storeList = resposne.getStoreList();
                    showShopNet();
                }
            }
        });
    }

    public void startLocation() {
        new BaiduMapHelper().startLocation(getActivity(), new LocationCallBack() {
            @Override
            public void onLocationSuccess(BDLocation location) {
                if (location != null) {
                    mLocation = location;
                    mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                    setLoaction();
                    getShopNet();
                    new BaiduMapHelper().stopLoaction();
                }
            }

            @Override
            public void onLocationFail(String result) {
                new BaiduMapHelper().stopLoaction();
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
    }

    /**
     * 定位到某个位置 传递经纬度值
     */
    public void setLoaction() {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mLatLng);
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
        if(storeList!=null&&storeList.size()>0){
            mBaiduMap.clear();
            mMarkers.clear();
            for(StoreListBean storeListBean:storeList)
                setMarker(storeListBean);
//            String content = response.getPointName() + " \r\n " + response.getPointAddress();
//            addMarkCustomView(mMarkers.get(0), content, mLat, mLng, null);
            isLocationStart = true;
//            mBaiduMap.add
        }
//        else {
//            ToastUtils.showToastInCenter(getApplicationContext(), "获取附近网点失败!");
//        }
    }

    public void setMarker(StoreListBean store){
        double mLat = 0;
        double mLng = 0;
        try {
            mLat = Double.parseDouble(store.getLatitude());
            mLng = Double.parseDouble(store.getLongitude());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Marker marker = initOyerLay(mLat, mLng, 2, String.valueOf(store.getId())+"$"+store.getName());
        mMarkers.add(marker);
    }

    /**
     * 初始化覆盖图 图标
     *
     * @param lat                    维度
     * @param lng                    经度
     * @param animationType          0无动画 1从天上掉下 2从地上生长
     */
    public Marker initOyerLay(double lat, double lng, int animationType, String title) {
        Bitmap bm = null;
        try {
//            int index = Integer.parseInt(title);
//            if (index >= 0 && index <= BaiduMapHelper.mMarkerResourceIds.length - 1) {
//                bm = BitmapFactory.decodeResource(getResources(), BaiduMapHelper.mMarkerResourceIds[index]);
//            } else {
                bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_map_local);
//            }
        } catch (Exception e) {
            e.printStackTrace();
//            bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_map_local);
        }
        // Bitmap bm1 = getNewBitMap(bm, "123");
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(bm);
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
        ToastMaster.shortToast(marker.getTitle());
        return true;
    }

    @Override
    public void onPause() {
        if(mMapView!=null)mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if(mMapView!=null)mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(mMapView!=null) mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        showShopNet();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

