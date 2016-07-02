package com.zitech.framework.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ActionSheet iOS7风格 android平台实现说明:<br/> 
 * 1.由于ActionSheet iOS7风格改版，避免接口改变导致调用出错，ActionSheet组件需要保证在对旧接口调用
 * 兼容的前提下进行视觉风格替换，所以需要保留所有旧字段和方法接口。 但由于保留了调用者可以自定义view的接口，如
 * setHeaderView、setActionContentView、addView等方法，因此所有调用自定义View接口的人员需要
 * 手动修改View的视觉样式为iOS7风格。<br/>
 * 2.iOS7风格的ActionSheet内容都是白底+红色字或者蓝色字，最下是取消按钮。需要根据内容来动态设置圆角类型。 <br/>
 * 3.默认实现已支持无障碍化
 * 
 */
@SuppressLint("NewApi")
public class ActionSheet extends Dialog {

	public static final String TAG = "ActionSheet";

	
	/**
	 * 为兼容旧button样式保留的字段，iOS7按钮文字颜色风格中除RED_STYLE_BTN为白色背景红色字体外，其余均为白色背景蓝色字体。
	 */
	public static final int DEFAULT_STYLE_BTN = 0;

	/**
	 * 为兼容旧button样式保留的字段，iOS7按钮文字颜色风格中 除RED_STYLE_BTN为白色背景红色字体外，其余均为白色背景蓝色字体。
	 */
	@Deprecated
	public static final int WHITE_STYLE_BTN = 1;

	/**
	 * 为兼容旧button样式保留的字段，iOS7按钮文字颜色风格中 除RED_STYLE_BTN为白色背景红色字体外，其余均为白色背景蓝色字体。
	 */
	@Deprecated
	public static final int GREEN_STYLE_BTN = 2;

	/**
	 * 为兼容旧button样式保留的字段，iOS7按钮文字颜色风格中 除RED_STYLE_BTN为白色背景红色字体外，其余均为白色背景蓝色字体。
	 */
	@Deprecated
	public static final int GREY_STYLE_BTN = 4;
	
	/**
	 * iOS7按钮文字颜色风格中RED_STYLE_BTN为白底红色字体
	 */
	public static final int RED_STYLE_BTN = 3;

	/**
	 * iOS7按钮文字颜色风格中BLUE_STYLE_BTN为白底蓝色字体
	 */
	public static final int BLUE_STYLE_BTN = 5;
	
	
	/**
	 * Selector 圆角的样式，上下都有圆角
	 */
	public static final int SELECTOR_TYPE_SINGLE = 0;
	/**
	 * Selector 圆角的样式，上面有圆角
	 */
	public static final int SELECTOR_TYPE_TOP = 1;
	/**
	 * Selector 圆角的样式，四边没有圆角
	 */
	public static final int SELECTOR_TYPE_MIDDLE = 2;
	/**
	 * Selector 圆角的样式，底部有圆角
	 */
	public static final int SELECTOR_TYPE_BOTTOM = 3;

	private Context mContext;
	private LayoutInflater mInflater;
	private Resources mResources;

	private Handler mHandler;
	private TranslateAnimation animation;

	/**
	 * actionsheet根View
	 */
	private View mRootView;

	/**
	 * 下方actionview
	 */
	private LinearLayout mActionView;

	/**
	 * 内容view
	 */
	private LinearLayout mContentContainer;

	/**
	 * 取消Button
	 */
	private Button mCancelBtn;

	private CharSequence mMainTitle;
	private CharSequence mSecondaryTitle;
	// integer记录每个button对应的字体颜色风格
	private ArrayList<Pair<CharSequence, Integer>> mContents;
	private CharSequence mCancelText;

	/**
	 * action Button选中时的监听器
	 */
	private OnButtonClickListener mButtonsListener = null;

	private OnDismissListener mDismissListener = null;

	/**
	 * 当前选中的View id -1标识没有任何button处于checked状态
	 */
	private int mCurrentSelectedViewId = -1;

	/**
	 * 是否是RaidoGroup模式
	 */
	private boolean mRadioGroupMode = false;

	/**
	 * 记录所有添加的 radio button的id和对应的View,不包括cancelBtn
	 */
	private SparseArray<View> mRadioButtonMap;

	private int mAnimationTime = 300;
	private boolean mIsMenuMode;
	
	/**
	 * 标记ContentViews是否构造完成，保证{@link #prepareContentViews()}方法只被调用一次
	 *
	 */
	private boolean mIsReady;

	protected ActionSheet(Context context) {
		this(context, false);
	}

