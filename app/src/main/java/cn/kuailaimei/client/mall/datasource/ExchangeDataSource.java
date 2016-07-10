package cn.kuailaimei.client.mall.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.api.request.ExchangeListRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.ExchangeListResponse;
import cn.kuailaimei.client.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by lu on 2016/4/12.
 */
public class ExchangeDataSource implements IAsyncDataSource<List<ExchangeItem>> {
    private PagedProxy proxy = new PagedProxy(10);
    private Request request;

    public ExchangeDataSource(ExchangeListRequest request) {
        super();
        this.request = new Request(request);
        this.request.sign();
    }

    public void filterGoodsType(int goodsType) {
        ExchangeListRequest data = (ExchangeListRequest) request.getData();
        data.setGoodsType(String.valueOf(goodsType));
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<ExchangeItem>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ExchangeItem>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<ExchangeItem>> sender, final int page) throws Exception {
        ExchangeListRequest data = (ExchangeListRequest) request.getData();
        data.setIndex(page);
        final Subscription subscribe = ApiFactory.getExchangList(request).subscribe(new Action1<ApiResponse<ExchangeListResponse>>() {
            @Override
            public void call(ApiResponse<ExchangeListResponse> storeListResponseApiResponse) {
                ExchangeListResponse data = storeListResponseApiResponse.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                } else {
                    com.baidu.mapapi.common.Logger.logI("xxx", "---");
                }
                List<ExchangeItem> items = data.getItems();
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
