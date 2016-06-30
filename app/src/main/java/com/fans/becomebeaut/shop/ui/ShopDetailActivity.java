package com.fans.becomebeaut.store.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.ui.AppBarActivity;
import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by lu on 2016/6/17.
 */
public class ShopDetailActivity extends AppBarActivity {

    private RemoteImageView storeImage;
    private TextView shopName;
    private TextView tvshopname;
    private TextView shopHours;
    private TextView shopAddress;
    private TextView shopPhone;
    private TextView shopIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        storeImage = (RemoteImageView) findViewById(R.id.img_store);
        shopIntro = (TextView) findViewById(R.id.tv_shop_intro);
        shopPhone = (TextView) findViewById(R.id.tv_shop_phone);
        shopAddress = (TextView) findViewById(R.id.tv_shop_address);
        shopHours = (TextView) findViewById(R.id.tv_shop_hours);
        shopName = (TextView) findViewById(R.id.tv_shop_name);
    }

    @Override
    protected void initData() {

    }
}
