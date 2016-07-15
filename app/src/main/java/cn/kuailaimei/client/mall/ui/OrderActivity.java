package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.utils.DateUtil;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.widget.ViewAnimator;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.pay.PayTools;

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
    private LinearLayout orderStateLayout;

    public static final int POSITION_PRICE = 0;
    public static final int POSITION_CANCLE = 1;
    private static final int LAUNCH_FOR_ORDER = 1;

    //
//    public
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
        this.orderStateLayout= (LinearLayout) findViewById(R.id.order_state_layout);

    }

    @Override
    protected void initData() {
        if (isDisplayOrder()) {
            GoodsDetail goodsDetail = getIntent().getParcelableExtra(Constants.ActivityExtra.GOODS_DETAIL);
            SkuItem choosedSku = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_SKU);
            StockItem chooseStock = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_STOCK);
            priceCancleViewAnimator.setDisplayedChild(POSITION_PRICE);
            orderStateLayout.setVisibility(ViewAnimator.GONE);
            needPayAnother.setText(Utils.formartPrice(goodsDetail.getPrice()));
            paidPrice.setText(Utils.formartPrice(goodsDetail.getPrice()));
            freight.setText(Utils.formartPrice(goodsDetail.getFare()));
            expressFreight.setText(Utils.formartPrice(goodsDetail.getFare()));
            totalPrice.setText(Utils.formartPrice(goodsDetail.getPrice()));
            paidCoupon.setText(goodsDetail.getScore());
            orderStatus.setVisibility(ViewAnimator.GONE);
            orderDate.setText(DateUtil.formart(new Date(), DateUtil.FORMAT_DATE));
        }
//        Request  request=new Request();
//
//        ApiFactory.getAddressList()

    }

    private boolean isDisplayOrder() {
        return getIntent().getIntExtra(Constants.ActivityExtra.LAUNCH_ORDER_MODE, LAUNCH_FOR_ORDER) == LAUNCH_FOR_ORDER;
    }

    public static void launchForOrder(Context context, GoodsDetail goodsDetail, SkuItem choosedSku, StockItem choosedStock) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(Constants.ActivityExtra.GOODS_DETAIL, goodsDetail);
        intent.putExtra(Constants.ActivityExtra.CHOOSE_SKU, choosedSku);
        intent.putExtra(Constants.ActivityExtra.CHOOSE_STOCK, choosedStock);
        intent.putExtra(Constants.ActivityExtra.LAUNCH_ORDER_MODE, LAUNCH_FOR_ORDER);
        context.startActivity(intent);
    }
}
