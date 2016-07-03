package com.fans.becomebeaut.mine.datasource;

import com.fans.becomebeaut.api.ApiFactory;
import com.fans.becomebeaut.api.entity.ScoreBean;
import com.fans.becomebeaut.api.request.PageRequest;
import com.fans.becomebeaut.api.response.ScoreListResponse;
import com.fans.becomebeaut.common.datasource.PagedProxy;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreListDataSource  implements IAsyncDataSource<List<ScoreBean>> {
    private PagedProxy proxy = new PagedProxy(10);
    private PageRequest request;

    public ScoreListDataSource(PageRequest pageRequest) {
        super();
        this.request = pageRequest;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<ScoreBean>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<ScoreBean>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<ScoreBean>> sender, final int page) throws Exception {
        request.setCurrentPage(page);
        final Subscription subscribe = ApiFactory.getScoreList(request).subscribe(new Action1<ApiResponse<ScoreListResponse>>() {
            @Override
            public void call(ApiResponse<ScoreListResponse> scoreListResponseApiResponse) {
                ScoreListResponse data = scoreListResponseApiResponse.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<ScoreBean> items = data.getItems();
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
