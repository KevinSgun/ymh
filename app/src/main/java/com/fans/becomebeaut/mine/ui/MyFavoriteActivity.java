package com.fans.becomebeaut.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.NearShop;
import com.fans.becomebeaut.api.request.PageRequest;
import com.fans.becomebeaut.api.request.PageRequestData;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.common.widget.MVCSwipeRefreshHelper;
import com.fans.becomebeaut.mine.adapter.MyFavoriteAdapter;
import com.fans.becomebeaut.mine.datasource.FavoriteListDataSource;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_favorite;
    }

    @Override
    protected void initView() {
        setTitle("我的收藏");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        favoritelistview = (ListView) findViewById(R.id.favorite_list_view);
    }

    @Override
    protected void initData() {
        PageRequest request = new PageRequest(new PageRequestData());
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<NearShop>>(swipeRefreshLayout);
        dataSource = new FavoriteListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new MyFavoriteAdapter(this, new ArrayList<NearShop>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static void launch(Activity act) {
        Intent intent = new Intent(act, MyFavoriteActivity.class);
        act.startActivity(intent);
    }

}
