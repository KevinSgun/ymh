package com.zitech.framework.widget;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.zitech.framework.R;

/**
 * SimpleDialog
 * 改为继承自DialogFragment,DialogFragment的生命周期依赖Activity的生命周期，
 * DialogFragment在Activity销毁时也会销毁
 * @author LuDaiqian
 * @author ymh
 *
 */
public class LoadingDialog extends DialogFragment {

	protected static final String TAG = "IphoneDialog";
	private OnDialogCancelListener cancelListener;
	private boolean mCancel;

	public static LoadingDialog newInstance() {
		LoadingDialog loadingDialog = new LoadingDialog();
		loadingDialog.setStyle(STYLE_NO_TITLE, 0);
		return loadingDialog;
	}

	public LoadingDialog(){
	}

	@Override
	public void onStart() {
		super.onStart();

		Window window = getDialog().getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0.0f;

		window.setAttributes(windowParams);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getDialog().setCanceledOnTouchOutside(mCancel);
		View contentView = inflater.inflate(R.layout.progress_bar, container, false);
		initView(contentView);
		return contentView;
	}

	private void initView(View contentView) {
		View loadingIcon = contentView.findViewById(R.id.progressbar);
		Animation roteAnimation = new RotateAnimation(0.0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		roteAnimation.setRepeatCount(-1);
		roteAnimation.setInterpolator(new LinearInterpolator());
		roteAnimation.setDuration(2000);

		loadingIcon.startAnimation(roteAnimation);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		if(cancelListener != null){
			cancelListener.onCancel();
		}
	}

	public void setCancelOutSideTouched(boolean cancel){
		mCancel = cancel;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		super.show(manager, tag);
	}

	public void show(FragmentManager manager) {
		super.show(manager, "loadingDialog");
	}

	public void setOnDialogCancelListener(OnDialogCancelListener cancelListener){
		this.cancelListener = cancelListener;
	}
	public interface OnDialogCancelListener{
		void onCancel();
	}
}
