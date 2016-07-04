package cn.kuailaimei.client.shop.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.RoundedCornersTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Designer;
import cn.kuailaimei.client.api.entity.DesignerService;
import cn.kuailaimei.client.api.entity.ServicesBean;
import cn.kuailaimei.client.api.request.IDRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.DesignerDetail;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.RippleButton;
import cn.kuailaimei.client.common.widget.ServiceRadioGroup;
import cn.kuailaimei.client.utils.ToastMaster;

/**
 * Created by lu on 2016/7/4.
 */
public class DesignerHomeActivity extends AppBarActivity {


    private RemoteImageView designerAvatar;
    private TextView designerName;
    private TextView designerLevel;
    private TextView designerIntro;
    private TextView schedule;
    private TextView order;
    private TextView reveiw;
    private TextView rate;
    private TextView shopName;
    private ServiceRadioGroup serviceItems;
    private RippleButton orderNow;
    private String desiginerId;
    private DesignerService choosedService;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_designer_detail;
    }

    @Override
    protected void initView() {
        setTitle("造型师详情");
        this.orderNow = (RippleButton) findViewById(R.id.order_now);
        this.serviceItems = (ServiceRadioGroup) findViewById(R.id.service_items);
        this.shopName = (TextView) findViewById(R.id.shop_name);
        this.rate = (TextView) findViewById(R.id.rate);
        this.reveiw = (TextView) findViewById(R.id.reveiw);
        this.order = (TextView) findViewById(R.id.order);
        this.schedule = (TextView) findViewById(R.id.schedule);
        this.designerIntro = (TextView) findViewById(R.id.designer_intro);
        this.designerLevel = (TextView) findViewById(R.id.designer_level);
        this.designerName = (TextView) findViewById(R.id.designer_name);
        this.designerAvatar = (RemoteImageView) findViewById(R.id.designer_avatar);
    }

    private void render(DesignerDetail detail) {
        Designer designer = detail.getDesigner();
        shopName.setText(designer.getAlias());
        rate.setText(designer.getOrderRate());
        reveiw.setText("" + designer.getCommentCount());
        schedule.setText(""+designer.getReserveCount());
        order.setText(""+designer.getOrderRate());
        rate.setText(designer.getSatisfactory());
//        designerIntro.setText();
        designerLevel.setText(designer.getSignature());
        designerName.setText(designer.getAlias());
        designerAvatar.setBitmapTransformation(new RoundedCornersTransformation(this, ViewUtils.getDimenPx(R.dimen.w10)));
        designerAvatar.setImageUri(designer.getPortrait());
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMaster.popToast(getContext(), "去订购");
            }
        });
        serviceItems.setServiceItems(detail.getServiceList());
        serviceItems.setOnServiceCheckedListener(new ServiceRadioGroup.OnServiceCheckedListener() {
            @Override
            public void onServiceChecked(DesignerService service) {
                choosedService = service;
            }
        });
    }


    @Override
    protected void initData() {
        desiginerId = getIntent().getStringExtra(Constants.ActivityExtra.ID);
        requestData();
    }

    private void requestData() {
        IDRequest id = new IDRequest();
        id.setId(desiginerId);
        Request request = new Request(id);
        ApiFactory.getDesignerDetail(request).subscribe(new ProgressSubscriber<ApiResponse<DesignerDetail>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<DesignerDetail> designerDetailApiResponse) {
                DesignerDetail detail = designerDetailApiResponse.getData();
                render(detail);
            }
        });
    }

    public static void launch(Context context, String designerId) {
        Intent intent = new Intent(context, DesignerHomeActivity.class);
        intent.putExtra(Constants.ActivityExtra.ID, designerId);
        context.startActivity(intent);
    }
}