	protected ActionSheet(Context context, boolean isMenuMode) {
		super(context, com.zitech.framework.R.style.MenuDialogStyle);

		mContext = context;
		mIsMenuMode = isMenuMode;
		mInflater = LayoutInflater.from(context);
		mResources = context.getResources();
		mHandler = new Handler(Looper.getMainLooper());

		mRootView = mInflater.inflate(com.zitech.framework.R.layout.action_sheet_base, null);

		super.setContentView(mRootView);

		mActionView = (LinearLayout) mRootView
				.findViewById(com.zitech.framework.R.id.action_sheet_actionView);
		mContentContainer = (LinearLayout) mRootView
				.findViewById(com.zitech.framework.R.id.action_sheet_contentView);

		if (mIsMenuMode) {
			mRootView.setOnClickListener(mDefaultDismissListener);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mIsMenuMode) {
			try {
				dismiss();
			} catch (Exception e) {
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * 原Dialog方法,已无作用
	 * 
	 * @deprecated
	 */
	@Override
	public void setContentView(View view) {
		throw new UnsupportedOperationException("this method is not support");
	}

	/**
	 * 原Dialog方法,已无作用.
	 * 
	 * @deprecated
	 */
	@Override
	public void setContentView(int layoutResID) {
		throw new UnsupportedOperationException("this method is not support");
	}

	/**
	 * 原Dialog方法,已无作用
	 * 
	 * @deprecated
	 */
	@Override
	public void setTitle(int resId) {
		throw new UnsupportedOperationException("this method is not support");
	}

	/**
	 * 原Dialog方法,已无作用
	 * 
	 * @deprecated
	 */
	@Override
	public void setTitle(CharSequence title) {
		throw new UnsupportedOperationException("this method is not support");
	}

	/**
	 * 设置ActionSheet头部view,如果只是添加主标题和副标题，请调用{@link #setMainTitle(CharSequence)}{@link #setSecondaryTitle(CharSequence)}
	 * 
	 * @param view
	 * @param params
	 */
	@Deprecated
	public void setHeaderView(View view, LayoutParams params) {
		if (view != null) {
			if (params == null) {
				params = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			}
			mContentContainer.addView(view, 0, params);
		}
	}

	/**
	 * 设置标题
	 *
	 * @param text
	 */
	public void setMainTitle(int resid) {
		CharSequence text = mResources.getText(resid);
		setMainTitle(text);
	}

	/**
	 * 设置主标题
	 *
	 * @param text
	 */
	public void setMainTitle(CharSequence text) {
		if (text != null) {
			mMainTitle = text;
		}
	}

	/**
	 * 设置副标题
	 *
	 * @param text
	 */
	public void setSecondaryTitle(int resid) {
		CharSequence text = mResources.getText(resid);
		setSecondaryTitle(text);
	}

	/**
	 * 设置副标题
	 *
	 * @param text
	 */
	public void setSecondaryTitle(CharSequence text) {
		if (text != null) {
			mSecondaryTitle = text;
		}
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
				params = new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			}
			mActionView.addView(view, params);
		}
	}

	/**
	 * 添加缺省风格为白色背景蓝色字体的Button到ActionSheet，注意此方法不能和addRadioButton方法混用
	 * 
	 * @param text
	 *            显示的文字
	 */
	public void addButton(int resId) {
		CharSequence text = mResources.getText(resId);
		addButton(text, DEFAULT_STYLE_BTN);
	}

	/**
	 * 添加缺省风格为白色背景蓝色字体的Button到ActionSheet，注意此方法不能和addRadioButton方法混用
	 * 
	 * @param text
	 *            显示的文字
	 * @param styleType
	 *            按钮文字颜色风格
	 */
	public void addButton(int id, int styleType) {
		CharSequence text = mResources.getText(id);
		addButton(text, styleType);
	}

	/**
	 * 添加缺省风格为白色背景蓝色字体的Button到ActionSheet，注意此方法不能和addRadioButton方法混用
	 * 
	 * @param text
	 */
	public void addButton(CharSequence text) {
		addButton(text, DEFAULT_STYLE_BTN);
	}

	/**
	 * 添加指定字体颜色风格的Button到ActionSheet，注意此方法不能和addRadioButton方法混用
	 * 
	 * @param text
	 *            显示的文字
	 * @param styleType
	 *            按钮字体颜色风格，如RED_STYLE_BTN或BLUE_SYLE_BTN
	 */
	public void addButton(CharSequence text, int btnStyleType) {
		if (text != null) {
			if (mContents == null) {
				mContents = new ArrayList<Pair<CharSequence, Integer>>();
			}
			Pair<CharSequence, Integer> pair = new Pair<CharSequence, Integer>(
					text, btnStyleType);
			if (!mContents.contains(pair)) {
				mContents.add(pair);
			}

			// 单选模式下，不能调用addButton
			if (mRadioGroupMode) {
				throw new UnsupportedOperationException(
						"ActionSheet is in radio group mode,shouldn't call addButton!");
			}
		}
	}

	public String getContent(int index) {
		if (mContents != null && index < mContents.size()) {
			Pair<CharSequence, Integer> pair = mContents.get(index);
			if (pair != null)
				return pair.first.toString();
		}
		return null;
	}

	/**
	 * 添加一个View到ActionSheet的contentViews
	 * 
	 * @param view
	 * 
	 * 
	 */
	public void addView(View view) {
		mContentContainer.addView(view);
	}

	/**
	 * 添加取消Button到ActionSheet，字体颜色默认为蓝色，ActionSheet只有一个cancel button，多次添加只会保留最后一个Cancel Button.
	 * 
	 * @param resid
	 */
	public void addCancelButton(int resid) {
		CharSequence text = mResources.getText(resid);
		addCancelButton(text);
	}

	/**
	 * 添加取消Button到ActionSheet，字体颜色默认为蓝色，ActionSheet只有一个cancel button,多次添加只会保留最后一个Cancel Button.
	 * 
	 * @param text
	 */
	public void addCancelButton(CharSequence text) {
		if (text != null) {
			mCancelText = text;
		}
	}

	/**
	 * 根据SelectorType返回制定类型的selector
	 * 
	 * @param selectorType selector type
	 * @return 圆角背景资源
	 */
	public Drawable getSelectorByType(int selectorType) {
		switch (selectorType) {
		case SELECTOR_TYPE_SINGLE:
			return mResources.getDrawable(com.zitech.framework.R.drawable.actionsheet_single);
		case SELECTOR_TYPE_TOP:
			return mResources.getDrawable(com.zitech.framework.R.drawable.actionsheet_top);
		case SELECTOR_TYPE_MIDDLE:
			return mResources.getDrawable(com.zitech.framework.R.drawable.actionsheet_middle);
		case SELECTOR_TYPE_BOTTOM:
			return mResources.getDrawable(com.zitech.framework.R.drawable.actionsheet_bottom);
		default:
			return mResources.getDrawable(com.zitech.framework.R.drawable.actionsheet_top);
		}
	}

	/**
	 * 根据Button的type返回Button字体颜色的 color资源id，缺省为蓝色字体
	 * 
	 * @param type
	 * @return Button字体的 color资源id
	 */
	private int getActionButtonColorByType(int type) {
		switch (type) {
		case DEFAULT_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		case WHITE_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		case GREEN_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		case GREY_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		case RED_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_red);
		case BLUE_STYLE_BTN:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		default:
			return mResources.getColor(com.zitech.framework.R.color.action_sheet_button_blue);
		}
	}

