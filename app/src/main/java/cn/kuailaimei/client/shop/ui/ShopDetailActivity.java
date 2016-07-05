package cn.kuailaimei.client.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ShopInfo;
import cn.kuailaimei.client.common.ui.AppBarActivity;

import com.zitech.framework.widget.RemoteImageView;

/**
 * Created by lu on 2016/6/17.
 */
public class ShopDetailActivity extends AppBarActivity {

    private RemoteImageView storeImage;
    private TextView shopName;
    private TextView shopHours;
    private TextView shopAddress;
    private TextView shopPhone;
    private TextView shopIntro;
    private ShopInfo shopInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initView() {
        setTitle("店铺详情");
        storeImage = (RemoteImageView) findViewById(R.id.img_store);
        shopIntro = (TextView) findViewById(R.id.tv_shop_intro);
        shopPhone = (TextView) findViewById(R.id.tv_shop_phone);
        shopAddress = (TextView) findViewById(R.id.tv_shop_address);
        shopHours = (TextView) findViewById(R.id.tv_shop_hours);
        shopName = (TextView) findViewById(R.id.tv_shop_name);
    }

    @Override
    protected void initData() {
        shopInfo = getIntent().getParcelableExtra(Constants.ActivityExtra.SHOP_INFO);
        storeImage.setImageUri(shopInfo.getIcon());
        shopIntro.setText(shopInfo.getRemark());
        shopPhone.setText(shopInfo.getPhone());
        shopAddress.setText(shopInfo.getAddress());
        shopHours.setText(shopInfo.getStart() + "-" + shopInfo.getExpired());
        shopName.setText(shopInfo.getName());
    }

    public static void launch(Context context, ShopInfo shopInfo) {
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra(Constants.ActivityExtra.SHOP_INFO, shopInfo);
    }
}
