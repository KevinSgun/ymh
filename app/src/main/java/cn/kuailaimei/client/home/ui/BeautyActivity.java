package cn.kuailaimei.client.home.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.location.BDLocation;
import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.PricesBean;
import cn.kuailaimei.client.api.entity.ServicesBean;
import cn.kuailaimei.client.api.entity.Shop;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.ShopListRequest;
import cn.kuailaimei.client.api.response.ShopListResponse;
import cn.kuailaimei.client.common.SP;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.BeautyServiceLayout;
import cn.kuailaimei.client.common.widget.ContentViewHolder;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.common.widget.PriceChooseLayout;
import cn.kuailaimei.client.home.adapter.ShopRecycleViewAdapter;
import cn.kuailaimei.client.home.datasource.ShopListDataSource;
import cn.kuailaimei.client.map.BaiduMapHelper;
import cn.kuailaimei.client.map.LocationCallBack;
import cn.kuailaimei.client.common.utils.ToastMaster;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.MVCHelper;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import rx.functions.Action1;

/**
 * 我要美容or美发
 * Created by lu on 2016/7/2.
 */
public class BeautyActivity extends AppBarActivity  {


    private ContentViewHolder contentViewHolder;
    private BaiduMapHelper baiduMapHelper;
    private double latitude;
    private double longitude;
    private String sid;
    private PriceChooseLayout priceLayout;
    private BeautyServiceLayout serciceLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MVCHelper<List<Shop>> mvcHelper;
    private IAsyncDataSource<List<Shop>> dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_beauty;
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra(Constants.ActivityExtra.TITLE));
        contentViewHolder = (ContentViewHolder) findViewById(R.id.content_root);
        contentViewHolder.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
                contentViewHolder.showLoading();
            }
        });
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
        ShopListRequest storeListRequest = new ShopListRequest();
        storeListRequest.setLongitude(String.valueOf(longitude));
        storeListRequest.setLatitude(String.valueOf(latitude));
        storeListRequest.setCId(sid);
        Request request = new Request(storeListRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<Shop>>(swipeRefreshLayout);
        dataSource = new ShopListDataSource(storeListRequest);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new ShopRecycleViewAdapter(this));
        // 加载数据
        mvcHelper.refresh();
        ApiFactory.getHomeSalonShops(request).subscribe(new Action1<ApiResponse<ShopListResponse>>() {
            @Override
            public void call(ApiResponse<ShopListResponse> storeListResponseApiResponse) {
                contentViewHolder.showContent();
                render(storeListResponseApiResponse.getData());
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                contentViewHolder.showRetry();
            }
        });
    }

    private void render(final ShopListResponse data) {
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
