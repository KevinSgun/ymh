package com.fans.becomebeaut.mine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.BaseFragment;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class ScoreFragment extends BaseFragment{
    private static final String QUERY_DATE = "query_date";
    private String queryDate;
    private ListView scorelistview;

    @Override
    protected int getContentViewId() {
        return R.layout.fagment_score;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Bundle bundle = getArguments();
        if(bundle!=null){
            queryDate = bundle.getString(QUERY_DATE);
        }

        scorelistview = (ListView) contentView.findViewById(R.id.score_list_view);
    }

    public static ScoreFragment getInstance(String queryDate) {
        Bundle bundle = new Bundle();
        bundle.putString(QUERY_DATE,queryDate);
        ScoreFragment fragment = new ScoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
