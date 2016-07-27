package cn.kuailaimei.client.mall.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.RemoteImageView;

import java.util.Date;
import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Address;
import cn.kuailaimei.client.api.entity.CreditOrderDetail;
import cn.kuailaimei.client.api.entity.CreditOrderDetailInfo;
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.GoodsItem;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;
import cn.kuailaimei.client.api.request.OrderIDRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SubmitExchangeOrderRequest;
import cn.kuailaimei.client.api.response.CreditOrderDetailResponse;
import cn.kuailaimei.client.api.response.OrderPayResult;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.DateUtil;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.common.widget.ViewAnimator;
import cn.kuailaimei.client.mine.ui.PayWithExistOrderActivity;

/**
 * Created by lu on 2016/7/12.
 */
public class OrderActivity extends AppBarActivity implements OnRippleCompleteListener {
    private static final int CHOOSE_ADDRESS = 0x600;
    private TextView logisticsStatus;
    private TextView logisticsCompany;
    private TextView logisticsId;
    private RippleLinearLayout contactSeller;
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
    private TextView price;
    private RemoteImageView icon;
    private TextView name;
    private TextView oldPrice;
    private TextView needPayDesp;
    private TextView needPayAnother;
    private RippleButton cancleOrder;
    private ViewAnimator priceCancleViewAnimator;
    private RippleButton payNow;
    private LinearLayout orderStateLayout;
    private RippleLinearLayout chooseAddressLayout;
    private RippleLinearLayout addressLayout;
    public static final int POSITION_PRICE = 0;
    public static final int POSITION_CANCLE = 1;
    private static final int LAUNCH_FOR_ORDER = 1;
    private static final int LAUNCH_FOR_DETAIL = 2;
    private View logisticsStatusLayout;
    private Address choosedAddress;
    private GoodsDetail goodsDetail;
    private SkuItem choosedSku;
    private StockItem chooseStock;

    //
//    public
    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        this.logisticsStatusLayout = findViewById(R.id.logisticsStatusLayout);
        this.payNow = (RippleButton) findViewById(R.id.payNow);
        this.priceCancleViewAnimator = (ViewAnimator) findViewById(R.id.priceCancleViewAnimator);
        this.cancleOrder = (RippleButton) findViewById(R.id.cancleOrder);
        this.needPayDesp = (TextView) findViewById(R.id.needPay_desp);
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
        this.contactSeller = (RippleLinearLayout) findViewById(R.id.contactSeller);
        this.logisticsId = (TextView) findViewById(R.id.logisticsId);
        this.logisticsCompany = (TextView) findViewById(R.id.logisticsCompany);
        this.logisticsStatus = (TextView) findViewById(R.id.logisticsStatus);
        this.orderStateLayout = (LinearLayout) findViewById(R.id.order_state_layout);
        chooseAddressLayout = (RippleLinearLayout) findViewById(R.id.chooseAddress);
        addressLayout = (RippleLinearLayout) findViewById(R.id.address_layout);

