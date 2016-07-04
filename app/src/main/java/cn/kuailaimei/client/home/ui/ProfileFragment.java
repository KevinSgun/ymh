package cn.kuailaimei.client.home.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.response.NullDataRequest;
import cn.kuailaimei.client.api.response.UserHomeInfoResponse;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.event.EventFactory;
import cn.kuailaimei.client.common.ui.BaseFragment;
import cn.kuailaimei.client.common.widget.OnRippleCompleteListener;
import cn.kuailaimei.client.common.widget.RippleLinearLayout;
import cn.kuailaimei.client.login.ui.LoginActivity;
import cn.kuailaimei.client.mine.ui.AboutUsActivity;
import cn.kuailaimei.client.mine.ui.FeedBackActivity;
import cn.kuailaimei.client.mine.ui.MyFavoriteActivity;
import cn.kuailaimei.client.mine.ui.MyOrderListActivity;
import cn.kuailaimei.client.mine.ui.MyScoreActivity;
import cn.kuailaimei.client.mine.ui.ProfileInfoActivity;
import cn.kuailaimei.client.mine.ui.SettingActivity;

/**
 * Created by lu on 2016/6/17.
 */
public class ProfileFragment extends BaseFragment implements OnRippleCompleteListener {
    private RemoteImageView avatar;
    private RippleLinearLayout minetoplayout;
    private RippleLinearLayout myallorderlayout;
    private RippleLinearLayout waitpaylayout;
    private RippleLinearLayout waitcommentlayout;
    private RippleLinearLayout refundlayout;
    private RippleLinearLayout mycouponlayout;
    private RippleLinearLayout myfavoritelayout;
    private RippleLinearLayout aboutuslayout;
    private RippleLinearLayout feedbacklayout;
    private RippleLinearLayout settinglayout;
    private TextView nametv;
    private TextView waitpaytv;
    private TextView waitcommenttv;
    private TextView refundtv;//已完成
    private TextView couponcoutntv;
    private int scoreCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragmentt_profile;
    }

    @Subscribe
    public void onMainThreadUserInfoNotify(EventFactory.UserDataChange data) {
        refreshUI();
    }

    @Subscribe
    public void onMainThreadOrderInfo(EventFactory.OrderCountDataChange data) {
        requestOrderInfo();
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        avatar = (RemoteImageView) contentView.findViewById(R.id.avatar);
        minetoplayout = (RippleLinearLayout) contentView.findViewById(R.id.mine_top_layout);
        myallorderlayout = (RippleLinearLayout) contentView.findViewById(R.id.my_all_order_layout);
        waitpaylayout = (RippleLinearLayout) contentView.findViewById(R.id.wait_pay_layout);
        waitcommentlayout = (RippleLinearLayout) contentView.findViewById(R.id.wait_comment_layout);
        refundlayout = (RippleLinearLayout) contentView.findViewById(R.id.refund_layout);
        mycouponlayout = (RippleLinearLayout) contentView.findViewById(R.id.my_coupon_layout);
        myfavoritelayout = (RippleLinearLayout) contentView.findViewById(R.id.my_favorite_layout);
        aboutuslayout = (RippleLinearLayout) contentView.findViewById(R.id.about_us_layout);
        feedbacklayout = (RippleLinearLayout) contentView.findViewById(R.id.feed_back_layout);
        settinglayout = (RippleLinearLayout) contentView.findViewById(R.id.setting_layout);
        nametv = (TextView) contentView.findViewById(R.id.name_tv);
        waitpaytv = (TextView) contentView.findViewById(R.id.wait_pay_tv);
        waitcommenttv = (TextView) contentView.findViewById(R.id.wait_comment_tv);
        refundtv = (TextView) contentView.findViewById(R.id.refund_tv);
        couponcoutntv = (TextView) contentView.findViewById(R.id.coupon_coutn_tv);
        initEvent();
        refreshUI();
    }

    private void initEvent() {
        minetoplayout.setOnRippleCompleteListener(this);

        myallorderlayout.setOnRippleCompleteListener(this);

        waitpaylayout.setOnRippleCompleteListener(this);
        waitcommentlayout.setOnRippleCompleteListener(this);
        refundlayout.setOnRippleCompleteListener(this);

        mycouponlayout.setOnRippleCompleteListener(this);
        myfavoritelayout.setOnRippleCompleteListener(this);
        aboutuslayout.setOnRippleCompleteListener(this);
        feedbacklayout.setOnRippleCompleteListener(this);
        settinglayout.setOnRippleCompleteListener(this);
    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        requestOrderInfo();
    }

    private void requestOrderInfo() {
        Request request = new Request(new NullDataRequest());
        ApiFactory.getVipUserHome(request).subscribe(new ProgressSubscriber<ApiResponse<UserHomeInfoResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<UserHomeInfoResponse> userHomeInfoResponseApiResponse) {
                UserHomeInfoResponse homeInfoResponse = userHomeInfoResponseApiResponse.getData();
                if (homeInfoResponse != null) {
                    if (homeInfoResponse.getNoPay() > 0)
                        waitpaytv.setText(String.format(getString(R.string.wait_pay), homeInfoResponse.getNoPay()));
                    if (homeInfoResponse.getWaitComment() > 0)
                        waitcommenttv.setText(String.format(getString(R.string.wait_comment), homeInfoResponse.getWaitComment()));
                    if (homeInfoResponse.getCompleted() > 0)
                        refundtv.setText(String.format(getString(R.string.refund), homeInfoResponse.getCompleted()));
                    scoreCount = homeInfoResponse.getIntegral();
                    if (scoreCount > 0) {
                        couponcoutntv.setText(String.valueOf(scoreCount));
                    }
                }
            }
        });
    }

    private void refreshUI() {
        User user = User.get();
        if (user.notLogin()) {
            nametv.setText("登录/注册");
        } else {
            avatar.setBitmapTransformation(new CropCircleTransformation(getActivity()));
            avatar.setImageUri(R.mipmap.ic_avatar, user.getPortrait());
            nametv.setText(user.getNickname());

        }


    }

    @Override
    public void onComplete(View v) {
        if (ViewUtils.isFastDoubleClick()) return;
        switch (v.getId()) {
            case R.id.mine_top_layout:
                //顶部点击事件，未登录就去登陆注册，已登录就编辑个人资料
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    ProfileInfoActivity.launch(getActivity());
                }
                break;
            case R.id.my_all_order_layout:
                //查看我的全部订单
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyOrderListActivity.launch(getActivity(), 0);
                }
                break;
            case R.id.wait_pay_layout:
                //查看待付款订单
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyOrderListActivity.launch(getActivity(), 1);
                }
                break;
            case R.id.wait_comment_layout:
                //查看待评论订单
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyOrderListActivity.launch(getActivity(), 2);
                }
                break;
            case R.id.refund_layout:
                //已完成订单
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyOrderListActivity.launch(getActivity(), 3);
                }
                break;
            case R.id.my_coupon_layout:
                //我的美券
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyScoreActivity.launch(getActivity(), scoreCount);
                }
                break;
            case R.id.my_favorite_layout:
                //我的收藏
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    MyFavoriteActivity.launch(getActivity());
                }
                break;
            case R.id.about_us_layout:
                //关于我们
                AboutUsActivity.launch(getActivity());
                break;
            case R.id.feed_back_layout:
                //意见反馈
                if (User.get().notLogin()) {
                    LoginActivity.launch(getActivity(), false);
                } else {
                    FeedBackActivity.launch(getActivity());
                }
                break;
            case R.id.setting_layout:
                //系统设置
                SettingActivity.launch(getActivity());
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
