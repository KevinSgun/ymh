package cn.kuailaimei.client.shop.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.CommitOrderInfo;
import cn.kuailaimei.client.api.entity.Order;
import cn.kuailaimei.client.api.entity.PayInfo;
import cn.kuailaimei.client.api.entity.PayListBean;
import cn.kuailaimei.client.api.request.CommitOrderRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.NullDataRequest;
import cn.kuailaimei.client.api.response.OrderPayListResponse;
import cn.kuailaimei.client.api.response.OrderPayResult;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.MutilRadioGroup;
import cn.kuailaimei.client.common.widget.PayResultDialog;
import cn.kuailaimei.client.home.ui.MainActivity;
import cn.kuailaimei.client.mine.ui.MyOrderListActivity;
import cn.kuailaimei.client.pay.PayTools;
import cn.kuailaimei.client.common.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class ConfirmOrderActivity extends AppBarActivity implements MutilRadioGroup.OnCheckedChangeListener, View.OnClickListener, PayTools.OnPayResultListener {
    private static final String COMMIT_ORDER_INFO = "commit_order_info";
    private TextView serviceitemstv;
    private TextView assistantnametv;
    private TextView designernametv;
    private TextView consumertipstv;
    private TextView consumermoneytv;
    private LinearLayout profilenicknamelayout;
    private RadioButton wxrb;
    private TextView wxpaytipstv;
    private LinearLayout wxpaylayout;
    private RadioButton zfbrb;
    private TextView zfbpaytipstv;
    private LinearLayout zfbpaylayout;
    private RadioButton viprb;
    private TextView vippaytipstv;
    private LinearLayout vippaylayout;
    private MutilRadioGroup paytyperg;
    private CommitOrderInfo commitOrderInfo;
    private LinearLayout assistantLayout;
    private View assistantLine;
    private String payType = "2";
    private static final String VIP_WAY = "3";
    private TextView wxTv;
    private TextView zfbTv;
    private TextView vipTv;
    private Button commitbtn;
    private PayTools payTools;
    private Order orderBean;
    private View vipTopLine;
    private View zfbTopLine;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        setTitle("确认订单");
        commitOrderInfo = getIntent().getParcelableExtra(COMMIT_ORDER_INFO);

        serviceitemstv = (TextView) findViewById(R.id.service_items_tv);
        assistantnametv = (TextView) findViewById(R.id.assistant_name_tv);
        designernametv = (TextView) findViewById(R.id.designer_name_tv);
        consumertipstv = (TextView) findViewById(R.id.consumer_tips_tv);
        consumermoneytv = (TextView) findViewById(R.id.consumer_money_tv);
        profilenicknamelayout = (LinearLayout) findViewById(R.id.profile_nickname_layout);
        wxrb = (RadioButton) findViewById(R.id.wx_rb);
        wxpaytipstv = (TextView) findViewById(R.id.wx_pay_tips_tv);
        wxpaylayout = (LinearLayout) findViewById(R.id.wx_pay_layout);
        zfbrb = (RadioButton) findViewById(R.id.zfb_rb);
        zfbpaytipstv = (TextView) findViewById(R.id.zfb_pay_tips_tv);
        zfbpaylayout = (LinearLayout) findViewById(R.id.zfb_pay_layout);
        viprb = (RadioButton) findViewById(R.id.vip_rb);
        vippaytipstv = (TextView) findViewById(R.id.vip_pay_tips_tv);
        vippaylayout = (LinearLayout) findViewById(R.id.vip_pay_layout);
        paytyperg = (MutilRadioGroup) findViewById(R.id.pay_type_rg);
        assistantLayout = (LinearLayout) findViewById(R.id.assistant_layout);
        assistantLine = findViewById(R.id.assistant_line);
        commitbtn = (Button) findViewById(R.id.commit_btn);
        vipTopLine = findViewById(R.id.vip_top_line);
        zfbTopLine = findViewById(R.id.zfb_top_line);
        wxTv = (TextView) findViewById(R.id.wx_tv);
        zfbTv = (TextView) findViewById(R.id.zfb_tv);
        vipTv = (TextView) findViewById(R.id.vip_tv);

        paytyperg.setOnCheckedChangeListener(this);
        wxpaylayout.setOnClickListener(this);
        zfbpaylayout.setOnClickListener(this);
        vippaylayout.setOnClickListener(this);
        commitbtn.setOnClickListener(this);

        payTools = PayTools.getInstance(this);
        payTools.setOnPayResultListener(this);
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
            case R.id.vip_pay_layout:
                paytyperg.check(R.id.vip_rb);
                break;
            case R.id.commit_btn:
                //提交訂單
                if (ViewUtils.isFastDoubleClick()) return;
                CommitOrderRequest commitOrderRequest = new CommitOrderRequest();
                commitOrderRequest.setName(commitOrderInfo.getName());
                commitOrderRequest.setAmount(String.valueOf(commitOrderInfo.getAmount()));
                commitOrderRequest.setCId(commitOrderInfo.getcId());
                commitOrderRequest.setSId(commitOrderInfo.getsId());
                commitOrderRequest.setMId(commitOrderInfo.getmId());
                commitOrderRequest.setMId1(commitOrderInfo.getmId1());
                commitOrderRequest.setId(commitOrderInfo.getId());
                commitOrderRequest.setPayId(payType);
                Request request = new Request(commitOrderRequest);
                request.sign();
                ApiFactory.commitShopOrder(request).subscribe(new ProgressSubscriber<ApiResponse<OrderPayResult>>(this) {

                    @Override
                    protected void onNextInActive(ApiResponse<OrderPayResult> response) {
                        PayInfo payInfo = null;
                        String payInfoStr = "";
                        try {
                            payInfo = response.getData().getPayInfo();
                            payInfoStr = payInfo.getPayInfo();
                            orderBean = response.getData().getOrder();
                        } catch (NullPointerException ignored) {

                        }

                        if (PayTools.WX_WAY.equals(payType)) {
                            payTools.payByWX(payInfo);
                        } else if (PayTools.ZFB_WAY.equals(payType)) {
                            payTools.payByZFB(payInfoStr);
                        } else if (VIP_WAY.equals(payType)) {
                            ToastMaster.shortToast(response.getBasic().getMsg());
                            showResultDialog();
                        }
                    }
                });
                break;
        }
    }

    private void showResultDialog() {
        EventBus.getDefault().post(new EventFactory.OrderListDataChange());
        PayResultDialog payResultDialog = new PayResultDialog(ConfirmOrderActivity.this, orderBean);
        payResultDialog.setOnButtonClickListener(new PayResultDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(PayResultDialog.StuffType stuffType) {
                if (stuffType == PayResultDialog.StuffType.GO_MAIN_ACT) {
                    MainActivity.launch(ConfirmOrderActivity.this);
                } else {
                    MyOrderListActivity.launch(ConfirmOrderActivity.this, 2);
                    ConfirmOrderActivity.this.finish();
                }
            }
        });
        payResultDialog.show();
    }

    @Override
    public void paySuccessfulResult(String payType, String message) {
        ToastMaster.shortToast(message);
        showResultDialog();

    }

    @Override
    public void failedPayResult(String payType, String message) {
        EventBus.getDefault().post(new EventFactory.OrderListDataChange());
        ToastMaster.shortToast(message);
    }

    @Override
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.wx_rb:
                payType = PayTools.WX_WAY;
                wxpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent_pink));
                zfbpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                vippaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));

                wxTv.setTextColor(getResources().getColor(R.color.textColorPink));
                zfbTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                vipTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                break;
            case R.id.zfb_rb:
                payType = PayTools.ZFB_WAY;
                wxpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                zfbpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent_pink));
                vippaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));

                wxTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                zfbTv.setTextColor(getResources().getColor(R.color.textColorPink));
                vipTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                break;
            case R.id.vip_rb:
                payType = VIP_WAY;
                wxpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                zfbpaylayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                vippaylayout.setBackgroundColor(getResources().getColor(R.color.transparent_pink));

                wxTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                zfbTv.setTextColor(getResources().getColor(R.color.textColorPrimary));
                vipTv.setTextColor(getResources().getColor(R.color.textColorPink));
                break;
        }
    }

    @Override
    protected void initData() {
        if (commitOrderInfo != null) {
            serviceitemstv.setText(commitOrderInfo.getName());
            if (TextUtils.isEmpty(commitOrderInfo.getmId1())) {
                assistantLayout.setVisibility(View.GONE);
                assistantLine.setVisibility(View.GONE);
            } else {
                assistantLayout.setVisibility(View.VISIBLE);
                assistantLine.setVisibility(View.VISIBLE);
                assistantnametv.setText(commitOrderInfo.getmId1() + "号  " + commitOrderInfo.getAssistantName());
            }
            designernametv.setText(commitOrderInfo.getmId() + "号  " + commitOrderInfo.getDesignName());
            consumertipstv.setText(commitOrderInfo.getContent());
            consumermoneytv.setText(String.format(getString(R.string.rmb), commitOrderInfo.getAmount()));
        } else {
            ToastMaster.shortToast("订单信息错误");
            finish();
        }

        Request request = new Request(new NullDataRequest());
        ApiFactory.getOrderPayList(request).subscribe(new ProgressSubscriber<ApiResponse<OrderPayListResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<OrderPayListResponse> response) {
                List<PayListBean> payList = response.getData().getPayList();
                refreshPayListUI(payList);
            }
        });
    }

    private void refreshPayListUI(List<PayListBean> payList) {
        String wxType = null;
        String zfbType = null;
        String vipType = null;
        for (PayListBean bean : payList) {
            if (PayTools.WX_WAY.equals(bean.getValue())) {
                wxType = PayTools.WX_WAY;
                wxpaytipstv.setText(bean.getRemark());
            }

            if (PayTools.ZFB_WAY.equals(bean.getValue())) {
                zfbType = PayTools.ZFB_WAY;
                zfbpaytipstv.setText(bean.getRemark());
            }

            if (VIP_WAY.equals(bean.getValue())) {
                vipType = VIP_WAY;
                vippaytipstv.setText(bean.getRemark());
            }

        }
        if (PayTools.WX_WAY.equals(wxType)) {
           wxpaylayout.setVisibility(View.VISIBLE);
            zfbTopLine.setVisibility(View.VISIBLE);
        }else{
            wxpaylayout.setVisibility(View.GONE);
            zfbTopLine.setVisibility(View.GONE);
        }

        if (PayTools.ZFB_WAY.equals(zfbType)) {
            zfbpaylayout.setVisibility(View.VISIBLE);
            vipTopLine.setVisibility(View.VISIBLE);
        }else{
            zfbpaylayout.setVisibility(View.GONE);
            vipTopLine.setVisibility(View.GONE);
        }

        if (VIP_WAY.equals(vipType)) {
            vippaylayout.setVisibility(View.VISIBLE);
        }else{
            vippaylayout.setVisibility(View.GONE);
            vipTopLine.setVisibility(View.GONE);
        }
    }

    /**
     * @param act
     * @param commitOrderInfo 提交訂單時需要的信息
     */
    public static void launch(Activity act, CommitOrderInfo commitOrderInfo) {
        Intent intent = new Intent(act, ConfirmOrderActivity.class);
        intent.putExtra(COMMIT_ORDER_INFO, commitOrderInfo);
        act.startActivity(intent);
    }

}
