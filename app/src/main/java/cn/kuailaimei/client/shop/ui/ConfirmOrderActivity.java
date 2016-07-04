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

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.CommitOrderInfo;
import cn.kuailaimei.client.api.request.CommitOrderRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.PayInfoResponse;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.MutilRadioGroup;
import cn.kuailaimei.client.common.widget.PayResultDialog;
import cn.kuailaimei.client.home.ui.MainActivity;
import cn.kuailaimei.client.mine.ui.MyOrderListActivity;
import cn.kuailaimei.client.pay.PayTools;
import cn.kuailaimei.client.utils.ToastMaster;

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
    private PayInfoResponse.OrderBean orderBean;

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
        wxTv = (TextView) findViewById(R.id.wx_tv);
        zfbTv = (TextView) findViewById(R.id.zfb_tv);
        vipTv = (TextView) findViewById(R.id.vip_tv);

        paytyperg.setOnCheckedChangeListener(this);
        wxpaylayout.setOnClickListener(this);
        zfbpaylayout.setOnClickListener(this);
        vippaylayout.setOnClickListener(this);
        commitbtn.setOnClickListener(this);

        payTools  = PayTools.getInstance(this);
        payTools.setOnPayResultListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                if(ViewUtils.isFastDoubleClick()) return;
                CommitOrderRequest commitOrderRequest = new CommitOrderRequest();
                commitOrderRequest.setName(commitOrderInfo.getName());
                commitOrderRequest.setAmount(commitOrderRequest.getAmount());
                commitOrderRequest.setCId(commitOrderRequest.getCId());
                commitOrderRequest.setSId(commitOrderRequest.getSId());
                commitOrderRequest.setMId(commitOrderRequest.getMId());
                commitOrderRequest.setMId1(commitOrderRequest.getMId1());
                commitOrderRequest.setPayId(payType);
                Request request = new Request(commitOrderRequest);
                request.sign();
                ApiFactory.commitShopOrder(request).subscribe(new ProgressSubscriber<ApiResponse<PayInfoResponse>>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse<PayInfoResponse> response) {
                        String payInfo = "";
                        try{
                           payInfo = response.getData().getPayInfo().getPayInfo();
                            orderBean = response.getData().getOrder();
                       }catch (NullPointerException ignored){

                       }

                        if(PayTools.WX_WAY.equals(payType)){
//                            payTools.payByWX();
                        }else if(PayTools.ZFB_WAY.equals(payType)){
                              payTools.payByZFB(payInfo);
                        }else if(VIP_WAY.equals(payType)){
                            ToastMaster.shortToast(response.getBasic().getMsg());
                            showResultDialog();
                        }
                    }
                });
                break;
        }
    }

    private void showResultDialog() {
        PayResultDialog payResultDialog = new PayResultDialog(ConfirmOrderActivity.this,orderBean);
        payResultDialog.setOnButtonClickListener(new PayResultDialog.OnButtonClickListener() {
            @Override
            public void onButtonClick(PayResultDialog.StuffType stuffType) {
                if(stuffType == PayResultDialog.StuffType.GO_MAIN_ACT){
                    MainActivity.launch(ConfirmOrderActivity.this);
                }else{
                    MyOrderListActivity.launch(ConfirmOrderActivity.this,2);
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
        ToastMaster.shortToast(message);
    }

    @Override
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId){
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
        if(commitOrderInfo!=null){
            serviceitemstv.setText(commitOrderInfo.getName());
            if(TextUtils.isEmpty(commitOrderInfo.getmId1())){
                assistantLayout.setVisibility(View.GONE);
                assistantLine.setVisibility(View.GONE);
            }else{
                assistantLayout.setVisibility(View.VISIBLE);
                assistantLine.setVisibility(View.VISIBLE);
                assistantnametv.setText(commitOrderInfo.getmId1()+"  "+commitOrderInfo.getAssistantName());
            }
            designernametv.setText(commitOrderInfo.getmId()+"  "+commitOrderInfo.getDesignName());
            consumertipstv.setText(commitOrderInfo.getContent());
            consumermoneytv.setText(String.format(getString(R.string.rmb),commitOrderInfo.getAmount()));
        }else {
            ToastMaster.shortToast("订单信息错误");
            finish();
        }
    }

    /**
     *
     * @param act
     * @param commitOrderInfo 提交訂單時需要的信息
     */
    public static void launch(Activity act, CommitOrderInfo commitOrderInfo){
        Intent intent = new Intent(act,ConfirmOrderActivity.class);
        intent.putExtra(COMMIT_ORDER_INFO,commitOrderInfo);
        act.startActivity(intent);
    }

}
