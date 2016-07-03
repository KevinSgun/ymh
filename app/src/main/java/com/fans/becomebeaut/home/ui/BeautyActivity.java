package com.fans.becomebeaut.home.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.PricesBean;
import com.fans.becomebeaut.api.entity.ServicesBean;
import com.fans.becomebeaut.api.entity.Store;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.request.StoreListRequest;
import com.fans.becomebeaut.api.response.StoreListResponse;
import com.fans.becomebeaut.common.SP;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.widget.BeautyServiceLayout;
import com.fans.becomebeaut.common.widget.ContentViewHolder;
import com.fans.becomebeaut.common.widget.MVCSwipeRefreshHelper;
import com.fans.becomebeaut.common.widget.PriceChooseLayout;
import com.fans.becomebeaut.home.adapter.ShopRecycleViewAdapter;
import com.fans.becomebeaut.home.datasource.ShopListDataSource;
import com.fans.becomebeaut.map.BaiduMapHelper;
import com.fans.becomebeaut.map.LocationCallBack;
import com.fans.becomebeaut.utils.ToastMaster;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.MVCHelper;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import rx.functions.Action1;

/**
 * 我要美容or美发
 * Created by lu on 2016/7/2.
 */
public class BeautyActivity extends AppBarActivity {


    private ContentViewHolder contentViewHolder;
    private BaiduMapHelper baiduMapHelper;
    private double latitude;
    private double longitude;
    private String sid;
    private PriceChooseLayout priceLayout;
    private BeautyServiceLayout serciceLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MVCHelper<List<Store>> mvcHelper;
    private IAsyncDataSource<List<Store>> dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_beauty;
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra(Constants.ActivityExtra.TITLE));
        contentViewHolder = (ContentViewHolder) findViewById(R.id.content_root);
        priceLayout = (PriceChooseLayout) findViewById(R.id.choose_price_layout);
        serciceLayout = (BeautyServiceLayout) findViewById(R.id.choose_service_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.store_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        sid = getIntent().getStringExtra(Constants.ActivityExtra.SID);
        contentViewHolder.showLoading();
        baiduMapHelper = new BaiduMapHelper();
        startLocation();
    }


    private void startLocation() {
        baiduMapHelper.startLocation(this, new LocationCallBack() {
            @Override
            public void onLocationSuccess(BDLocation location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                SP sp = SP.getDefaultSP();
                sp.putString(Constants.BAI_DU_MAP, location.getLatitude() + Constants.BAI_DU_SPLIT + location.getLongitude());
                requestData();
                baiduMapHelper.stopLoaction();
            }

            @Override
            public void onLocationFail(String result) {
                ToastMaster.shortToast("获取定位信息失败");
                requestData();
                baiduMapHelper.stopLoaction();
            }
        });
    }

    public void requestData() {
        StoreListRequest storeListRequest = new StoreListRequest();
        storeListRequest.setLongitude(String.valueOf(longitude));
        storeListRequest.setLatitude(String.valueOf(latitude));
        storeListRequest.setCId(sid);
        Request request = new Request(storeListRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<Store>>(swipeRefreshLayout);
        dataSource = new ShopListDataSource(storeListRequest);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new ShopRecycleViewAdapter(this));
        // 加载数据
        mvcHelper.refresh();
        ApiFactory.getHomeSalonStores(request).subscribe(new Action1<ApiResponse<StoreListResponse>>() {
            @Override
            public void call(ApiResponse<StoreListResponse> storeListResponseApiResponse) {
                contentViewHolder.showContent();
                render(storeListResponseApiResponse.getData());
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                contentViewHolder.showRetry();
            }
        });
    }

    private void render(final StoreListResponse data) {
        priceLayout.setPriceList(data.getPrices());
        priceLayout.setOnPriceCheckChangedListener(new PriceChooseLayout.OnPriceCheckChangedListener() {
            @Override
            public void onPriceChecked(PricesBean pricesBean) {
                ShopListDataSource dataSource = (ShopListDataSource) BeautyActivity.this.dataSource;
                dataSource.filterPrice(pricesBean.getId());
                //参数
                mvcHelper.refresh();
            }
        });
        serciceLayout.setBeautyService(data.getServices());
        serciceLayout.setOnBeautyCheckChangeListener(new BeautyServiceLayout.OnBeautyCheckChangeListener() {
            @Override
            public void onBeautyCheckChanged(ServicesBean servicesBean) {
                ShopListDataSource dataSource = (ShopListDataSource) BeautyActivity.this.dataSource;
                dataSource.filterCid(servicesBean.getId());
                mvcHelper.refresh();
            }
        });
//        data.get
    }

    public static void launch(Context context, String sid, String title) {
        Intent intent = new Intent(context, BeautyActivity.class);
        intent.putExtra(Constants.ActivityExtra.SID, sid);
        intent.putExtra(Constants.ActivityExtra.TITLE, title);
        context.startActivity(intent);
    }
}
