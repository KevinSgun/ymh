package cn.kuailaimei.client.mall.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.widget.SlidingTabs;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Order;
import cn.kuailaimei.client.api.request.CreditPayRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.OrderPayResult;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.utils.ToastMaster;
import cn.kuailaimei.client.common.utils.Utils;
import cn.kuailaimei.client.common.widget.MutilRadioGroup;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.PayResultDialog;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.home.ui.MainActivity;
import cn.kuailaimei.client.mine.ui.MyOrderListActivity;
import cn.kuailaimei.client.pay.PayTools;

/**
 * Created by lu on 2016/7/17.
 */
public class PayActivity extends AppBarActivity implements MutilRadioGroup.OnCheckedChangeListener, PayTools.OnPayResultListener, View.OnClickListener {
    private MutilRadioGroup paytyperg;
    private RadioButton wxrb;
    private PayTools payTools;
    private RadioButton zfbrb;
    private TextView payAmount;
    private RippleButton commitBtn;
    private String orderId;
    private String payType = PayTools.ZFB_WAY;
    private LinearLayout wxpaylayout;
    private LinearLayout zfbpaylayout;
    private TextView wxTv;
    private TextView zfbTv;
    private int price;
    private Order orderBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        setTitle("订单支付");
        orderId = getIntent().getStringExtra(Constants.ActivityExtra.ORDER_ID);
         price = getIntent().getIntExtra(Constants.ActivityExtra.PRICE, 0);
        payAmount = (TextView) findViewById(R.id.pay_amount_tv);
        zfbrb = (RadioButton) findViewById(R.id.zfb_rb);
        wxrb = (RadioButton) findViewById(R.id.wx_rb);
        paytyperg = (MutilRadioGroup) findViewById(R.id.pay_type_rg);
        commitBtn = (RippleButton) findViewById(R.id.commit_btn);
        wxpaylayout = (LinearLayout) findViewById(R.id.wx_pay_layout);
        zfbpaylayout = (LinearLayout) findViewById(R.id.zfb_pay_layout);
        wxTv = (TextView) findViewById(R.id.wx_tv);
        zfbTv = (TextView) findViewById(R.id.zfb_tv);
        paytyperg.check(R.id.zfb_rb);
        paytyperg.setOnCheckedChangeListener(this);
        wxpaylayout.setOnClickListener(this);
        zfbpaylayout.setOnClickListener(this);

        payTools = PayTools.getInstance(this);
        payTools.setOnPayResultListener(this);
        paytyperg.setOnCheckedChangeListener(this);
        commitBtn.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View v) {
                CreditPayRequest req = new CreditPayRequest();
                req.setOrderId(orderId);
                req.setPayId(payType);
                ApiFactory.doCreditPay(new Request(req)).subscribe(new ProgressSubscriber<ApiResponse<OrderPayResult>>(PayActivity.this) {
                    @Override
                    protected void onNextInActive(ApiResponse<OrderPayResult> response) {
//                        ToastMaster.popToast(getContext(), "支付成功");
//                        orderPayResultApiResponse.getData().getPayInfo();
                        String payInfo = "";
                        try {
                            payInfo = response.getData().getPayInfo().getPayInfo();
                            orderBean = response.getData().getOrder();
                        } catch (NullPointerException ignored) {

                        }
                        if (PayTools.WX_WAY.equals(payType)) {
//                            payTools.payByWX();
                        } else if (PayTools.ZFB_WAY.equals(payType)) {
                            payTools.payByZFB(payInfo);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        payAmount.setText(Utils.formartPrice(price));
    }

    public static void launch(Context context, String orderId, int price) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra(Constants.ActivityExtra.ORDER_ID, orderId);
        intent.putExtra(Constants.ActivityExtra.PRICE, price);
        context.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.wx_rb:
                payType = PayTools.WX_WAY;
                wxpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent_pink));
                zfbpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));

                wxTv.setTextColor(getResources().getColor(R.color.textColorPink));
                zfbTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                break;
            case R.id.zfb_rb:
                payType = PayTools.ZFB_WAY;
                wxpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                zfbpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent_pink));
                wxTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                zfbTv.setTextColor(getResources().getColor(R.color.textColorPink));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wx_pay_layout:
                paytyperg.check(R.id.wx_rb);
                break;
            case R.id.zfb_pay_layout:
                paytyperg.check(R.id.zfb_rb);
                break;

        }
    }

    @Override
    public void paySuccessfulResult(String payType, String message) {
        ToastMaster.shortToast(message);
        showResultDialog();
    }
    private void showResultDialog() {
        EventBus.getDefault().post(new EventFactory.OrderListDataChange());
        PayResultDialog payResultDialog = new PayResultDialog(PayActivity.this, orderBean);
        payResultDialog.setOnButtonClickListener(new PayResultDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(PayResultDialog.StuffType stuffType) {
                if (stuffType == PayResultDialog.StuffType.GO_MAIN_ACT) {
                    MainActivity.launch(PayActivity.this);
                } else {
                    MyOrderListActivity.launch(PayActivity.this, 2);
                }
            }
        });
        payResultDialog.show();
    }
    @Override
    public void failedPayResult(String payType, String message) {
//        EventFactory.OrderListDataChange orerdata = new EventFactory.OrderListDataChange();
//        orerdata.status = "1";
//        EventBus.getDefault().post(orerdata);
        ToastMaster.shortToast(message);
    }
}
