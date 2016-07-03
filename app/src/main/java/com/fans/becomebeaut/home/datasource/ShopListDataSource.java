package com.fans.becomebeaut.home.datasource;

import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.Shop;
import com.fans.becomebeaut.api.request.Request;
import com.fans.becomebeaut.api.request.ShopListRequest;
import com.fans.becomebeaut.api.request.ShopSelectionRequest;
import com.fans.becomebeaut.api.response.ShopListResponse;
import com.fans.becomebeaut.common.datasource.PagedProxy;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by lu on 2016/4/12.
 */
public class ShopListDataSource implements IAsyncDataSource<List<Shop>> {
    private PagedProxy proxy = new PagedProxy(10);
    private Request request;

    public ShopListDataSource(ShopListRequest request) {
        super();
        ShopSelectionRequest storeSelectionRequest = new ShopSelectionRequest();
        storeSelectionRequest.setCId(request.getCId());
        storeSelectionRequest.setIndex(request.getIndex());
        storeSelectionRequest.setSize(request.getSize());
        storeSelectionRequest.setLatitude(request.getLatitude());
        storeSelectionRequest.setLongitude(request.getLongitude());
        this.request = new Request(storeSelectionRequest);
    }

    public void filterCid(String cid) {
        ShopSelectionRequest data = (ShopSelectionRequest) request.getData();
        data.setCId(cid);
    }

    public void filterPrice(String price) {
        ShopSelectionRequest data = (ShopSelectionRequest) request.getData();
        data.setBottomPrice(price);
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Shop>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Shop>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<Shop>> sender, final int page) throws Exception {
        ShopSelectionRequest data = (ShopSelectionRequest) request.getData();
        data.setIndex(page);
        final Subscription subscribe = ApiFactory.getSeletedShops(request).subscribe(new Action1<ApiResponse<ShopListResponse>>() {
            @Override
            public void call(ApiResponse<ShopListResponse> storeListResponseApiResponse) {
                ShopListResponse data = storeListResponseApiResponse.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }else{
                    com.baidu.mapapi.common.Logger.logI("xxx","---");
                }
                List<Shop> items = data.getStoreList();
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
