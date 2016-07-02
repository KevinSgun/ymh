package com.zitech.framework.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


/**
 * 
 */
@SuppressLint("NewApi")
public class ActionSheetDialog extends ValidDialog {

	private Context mContext;
	private LayoutInflater mInflater;

	private Handler mHandler;
	private TranslateAnimation animation;

	private View mRootView;

	private LinearLayout mActionView;

	protected ActionSheetDialog(Context context) {
		this(context, false);
	}

	protected ActionSheetDialog(Context context, boolean isMenuMode) {
		super(context, com.zitech.framework.R.style.MenuDialogStyle);

		mContext = context;
		mInflater = LayoutInflater.from(context);
		mHandler = new Handler(Looper.getMainLooper());

		mRootView = mInflater.inflate(com.zitech.framework.R.layout.action_sheet_dialog, null);

		super.setContentView(mRootView);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

		mActionView = (LinearLayout) mRootView.findViewById(com.zitech.framework.R.id.action_dialog);
        mActionView.setOnClickListener(null);
	}

	/**
	 * 设置ActionSheet内容View
	 * 
	 * @param view
	 * @param params
	 */
	public void setActionContentView(View view, LayoutParams params) {
		if (view != null) {
			mActionView.removeAllViews();
			if (params == null) {
				params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			}
			mActionView.addView(view, params);
		}
	}

	/**
	 * 添加一个View到ActionSheet的contentViews
	 * 
	 * @param view
	 * 
	 * 
	 */
	public void addView(View view) {
        mActionView.addView(view);
	}

	@Override
	public void show() {
		super.show();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				/**
				 * 播放View动画从下升起
				 */
				animation = new TranslateAnimation(0, 0, mActionView.getHeight(), 0);
				animation.setFillEnabled(true);
				animation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
						android.R.anim.decelerate_interpolator));
				animation.setDuration(500);
				mActionView.startAnimation(animation);
			}
		}, 0);

	}

	@Override
	public void dismiss() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				animation = new TranslateAnimation(0, 0, 0, mActionView.getHeight());
				animation.setInterpolator(AnimationUtils.loadInterpolator(mContext,
						android.R.anim.decelerate_interpolator));
				animation.setDuration(200);
				animation.setFillAfter(true);
				mActionView.startAnimation(animation);

				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationEnd(Animation animation) {
						try {
							ActionSheetDialog.super.dismiss();
						} catch (Exception e) {
							// 异常不做任何处理，防止崩溃即可
						}
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationStart(Animation animation) {
					}

				});
			}
		}, 0);
	}

}
