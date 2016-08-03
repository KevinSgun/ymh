package cn.kuailaimei.client.shop.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Evaluation;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.api.request.EvaluationRequest;
import cn.kuailaimei.client.api.request.ExchangeListRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.EvaluationList;
import cn.kuailaimei.client.api.response.ExchangeListResponse;
import cn.kuailaimei.client.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by lu on 2016/4/12.
 */
public class EvaluationDataSource implements IAsyncDataSource<List<Evaluation>> {
    private PagedProxy proxy = new PagedProxy(10);
    private Request request;

    public EvaluationDataSource(String shopId) {
        super();
        EvaluationRequest request = new EvaluationRequest();
        request.setSType(String.valueOf(1));
        request.setSize(proxy.getPageSize());
        request.setId(shopId);
        this.request = new Request(request);
        this.request.sign();
    }

    //  "sType": "1",////1 店铺评论 2设计师评论
    public void filterSType(int sType) {
        EvaluationRequest data = (EvaluationRequest) request.getData();
        data.setSType(String.valueOf(sType));
    }

    public interface OnDataLoadedListener {
        public void onDataLoaded(EvaluationList data);
    }

    private OnDataLoadedListener onDataLoadedListener;


    public void setOnDataLoadedListener(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Evaluation>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Evaluation>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<Evaluation>> sender, final int page) throws Exception {
        EvaluationRequest data = (EvaluationRequest) request.getData();
        data.setIndex(page);
        final Subscription subscribe = ApiFactory.getShopEvaluations(request).subscribe(new Action1<ApiResponse<EvaluationList>>() {
            @Override
            public void call(ApiResponse<EvaluationList> response) {
                EvaluationList data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                } else {
                    com.baidu.mapapi.common.Logger.logI("xxx", "---");
                }
                if (onDataLoadedListener != null) {
                    onDataLoadedListener.onDataLoaded(data);
                }
                List<Evaluation> items = data.getItems();
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
