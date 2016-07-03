package com.fans.becomebeaut.shop.ui;

import android.content.Context;
import android.content.Intent;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.fans.becomebeaut.home.ui.HomeFragment;

/**
 * Created by lu on 2016/6/21.
 */
public class ShopHomeActivity extends AppBarActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, ShopHomeActivity.class);
        context.startActivity(intent);
    }
}
