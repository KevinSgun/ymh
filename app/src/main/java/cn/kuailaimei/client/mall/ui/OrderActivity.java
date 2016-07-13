package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;
import cn.kuailaimei.client.common.widget.ViewAnimator;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.AppBarActivity;

/**
 * Created by lu on 2016/7/12.
 */
public class OrderActivity extends AppBarActivity {
    private TextView logisticsStatus;
    private TextView logisticsCompany;
    private TextView logisticsId;
    private LinearLayout contactSeller;
    private TextView receiverName;
    private TextView receiverPhone;
    private TextView receiverAddress;
    private LinearLayout chooseAddress;
    private ViewAnimator addressViewAnimator;
    private TextView orderId;
    private TextView orderDate;
    private TextView orderStatus;
    private TextView expressFreight;
    private TextView paidCoupon;
    private TextView totalPrice;
    private TextView freight;
    private TextView couponDiscount;
    private TextView paidPrice;
    private TextView needPayAnother;
    private Button cancleOrder;
    private ViewAnimator priceCancleViewAnimator;
    private Button payNow;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        this.payNow = (Button) findViewById(R.id.payNow);
        this.priceCancleViewAnimator = (ViewAnimator) findViewById(R.id.priceCancleViewAnimator);
        this.cancleOrder = (Button) findViewById(R.id.cancleOrder);
        this.needPayAnother = (TextView) findViewById(R.id.needPayAnother);
        this.paidPrice = (TextView) findViewById(R.id.paidPrice);
        this.couponDiscount = (TextView) findViewById(R.id.couponDiscount);
        this.freight = (TextView) findViewById(R.id.freight);
        this.totalPrice = (TextView) findViewById(R.id.totalPrice);
        this.paidCoupon = (TextView) findViewById(R.id.paidCoupon);
        this.expressFreight = (TextView) findViewById(R.id.expressFreight);
        this.orderStatus = (TextView) findViewById(R.id.orderStatus);
        this.orderDate = (TextView) findViewById(R.id.orderDate);
        this.orderId = (TextView) findViewById(R.id.orderId);
        this.addressViewAnimator = (ViewAnimator) findViewById(R.id.addressViewAnimator);
        this.chooseAddress = (LinearLayout) findViewById(R.id.chooseAddress);
        this.receiverAddress = (TextView) findViewById(R.id.receiverAddress);
        this.receiverPhone = (TextView) findViewById(R.id.receiverPhone);
        this.receiverName = (TextView) findViewById(R.id.receiverName);
        this.contactSeller = (LinearLayout) findViewById(R.id.contactSeller);
        this.logisticsId = (TextView) findViewById(R.id.logisticsId);
        this.logisticsCompany = (TextView) findViewById(R.id.logisticsCompany);
        this.logisticsStatus = (TextView) findViewById(R.id.logisticsStatus);
    }

    @Override
    protected void initData() {
        GoodsDetail goodsDetail = getIntent().getParcelableExtra(Constants.ActivityExtra.GOODS_DETAIL);
        SkuItem choosedSku = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_SKU);
        StockItem chooseStock = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_STOCK);


    }

    public static void launchForOrder(Context context, GoodsDetail goodsDetail, SkuItem choosedSku, StockItem choosedStock) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(Constants.ActivityExtra.GOODS_DETAIL, goodsDetail);
        intent.putExtra(Constants.ActivityExtra.CHOOSE_SKU, choosedSku);
        intent.putExtra(Constants.ActivityExtra.CHOOSE_STOCK, choosedStock);
        context.startActivity(intent);
    }
}
