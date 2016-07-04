package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import com.zitech.framework.widget.SlidingTabs;
import com.zitech.framework.widget.TabsFragmentAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class MyOrderListActivity extends AppBarActivity{
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    private List<Fragment> fragments;
    private TabsFragmentAdapter adapter;
    private int status;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected void initView() {
        setTitle("我的订单");
        status = getIntent().getIntExtra(Constants.ActivityExtra.STATUS,0);
        slidingtabs = (SlidingTabs) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        //0全部 1待付款 2待评价 3 已完成
        String[] titles = { "全部", "待付款", "待评价","已完成"};

        this.fragments = new LinkedList<>();
        OrderListFragment fragment0 = OrderListFragment.getInstance("0");
        OrderListFragment fragment1 = OrderListFragment.getInstance("1");
        OrderListFragment fragment2 = OrderListFragment.getInstance("2");
        OrderListFragment fragment3 = OrderListFragment.getInstance("3");
        this.fragments.add(fragment0);
        this.fragments.add(fragment1);
        this.fragments.add(fragment2);
        this.fragments.add(fragment3);

        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), titles,
                this.fragments);
        this.pager.setAdapter(this.adapter);
        this.slidingtabs.setViewPager(this.pager);
        pager.setCurrentItem(status);
    }

    @Override
    protected void initData() {

    }

    /**
     *
     * @param act
     * @param status 订单类型
     */
    public static void launch(Activity act, int status){
        Intent intent = new Intent(act,MyOrderListActivity.class);
        intent.putExtra(Constants.ActivityExtra.STATUS,status);
        act.startActivity(intent);
    }
}
