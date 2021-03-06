package cn.kuailaimei.client.mine.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.NearShop;
import cn.kuailaimei.client.api.request.PageRequest;
import cn.kuailaimei.client.api.response.MyFavoriteResponse;
import cn.kuailaimei.client.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class FavoriteListDataSource implements IAsyncDataSource<List<NearShop>> {
    private PagedProxy proxy = new PagedProxy(10);
    private PageRequest request;

    public FavoriteListDataSource(PageRequest pageRequest) {
        super();
        this.request = pageRequest;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<NearShop>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<NearShop>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<NearShop>> sender, final int page) throws Exception {
        request.setCurrentPage(page);
        final Subscription subscribe = ApiFactory.getMyFavorite(request).subscribe(new Action1<ApiResponse<MyFavoriteResponse>>() {
            @Override
            public void call(ApiResponse<MyFavoriteResponse> response) {
                MyFavoriteResponse data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<NearShop> items = data.getStoreList();
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
