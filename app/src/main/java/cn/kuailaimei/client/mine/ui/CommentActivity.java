package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.OrderItem;
import cn.kuailaimei.client.api.request.OrderCommentRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.common.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class CommentActivity extends AppBarActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private TextView canceltv;
    private TextView actionbartitle;
    private Button postbtn;
    private RadioButton positiverb;
    private RadioButton justsosorb;
    private RadioButton negativerb;
    private RadioGroup commentrg;
    private EditText commenttet;
    private String type = "1";
    private OrderItem orderItem;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {

        orderItem = getIntent().getParcelableExtra(Constants.ActivityExtra.ORDER);
        toolbar.setVisibility(View.GONE);

        canceltv = (TextView) findViewById(R.id.cancel_tv);
        actionbartitle = (TextView) findViewById(R.id.action_bar_title);
        postbtn = (Button) findViewById(R.id.post_btn);
        positiverb = (RadioButton) findViewById(R.id.positive_rb);
        justsosorb = (RadioButton) findViewById(R.id.just_so_so_rb);
        negativerb = (RadioButton) findViewById(R.id.negative_rb);
        commentrg = (RadioGroup) findViewById(R.id.comment_rg);
        commenttet = (EditText) findViewById(R.id.commentt_et);

        canceltv.setOnClickListener(this);
        postbtn.setOnClickListener(this);
        commentrg.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_tv:
                if(!TextUtils.isEmpty(commenttet.getText().toString())){
                    CommonDialog commonDialog = new CommonDialog(this,"确定放弃编辑评论信息吗");
                    commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            finish();
                        }
                    });
                }else{
                    finish();
                }

                break;
            case R.id.post_btn:
                OrderCommentRequest commentRequest = new OrderCommentRequest();
                commentRequest.setOrderId(orderItem.getOrderId());
                commentRequest.setSId(String.valueOf(orderItem.getsId()));
                commentRequest.setType(type);
                if(!TextUtils.isEmpty(commenttet.getText().toString())){
                    commentRequest.setContent(commenttet.getText().toString());
                }else{
                    commentRequest.setContent("好评！");
                }
                Request request = new Request(commentRequest);
                request.sign();
                ApiFactory.orderComment(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse apiResponse) {
                        ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                        EventBus.getDefault().post(new EventFactory.OrderListDataChange());
                        finish();
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.positive_rb:
                type = "1";
                break;
            case R.id.just_so_so_rb:
                type = "2";
                break;
            case R.id.negative_rb:
                type = "3";
                break;
        }
    }

    public static void launch(Activity act, OrderItem orderItem){
        Intent intent = new Intent(act,CommentActivity.class);
        intent.putExtra(Constants.ActivityExtra.ORDER,orderItem);
        act.startActivity(intent);
    }
}