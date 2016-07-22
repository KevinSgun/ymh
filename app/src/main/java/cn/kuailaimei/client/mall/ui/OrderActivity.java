package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
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
import cn.kuailaimei.client.api.entity.GoodsDetail;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.StockItem;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.SubmitExchangeOrderRequest;
import cn.kuailaimei.client.api.response.OrderIdResposne;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.DateUtil;
import cn.kuailaimei.client.common.utils.Utils;
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
    private TextView price;
    private RemoteImageView icon;
    private TextView name;
    private TextView oldPrice;
    private TextView needPayDesp;
    private TextView needPayAnother;
    private Button cancleOrder;
    private ViewAnimator priceCancleViewAnimator;
    private RippleButton payNow;
    private LinearLayout orderStateLayout;
    private RippleLinearLayout chooseAddressLayout;
    private RippleLinearLayout addressLayout;
    public static final int POSITION_PRICE = 0;
    public static final int POSITION_CANCLE = 1;
    private static final int LAUNCH_FOR_ORDER = 1;
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
        this.cancleOrder = (Button) findViewById(R.id.cancleOrder);
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
        this.contactSeller = (LinearLayout) findViewById(R.id.contactSeller);
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
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseAddressActivity.launchForResult(OrderActivity.this, CHOOSE_ADDRESS);
            }
        });

    }

    @Override
    protected void initData() {
        if (isDisplayOrder()) {
            setTitle("订单确认");
            logisticsStatusLayout.setVisibility(View.GONE);
            goodsDetail = getIntent().getParcelableExtra(Constants.ActivityExtra.GOODS_DETAIL);
            choosedSku = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_SKU);
            chooseStock = getIntent().getParcelableExtra(Constants.ActivityExtra.CHOOSE_STOCK);
            priceCancleViewAnimator.setDisplayedChild(POSITION_PRICE);
            orderStateLayout.setVisibility(ViewAnimator.GONE);
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
                    ApiFactory.submitExchangeOrder(new Request(request)).subscribe(new ProgressSubscriber<ApiResponse<OrderIdResposne>>(OrderActivity.this) {
                        @Override
                        protected void onNextInActive(ApiResponse<OrderIdResposne> response) {
                            String orderId = response.getData().getOrderId();
//                        PayWithExistOrderActivity
                            PayActivity.launch(getContext(),orderId,goodsDetail.getPrice()+goodsDetail.getFare());
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

    @Override
    public void onComplete(View v) {
        if (v.getId() == chooseAddressLayout.getId()) {
//            AddressActivity.launcForAdd(this);
            ChooseAddressActivity.launchForResult(this, CHOOSE_ADDRESS);

        } else if (v.getId() == addressLayout.getId()) {

            //EditAddressActivity.launcForAdd(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_ADDRESS&&resultCode==RESULT_OK){
            Address address= (Address) data.getSerializableExtra(Constants.ActivityExtra.ADDRESS);
            if(address!=null){
                setChoosedAddress(address);
            }

        }
    }
}
