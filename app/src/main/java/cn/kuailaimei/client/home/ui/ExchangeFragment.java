package cn.kuailaimei.client.home.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.LoopViewPager;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class ExchangeFragment extends BaseFragment {

    private LinearLayout exchangeHistory;
    private LinearLayout userAccount;
    private LoopViewPager banner;
    private CollapsingToolbarLayout collapsingToolbar;
    private TabLayout slidingTabs;
    private ViewPager viewPager;

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        this.viewPager = (ViewPager) contentView.findViewById(R.id.viewpager);
        this.slidingTabs = (TabLayout) contentView.findViewById(R.id.sliding_tabs);
        this.collapsingToolbar = (CollapsingToolbarLayout) contentView.findViewById(R.id.collapsing_toolbar);
        this.banner = (LoopViewPager) contentView.findViewById(R.id.banner);
        this.userAccount = (LinearLayout) contentView.findViewById(R.id.user_account);
        this.exchangeHistory = (LinearLayout) contentView.findViewById(R.id.exchange_history);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_exchange;
    }
}
