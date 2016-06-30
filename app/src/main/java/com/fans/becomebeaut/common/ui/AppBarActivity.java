package com.fans.becomebeaut.common.ui;

import android.view.View;

import com.fans.becomebeaut.common.widget.CustomToolBar;
import com.fans.becomebeaut.common.widget.ToolBarHelper;

/**
 * Created by lu on 2016/6/14.
 */
public abstract class AppBarActivity extends BaseActivity {
    public CustomToolBar toolbar;

    @Override
    protected void setContentView() {
        ToolBarHelper toolBarHelper = new ToolBarHelper(this, getContentViewId());
        toolbar = toolBarHelper.getToolBar();
        initToolBarEvent();
        setContentView(toolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
    }

    private void initToolBarEvent() {
        toolbar.setBtnLeftClick(leftOnClickListener);
        toolbar.setBtnRightClick(rightOnClickListener);
    }

    protected void setBarTitle(String title){
        toolbar.setTitle(title);
    }

    protected void setBarTitle(int titleId){
        toolbar.setTitle(titleId);
    }

    protected void setRightText(int titleId){
        toolbar.setRightText(titleId);
    }

    protected void setRightText(String title){
        toolbar.setRightText(title);
    }

    protected void setRightImg(int imgId){
        toolbar.setBtnRight(imgId);
    }

    protected void setLeftImg(int imgId){
        toolbar.setBtnLeft(imgId);
    }

    protected abstract int getContentViewId();

    protected void onActionBarItemClick(int position){
        if(position == ToolBarHelper.ITEM_LEFT)
            finish();
    }

    public View.OnClickListener leftOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_LEFT);
        }
    };

    public View.OnClickListener rightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onActionBarItemClick(ToolBarHelper.ITEM_RIGHT);
        }
    };

    @Override
    protected void onDestroy() {
        toolbar.destroyView();
        super.onDestroy();
    }
}