	/**
	 * 添加缺省风格为白色背景蓝色字体的单选Button，注意此方法不能和addButton方法混用
	 * 
	 * @param text
	 * @param isChecked
	 *            是否选中
	 */
	public void addRadioButton(int resid, boolean isChecked) {
		addRadioButton(resid, DEFAULT_STYLE_BTN, isChecked);
	}

	/**
	 * 添加缺省风格为白色背景蓝色字体的单选Button，注意此方法不能和addButton方法混用
	 * 
	 * @param text
	 * @param isChecked
	 *            是否选中
	 */
	public void addRadioButton(CharSequence text, boolean isChecked) {
		addRadioButton(text, DEFAULT_STYLE_BTN, isChecked);
	}

	/**
	 * 添加指定风格的Button到ActionSheet，注意此方法不能和addButton方法混用
	 * 
	 * @param resid
	 *            要显示的文字资源id
	 * @param btnStyleType
	 *            Button字体颜色风格如RED_STYLE_BTN或BLUE_STYLE_BTN
	 * @param isChecked
	 *            是否选中
	 */
	public void addRadioButton(int resid, int btnStyleType, boolean isChecked) {
		CharSequence text = mResources.getText(resid);
		addRadioButton(text, btnStyleType, isChecked);
	}

	/**
	 * 添加Action Button到ActionSheet，注意此方法不能和addButton方法混用
	 * 
	 * @param text
	 *            显示的文字
	 * @param btnStyleType
	 *            Button字体颜色风格如RED_STYLE_BTN或BLUE_STYLE_BTN
	 * @param isChecked
	 *            是否为选中按钮
	 */
	public void addRadioButton(CharSequence text, int btnStyleType,
			boolean isChecked) {

		if (text != null) {
			// normal Button 模式下，不能调用addRadioButton
			if (!mRadioGroupMode && (mContents != null) && (mContents.size() > 0)) {
				throw new UnsupportedOperationException(
						"ActionSheet is in normal button mode,shouldn't call addRadioButton!");
			}

			if (mContents == null) {
				mContents = new ArrayList<Pair<CharSequence, Integer>>();
			}
			Pair<CharSequence, Integer> pair = new Pair<CharSequence, Integer>(
					text, btnStyleType);
			if (!mContents.contains(pair)) {
				mContents.add(pair);
			}

			if (isChecked) {
				mCurrentSelectedViewId = mContents.size() - 1;
			}
			mRadioGroupMode = true;
		}
	}

