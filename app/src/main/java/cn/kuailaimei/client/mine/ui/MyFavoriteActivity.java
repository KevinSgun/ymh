package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.NearShop;
import cn.kuailaimei.client.api.request.PageRequest;
import cn.kuailaimei.client.api.request.PageRequestData;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SIDRequest;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.client.mine.adapter.MyFavoriteAdapter;
import cn.kuailaimei.client.mine.datasource.FavoriteListDataSource;
import cn.kuailaimei.client.shop.ui.ShopHomeActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyFavoriteActivity extends AppBarActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<NearShop>> mvcHelper;
    private FavoriteListDataSource dataSource;
    private ListView favoritelistview;
    private MyFavoriteAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_favorite;
    }

    @Override
    protected void initView() {
        setTitle("我的收藏");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        favoritelistview = (ListView) findViewById(R.id.favorite_list_view);

        favoritelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //TODO 进入店铺首页
                int index = position>=mvcHelper.getAdapter().getData().size()?mvcHelper.getAdapter().getData().size()-1:position;
                ShopHomeActivity.launch(MyFavoriteActivity.this,mvcHelper.getAdapter().getData().get(index).getId()+"");
            }
        });
        favoritelistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int index = position>=mAdapter.getData().size()?mAdapter.getData().size()-1:position;
                final NearShop item = mAdapter.getData().get(index);
                CommonDialog dialog = new CommonDialog(MyFavoriteActivity.this,"确定要删除该店铺收藏吗");
                dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        //TODO 删除店铺收藏
                        deleteFavoriteRequest(item,index);
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void deleteFavoriteRequest(NearShop item, final int index) {
        SIDRequest sidRequest = new SIDRequest();
        sidRequest.setsId(String.valueOf(item.getId()));
        Request request = new Request(sidRequest);
        request.sign();
        ApiFactory.deleteFavorite(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                mAdapter.getData().remove(index);
                mAdapter.notifyDataSetChanged();
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
            }
        });
    }

    @Override
    protected void initData() {
        PageRequest request = new PageRequest(new PageRequestData());
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<NearShop>>(swipeRefreshLayout);
        dataSource = new FavoriteListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mAdapter = new MyFavoriteAdapter(this, new ArrayList<NearShop>());
        mvcHelper.setAdapter(mAdapter);
        // 加载数据
        mvcHelper.refresh();
    }

    public static void launch(Activity act) {
        Intent intent = new Intent(act, MyFavoriteActivity.class);
        act.startActivity(intent);
    }

}
