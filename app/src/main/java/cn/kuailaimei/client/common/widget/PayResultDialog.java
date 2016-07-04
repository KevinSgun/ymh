package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.ValidDialog;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Order;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class PayResultDialog extends ValidDialog{

    private Order orderBean;
    private TextView couponcounttv;
    private Button backtohomebtn;
    private Button gotoorderdetailbtn;
    private OnButtonClickListener listener;

    /**
     * GO_MAIN_ACT表示回到首页
     * GO_MY_ORDER_LIST_ACT表示进入我的订单列表
     */
    public enum StuffType{
        GO_MAIN_ACT,GO_MY_ORDER_LIST_ACT
    }


    public PayResultDialog(Context context, Order orderBean) {
        super(context, R.style.CommonDialog);
        this.orderBean = orderBean;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_pay_result);
        initialize();

    }

    private void initialize() {

        couponcounttv = (TextView) findViewById(R.id.coupon_count_tv);
        backtohomebtn = (Button) findViewById(R.id.back_to_home_btn);
        gotoorderdetailbtn = (Button) findViewById(R.id.go_to_order_detail_btn);

        couponcounttv.setText(String.valueOf(orderBean.getIntegral()));

        backtohomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(listener!=null)
                    listener.onButtonClick(StuffType.GO_MAIN_ACT);
            }
        });

        gotoorderdetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(listener!=null)
                    listener.onButtonClick(StuffType.GO_MY_ORDER_LIST_ACT);
            }
        });

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewUtils.getDimenPx(R.dimen.w600);
        params.height = ViewUtils.getDimenPx(R.dimen.w649);
        getWindow().setAttributes(params);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        listener = onButtonClickListener;
    }

    public interface OnButtonClickListener{
        void onButtonClick(StuffType stuffType);
    }
}
