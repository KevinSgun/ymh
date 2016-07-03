package com.fans.becomebeaut.mine.ui;

import android.app.Activity;
import android.content.Intent;

import com.fans.becomebeaut.Constants;
import com.fans.becomebeaut.common.ui.AppBarActivity;

/**
 * 积分即是我的美券
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreActivity extends AppBarActivity{
    private int scoreCount;

    @Override
    protected int getContentViewId() {
        return 0;
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
        Intent intent = new Intent(act,ScoreActivity.class);
        intent.putExtra(Constants.ActivityExtra.SCORE_COUNT,scoreCount);
        act.startActivity(intent);
    }
}
