package com.fans.becomebeaut.home.datasource;

import android.util.Log;

import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.Store;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.request.StoreListRequest;
import com.fans.becomebeaut.api.request.StoreSelectionRequest;
import com.fans.becomebeaut.api.response.StoreListResponse;
import com.fans.becomebeaut.common.datasource.PagedProxy;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;
import java.util.logging.Logger;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by lu on 2016/4/12.
 */
public class ShopListDataSource implements IAsyncDataSource<List<Store>> {
    private PagedProxy proxy = new PagedProxy(10);
    private Request request;

    public ShopListDataSource(StoreListRequest request) {
        super();
        StoreSelectionRequest storeSelectionRequest = new StoreSelectionRequest();
        storeSelectionRequest.setCId(request.getCId());
        storeSelectionRequest.setIndex(request.getIndex());
        storeSelectionRequest.setSize(request.getSize());
        storeSelectionRequest.setLatitude(request.getLatitude());
        storeSelectionRequest.setLongitude(request.getLongitude());
        this.request = new Request(storeSelectionRequest);
    }

    public void filterCid(String cid) {
        StoreSelectionRequest data = (StoreSelectionRequest) request.getData();
        data.setCId(cid);
    }

    public void filterPrice(String price) {
        StoreSelectionRequest data = (StoreSelectionRequest) request.getData();
        data.setBottomPrice(price);
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Store>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Store>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<Store>> sender, final int page) throws Exception {
        StoreSelectionRequest data = (StoreSelectionRequest) request.getData();
        data.setIndex(page);
        final Subscription subscribe = ApiFactory.getSeletedStores(request).subscribe(new Action1<ApiResponse<StoreListResponse>>() {
            @Override
            public void call(ApiResponse<StoreListResponse> storeListResponseApiResponse) {
                StoreListResponse data = storeListResponseApiResponse.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }else{
                    com.baidu.mapapi.common.Logger.logI("xxx","---");
                }
                List<Store> items = data.getStoreList();
                if (items == null || items.size() == 0) {
                    proxy.setReachEnd(true);
                }
                sender.sendData(items);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                sender.sendError(new Exception(throwable));
            }
        });


        return new RequestHandle() {
            @Override
            public void cancle() {
                subscribe.unsubscribe();
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }

}