        chooseAddressLayout.setOnRippleCompleteListener(this);
        addressLayout.setOnRippleCompleteListener(this);
        price = (TextView) findViewById(R.id.price);
        icon = (RemoteImageView) findViewById(R.id.icon);
        name = (TextView) findViewById(R.id.name);
        oldPrice = (TextView) findViewById(R.id.old_price);
//        addressLayout

    }

    @Override
    protected void initData() {
        logisticsStatusLayout.setVisibility(View.GONE);
        if (isDisplayOrder()) {
            setTitle("订单确认");

            goodsDetail = getIntent().getParcelableExtra(Constants.ActivityExtra.GOODS_DETAIL);
            choosedSku = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_SKU);
            chooseStock = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_STOCK);
            priceCancleViewAnimator.setDisplayedChild(POSITION_PRICE);
            orderStateLayout.setVisibility(View.GONE);
            if (goodsDetail.getPrice() == 0 && goodsDetail.getFare() == 0) {
                needPayDesp.setVisibility(View.INVISIBLE);
                needPayAnother.setVisibility(View.INVISIBLE);
            } else {
                needPayAnother.setText(Utils.formartPrice(goodsDetail.getPrice() + goodsDetail.getFare()));
            }
            if (goodsDetail.getPrice() > 0) {
                paidPrice.setText(goodsDetail.getScore() + "美券" + "+" + Utils.formartPrice(goodsDetail.getPrice()));
            } else {
                paidPrice.setText(goodsDetail.getScore() + "美券");
            }
            freight.setText(Utils.formartPrice(goodsDetail.getFare()));
            expressFreight.setText(Utils.formartPrice(goodsDetail.getFare()));
            totalPrice.setText(Utils.formartPrice(goodsDetail.getPrice()));
            paidCoupon.setText(String.valueOf(goodsDetail.getScore()));
            orderStatus.setVisibility(ViewAnimator.GONE);
            orderDate.setText(DateUtil.formart(new Date(), DateUtil.FORMAT_DATE));
            if (goodsDetail.getPrice() > 0) {
                price.setText(goodsDetail.getScore() + "美券" + "+" + Utils.formartPrice(goodsDetail.getPrice()));
            } else {
                price.setText(goodsDetail.getScore() + "美券");
            }
            if (goodsDetail.getPhotos().size() > 0) {
                icon.setImageUri(goodsDetail.getPhotos().get(0));
            }
            name.setText(goodsDetail.getName());
            payNow.setOnRippleCompleteListener(new OnRippleCompleteListener() {
                @Override
                public void onComplete(View v) {
                    SubmitExchangeOrderRequest request = new SubmitExchangeOrderRequest();
                    request.setAddressId(String.valueOf(choosedAddress.getId()));
                    request.setAmount(goodsDetail.getFare() + goodsDetail.getPrice());
                    request.setGId(String.valueOf(goodsDetail.getId()));
                    request.setGoodPrice(goodsDetail.getPrice());
                    request.setName(goodsDetail.getName());
                    request.setScore(goodsDetail.getScore());
                    request.setStockId(String.valueOf(chooseStock.getId()));
                    ApiFactory.submitExchangeOrder(new Request(request)).subscribe(new ProgressSubscriber<ApiResponse<OrderPayResult>>(OrderActivity.this) {
                        @Override
                        protected void onNextInActive(ApiResponse<OrderPayResult> response) {
                            String orderId = response.getData().getOrder().getOrderId();
//                        PayWithExistOrderActivity
                            PayActivity.launch(getContext(), orderId, goodsDetail.getPrice() + goodsDetail.getFare());
                        }
                    });
                }
            });
        } else {
            setTitle("订单详情");
            final String orderId = getIntent().getStringExtra(Constants.ActivityExtra.ORDER_ID);
            OrderIDRequest request = new OrderIDRequest();
            request.setOrderId(orderId);
            ApiFactory.getOrderDetail(new Request(request)).subscribe(new ProgressSubscriber<ApiResponse<CreditOrderDetailResponse>>(this) {
                @Override
                protected void onNextInActive(ApiResponse<CreditOrderDetailResponse> response) {
                    CreditOrderDetailResponse data = response.getData();
                    //地址
                    final Address address = data.getAddress();
                    //
                    final GoodsItem item = data.getGoods();
                    final CreditOrderDetail order = data.getOrder();
                    final CreditOrderDetailInfo info = data.getInfoBean();
                    if(info!=null&"1".equals(order.getStatus())){
                        logisticsStatusLayout.setVisibility(View.VISIBLE);
                        logisticsStatus.setText(info.getLogisticsStatus());
                        logisticsCompany.setText(info.getExpressName());
                        logisticsId.setText(info.getTno());
                        contactSeller.setOnRippleCompleteListener(new OnRippleCompleteListener() {
                            @Override
                            public void onComplete(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + info.getPhone()));
                                startActivity(intent);
                            }
                        });

                    }

                    priceCancleViewAnimator.setDisplayedChild(POSITION_CANCLE);
                    orderStateLayout.setVisibility(View.GONE);
                    if (item.getPrice() == 0 && order.getFare() == 0) {
                        needPayDesp.setVisibility(View.INVISIBLE);
                        needPayAnother.setVisibility(View.INVISIBLE);
                    } else {
                        needPayAnother.setText(Utils.formartPrice(item.getPrice() + order.getFare()));
                    }
                    if (item.getPrice() > 0) {
                        paidPrice.setText(item.getScore() + "美券" + "+" + Utils.formartPrice(item.getPrice()));
                    } else {
                        paidPrice.setText(item.getScore() + "美券");
                    }
                    setChoosedAddress(address);
                    freight.setText(Utils.formartPrice(order.getFare()));
                    expressFreight.setText(Utils.formartPrice(order.getFare()));
                    totalPrice.setText(Utils.formartPrice(item.getPrice()));
                    paidCoupon.setText(String.valueOf(item.getScore()));
                    orderStatus.setVisibility(ViewAnimator.GONE);
                    orderDate.setText(order.getDate());
                    if (item.getPrice() > 0) {
                        price.setText(item.getScore() + "美券" + "+" + Utils.formartPrice(item.getPrice()));
                    } else {
                        price.setText(item.getScore() + "美券");
                    }
                    icon.setImageUri(item.getCover());
                    name.setText(item.getName());
//                    if(order)
                    cancleOrder.setOnRippleCompleteListener(new OnRippleCompleteListener() {
                        @Override
                        public void onComplete(View v) {
                            CommonDialog dialog=new CommonDialog(OrderActivity.this,"确认取消订单？");
                            dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    OrderIDRequest orderIDRequest=new OrderIDRequest();
                                    orderIDRequest.setOrderId(orderId);
                                    ApiFactory.cancelOrder(new Request(orderIDRequest)).subscribe(new ProgressSubscriber<ApiResponse>(OrderActivity.this) {
                                        @Override
                                        protected void onNextInActive(ApiResponse apiResponse) {
                                            ToastMaster.popToast(getContext(),"取消订单成功");
                                            finish();
                                        }
                                    });
                                }
                            });

                        }
                    });
                    payNow.setOnRippleCompleteListener(new OnRippleCompleteListener() {
                        @Override
                        public void onComplete(View v) {
                            PayActivity.launch(getContext(), orderId, item.getPrice() + order.getFare());
                        }
                    });
                }
            });


        }

