package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Order;
import cn.kuailaimei.client.api.entity.OrderItem;
import cn.kuailaimei.client.api.request.PayRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.OrderPayResult;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.MutilRadioGroup;
import cn.kuailaimei.client.common.widget.PayResultDialog;
import cn.kuailaimei.client.home.ui.MainActivity;
import cn.kuailaimei.client.pay.PayTools;
import cn.kuailaimei.client.utils.ToastMaster;

/**
 * 为已存在的订单发起支付
 * Created by ymh on 2016/7/4 0004.
 */
public class PayWithExistOrderActivity extends AppBarActivity implements MutilRadioGroup.OnCheckedChangeListener, PayTools.OnPayResultListener, View.OnClickListener {

    private TextView payamounttv;
    private RadioButton wxrb;
    private LinearLayout wxpaylayout;
    private RadioButton zfbrb;
    private LinearLayout zfbpaylayout;
    private MutilRadioGroup paytyperg;
    private OrderItem orderItem;
    private String payType = "1";
    private PayTools payTools;
    private Button commitbtn;
    private Order orderBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        orderItem = getIntent().getParcelableExtra(Constants.ActivityExtra.ORDER);

        payamounttv = (TextView) findViewById(R.id.pay_amount_tv);
        wxrb = (RadioButton) findViewById(R.id.wx_rb);
        wxpaylayout = (LinearLayout) findViewById(R.id.wx_pay_layout);
        zfbrb = (RadioButton) findViewById(R.id.zfb_rb);
        zfbpaylayout = (LinearLayout) findViewById(R.id.zfb_pay_layout);
        paytyperg = (MutilRadioGroup) findViewById(R.id.pay_type_rg);
        commitbtn = (Button) findViewById(R.id.commit_btn);

        paytyperg.setOnCheckedChangeListener(this);
        wxpaylayout.setOnClickListener(this);
        zfbpaylayout.setOnClickListener(this);
        commitbtn.setOnClickListener(this);

        payTools = PayTools.getInstance(this);
        payTools.setOnPayResultListener(this);
    }

    @Override
    protected void initData() {
        setTitle("订单支付");
        if(orderItem!=null){
            payamounttv.setText(String.format(getString(R.string.rmb),orderItem.getAmount()));
        }else{
            ToastMaster.shortToast("订单信息错误");
            finish();
        }
    }

    public static void launch(Activity act, OrderItem orderItem){
        Intent intent = new Intent(act,PayWithExistOrderActivity.class);
        intent.putExtra(Constants.ActivityExtra.ORDER,orderItem);
        act.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.wx_rb:
                payType = PayTools.WX_WAY;
                break;
            case R.id.zfb_rb:
                payType = PayTools.ZFB_WAY;
                break;
            case R.id.commit_btn:
                PayRequest payRequest = new PayRequest();
                payRequest.setPayType(payType);
                payRequest.setPo(orderItem.getOrderId());
                Request request = new Request(payRequest);
                request.sign();
                ApiFactory.doOrderRePay(request).subscribe(new ProgressSubscriber<ApiResponse<OrderPayResult>>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse<OrderPayResult> response) {
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
                break;
        }
    }

    @Override
    public void paySuccessfulResult(String payType, String message) {
        ToastMaster.shortToast(message);
        showResultDialog();
    }

    @Override
    public void failedPayResult(String payType, String message) {
        EventFactory.OrderListDataChange orerdata = new EventFactory.OrderListDataChange();
        orerdata.status = "1";
        EventBus.getDefault().post(orerdata);
        ToastMaster.shortToast(message);
    }

    private void showResultDialog() {
        EventFactory.OrderListDataChange orerdata = new EventFactory.OrderListDataChange();
        orerdata.status = "2";
        EventBus.getDefault().post(orerdata);
        PayResultDialog payResultDialog = new PayResultDialog(PayWithExistOrderActivity.this, orderBean);
        payResultDialog.setOnButtonClickListener(new PayResultDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(PayResultDialog.StuffType stuffType) {
                if (stuffType == PayResultDialog.StuffType.GO_MAIN_ACT) {
                    MainActivity.launch(PayWithExistOrderActivity.this);
                } else {
                    MyOrderListActivity.launch(PayWithExistOrderActivity.this, 2);
                }
            }
        });
        payResultDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wx_pay_layout:
                paytyperg.check(R.id.wx_rb);
                break;
            case R.id.zfb_pay_layout:
                paytyperg.check(R.id.zfb_rb);
                break;
        }
    }
}
