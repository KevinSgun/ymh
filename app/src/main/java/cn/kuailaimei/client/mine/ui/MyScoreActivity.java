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
 * 积分即是我的美券
 * Created by ymh on 2016/7/3 0003.
 */
public class MyScoreActivity extends AppBarActivity{
    private int scoreCount;
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    List<Fragment> fragments;
    private TabsFragmentAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_score;
    }

    @Override
    protected void initView() {
        scoreCount = getIntent().getIntExtra(Constants.ActivityExtra.SCORE_COUNT,0);
        String title;
        if(scoreCount>0)
            title = "我的美券（"+scoreCount+")";
        else
            title = "我的美券";
        setTitle(title);

        slidingtabs = (SlidingTabs) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        String[] titles = { "7天", "30天", "全部"};

        this.fragments = new LinkedList<>();
        ScoreListFragment fragment1 = ScoreListFragment.getInstance("7");
        ScoreListFragment fragment2 = ScoreListFragment.getInstance("30");
        ScoreListFragment fragment3 = ScoreListFragment.getInstance("");
        this.fragments.add(fragment1);
        this.fragments.add(fragment2);
        this.fragments.add(fragment3);

        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), titles,
                this.fragments);
        this.pager.setAdapter(this.adapter);
        this.slidingtabs.setViewPager(this.pager);

    }

    @Override
    protected void initData() {

    }

    /**
     *
     * @param act
     * @param scoreCount 积分数量即是美券数量
     */
    public static void launch(Activity act,int scoreCount){
        Intent intent = new Intent(act,MyScoreActivity.class);
        intent.putExtra(Constants.ActivityExtra.SCORE_COUNT,scoreCount);
        act.startActivity(intent);
    }

}
