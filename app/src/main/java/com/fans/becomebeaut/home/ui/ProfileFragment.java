package com.fans.becomebeaut.home.ui;

import android.view.View;
import android.widget.TextView;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.common.User;
import com.fans.becomebeaut.common.ui.BaseFragment;
import com.fans.becomebeaut.common.widget.OnRippleCompleteListener;
import com.fans.becomebeaut.common.widget.RippleLinearLayout;
import com.fans.becomebeaut.login.ui.LoginActivity;
import com.fans.becomebeaut.mine.ui.ProfileInfoActivity;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

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
    private TextView refundtv;

    @Override
    protected int getContentViewId() {
        return R.layout.fragmentt_profile;
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

    private void refreshUI() {
        User user = User.get();
        if(user.notLogin()){
            nametv.setText("登录/注册");
        }else{
            avatar.setImageUri(R.mipmap.ic_avatar, user.getPortrait());
            nametv.setText(user.getNickname());
            //暂时不加显示数量，以后可能要加
//            waitpaytv.setText(String.format(getString(R.string.wait_pay),1));
//            waitcommenttv.setText(String.format(getString(R.string.wait_comment),1));
//            refundtv.setText(String.format(getString(R.string.refund),1));
        }


    }

    @Override
    public void onComplete(View v) {
        if(ViewUtils.isFastDoubleClick()) return;
        switch ( v.getId()){
            case R.id.mine_top_layout:
                //顶部点击事件，未登录就去登陆注册，已登录就编辑个人资料
                if(User.get().notLogin()){
                    LoginActivity.launch(getActivity(),false);
                }else{
                    ProfileInfoActivity.launch(getActivity());
                }
                break;
            case R.id.my_all_order_layout:
                //查看我的全部订单
                break;
            case R.id.wait_pay_layout:
                //查看待付款订单
                break;
            case R.id.wait_comment_layout:
                //查看待评论订单
                break;
            case R.id.refund_layout:
                //退款订单
                break;
            case R.id.my_coupon_layout:
                //我的美券
                break;
            case R.id.my_favorite_layout:
                //我的收藏
                break;
            case R.id.about_us_layout:
                //关于我们
                break;
            case R.id.feed_back_layout:
                //意见反馈
                break;
            case R.id.setting_layout:
                //系统设置
                break;
        }
    }
}