	public void setRadioButtonChecked(int index){
		if (index >= 0 && (mContents != null) && (index < mContents.size())) {
			mCurrentSelectedViewId = index;
			
			if (mRadioButtonMap != null) {
				View v = mRadioButtonMap.get(mCurrentSelectedViewId);
				if (v != null) {
					v.findViewById(com.zitech.framework.R.id.action_sheet_checkedIcon)
					.setVisibility(View.VISIBLE);
				}
			} 
		}
	}
	
	/**
	 * 根据是否设置mainTitle和content子项的数目来初始化ContentViews，需要根据具体添加的内容来动态设定圆角背景
	 * ContentViews包括三部分：title(mainTitle, secondaryTitle), content items(action button or radio button), cancel
	 * Button.
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void prepareContentViews() {
		if (mIsReady) {
			return;
		}
		
		int count = 0;

		// 1.初始化title
		if (mMainTitle != null) {
			count += 1;

			View titleContainer = mInflater.inflate(
					com.zitech.framework.R.layout.action_sheet_title, null);
			titleContainer.setBackgroundDrawable(getSelectorByType(SELECTOR_TYPE_TOP));
			
			TextView mainTitleText = (TextView) titleContainer
					.findViewById(com.zitech.framework.R.id.action_sheet_title);
			mainTitleText.setVisibility(View.VISIBLE);
			mainTitleText.setText(mMainTitle);
			mainTitleText.setContentDescription(mMainTitle);

			if (mSecondaryTitle != null) {
				TextView secondaryTitleText = (TextView) titleContainer
						.findViewById(com.zitech.framework.R.id.action_sheet_secondary_title);
				secondaryTitleText.setVisibility(View.VISIBLE);
				secondaryTitleText.setText(mSecondaryTitle);
				secondaryTitleText.setContentDescription(mSecondaryTitle);
			}

			mContentContainer.addView(titleContainer, 0);
		}

		// 2.初始化contents，Action button或者Radio button
		if (mContents != null) {
			count += mContents.size();

			Drawable selector;
			final int buttonCount = mContents.size();

			for (int i = 0; i < buttonCount; i++) {
				View child = mInflater.inflate(
						com.zitech.framework.R.layout.action_sheet_common_button, null);
				TextView text = (TextView) child
						.findViewById(com.zitech.framework.R.id.action_sheet_button);

				Pair<CharSequence, Integer> pair = mContents.get(i);

				text.setText(pair.first);
				text.setContentDescription(pair.first);
				// 根据button的type分别设置字体颜色
				text.setTextColor(getActionButtonColorByType(pair.second));
				if ((i == 0) && (count == buttonCount) && (count == 1)) {
					selector = getSelectorByType(SELECTOR_TYPE_SINGLE);
				} else if ((i == 0) && (count == buttonCount) && (count > 1)){
					selector = getSelectorByType(SELECTOR_TYPE_TOP);
				} else if ((i == buttonCount - 1) && (count > 1)) {
					selector = getSelectorByType(SELECTOR_TYPE_BOTTOM);
				} else {
					selector = getSelectorByType(SELECTOR_TYPE_MIDDLE);
				}
				child.setBackgroundDrawable(selector);
				//显示设置View的id
				child.setId(i);
				child.setOnClickListener(mBtnClickListener);
				mContentContainer.addView(child);

				if (mRadioGroupMode) {
					//添加单选按钮到mRadioButtonMap
					if (mRadioButtonMap == null) {
						mRadioButtonMap = new SparseArray<View>();
					}
					mRadioButtonMap.append(i, child);

					//显示勾选icon
					if (i == mCurrentSelectedViewId) {
						child.findViewById(com.zitech.framework.R.id.action_sheet_checkedIcon)
								.setVisibility(View.VISIBLE);
					}
				}
			}
		}

		// 3.在尾部添加Cancel Button
		if (mCancelText != null) {
			View child = mInflater.inflate(com.zitech.framework.R.layout.action_sheet_cancel_button,
					null);
			Button cancelBtn = (Button) child
					.findViewById(com.zitech.framework.R.id.action_sheet_btnCancel);
			cancelBtn.setOnClickListener(mDefaultDismissListener);
			cancelBtn.setText(mCancelText);
			cancelBtn.setContentDescription(mCancelText);
			mContentContainer.addView(child);
		}
		
		//标记ContentViews构造完成
		mIsReady = true;
	}

	@Override
	public void show() {
		super.show();

		prepareContentViews();

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				/**
				 * 播放View动画从下升起
				 */
				animation = new TranslateAnimation(0, 0, mActionView
						.getHeight(), 0);
				animation.setFillEnabled(true);
				animation.setInterpolator(AnimationUtils.loadInterpolator(
						mContext, android.R.anim.decelerate_interpolator));
				animation.setDuration(mAnimationTime);
				mActionView.startAnimation(animation);
			}
		}, 0);

	}

	@Override
	public void dismiss() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				animation = new TranslateAnimation(0, 0, 0, mActionView
						.getHeight());
				animation.setInterpolator(AnimationUtils.loadInterpolator(
						mContext, android.R.anim.decelerate_interpolator));
				animation.setDuration(200);
				animation.setFillAfter(true);
				mActionView.startAnimation(animation);

				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationEnd(Animation animation) {
						try {
							ActionSheet.super.dismiss();
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

	/**
	 * 不使用动画调用dismiss()方法
	 * 
	 * @author spacedeng
	 * 
	 */
	public void superDismiss() {
		try {
			super.dismiss();
		} catch (Exception exception) {
			// 避免Crash
		}
	}

	/**
	 * AcitonSheet消失监听器
	 */
	public interface OnDismissListener {
		/**
		 * ActionSheet消失时调用
		 */
		public void onDismiss();
	}

	/**
	 * @author wilyli
	 */
	public interface OnButtonClickListener {

		/**
		 * 已经选中的View被点击的回调
		 * 
		 * @param clickedView
		 * @param which id从0,1,2..开始,根据不同的id设置响应事件
		 */
		public void OnClick(View clickedView, int which);
	}

	/**
	 * 对话框按钮点击事件监听
	 */
	private View.OnClickListener mBtnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			int id = v.getId();

			/**
			 * 如果包含有radio group样式的button 则处理checked逻辑
			 */
			if (mRadioGroupMode) {
				if ((mCurrentSelectedViewId != -1)
						&& (id != mCurrentSelectedViewId)) {
					// 隐藏老的勾选按钮
					View oldCheckedBtn = mRadioButtonMap
							.get(mCurrentSelectedViewId);
					oldCheckedBtn.findViewById(com.zitech.framework.R.id.action_sheet_checkedIcon)
							.setVisibility(View.GONE);

					// 显示点击按钮的勾选
					View currentCheckedBtn = mRadioButtonMap.get(id);
					currentCheckedBtn.findViewById(com.zitech.framework.R.id.action_sheet_checkedIcon)
							.setVisibility(View.VISIBLE);

					mCurrentSelectedViewId = id;
				}
			}

			/**
			 * 回调click事件
			 */
			if (mButtonsListener != null) {
				mButtonsListener.OnClick(v, id);
			}
		}
	};

	/**
	 * 默认的关闭对话框监听器
	 */
	private View.OnClickListener mDefaultDismissListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			dismiss();
			if (mDismissListener != null) {
				mDismissListener.onDismiss();
			}
		}
	};

	/**
	 * 设置已经选中的View又被点击的监听器
	 * 
	 * @param lis
	 */
	public void setOnButtonClickListener(OnButtonClickListener lis) {
		mButtonsListener = lis;
	}

	/**
	 * 设置"取消"按钮被点击的监听器
	 * 
	 */
	public void setOnDismissListener(OnDismissListener lis) {
		mDismissListener = lis;
	}

	public static ActionSheet create(Context context) {
		final ActionSheet dialog = new ActionSheet(context);
		dialog.getWindow().setWindowAnimations(com.zitech.framework.R.style.MenuDialogAnimation);
		return dialog;
	}

	public static ActionSheet createMenuSheet(Context context) {
		final ActionSheet dialog = new ActionSheet(context, true);
		dialog.getWindow().setWindowAnimations(com.zitech.framework.R.style.MenuDialogAnimation);
		return dialog;
	}

	public void setAnimationTime(int time) {
		if (time > 0) {
			mAnimationTime = time;
		}
	}

}