//

    }

    @Override
    protected void onResume() {
        super.onResume();
        Request request = new Request(null);
        ApiFactory.getAddressList(request).subscribe(new ProgressSubscriber<ApiResponse<List<Address>>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<List<Address>> listApiResponse) {
                List<Address> addressList = listApiResponse.getData();
//                for()
                if (addressList != null && addressList.size() > 0) {
                    addressViewAnimator.setDisplayedChild(1);
                    Address address = addressList.get(0);
                    for (int i = 0; i < addressList.size(); i++) {
                        if (addressList.get(i).getStatus() == 1) {
                            address = addressList.get(i);
                            break;
                        }
                    }
                    setChoosedAddress(address);
                } else {
                    addressViewAnimator.setDisplayedChild(0);
                }

//                addressViewAnimator
            }
        });
    }

    private void setChoosedAddress(Address address) {
        choosedAddress = address;
        receiverName.setText(choosedAddress.getContact());
        receiverPhone.setText(choosedAddress.getPhone());
        receiverAddress.setText(choosedAddress.getCityname() + choosedAddress.getAddress());
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

    public static void launchForComplatePayment(Context context, String orderId) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(Constants.ActivityExtra.ORDER_ID, orderId);
        intent.putExtra(Constants.ActivityExtra.LAUNCH_ORDER_MODE, LAUNCH_FOR_DETAIL);
        context.startActivity(intent);
    }

    @Override
    public void onComplete(View v) {
        if (v.getId() == chooseAddressLayout.getId()) {
//            AddressActivity.launcForAdd(this);
            ChooseAddressActivity.launchForResult(this, CHOOSE_ADDRESS);

        } else if (v.getId() == addressLayout.getId()) {
            ChooseAddressActivity.launchForResult(OrderActivity.this, CHOOSE_ADDRESS);
            //EditAddressActivity.launcForAdd(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_ADDRESS && resultCode == RESULT_OK) {
            Address address = (Address) data.getExtras().getParcelable(Constants.ActivityExtra.ADDRESS);
            //data.putExtra(Constants.ActivityExtra.ADDRESS, address);
            if (address != null) {
                setChoosedAddress(address);
            }

        }
    }
}
