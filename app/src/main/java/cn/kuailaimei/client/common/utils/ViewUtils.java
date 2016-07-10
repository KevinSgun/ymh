package cn.kuailaimei.client.common.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


public class ViewUtils extends com.zitech.framework.utils.ViewUtils {

	/**
	 * Get center child in X Axes
	 */
	public static View getCenterXChild(RecyclerView recyclerView) {
		int childCount = recyclerView.getChildCount();
		if (childCount > 0) {
			for (int i = 0; i < childCount; i++) {
				View child = recyclerView.getChildAt(i);
				if (isChildInCenterX(recyclerView, child)) {
					return child;
				}
			}
		}
		return null;
	}

	/**
	 * Get position of center child in X Axes
	 */
	public static int getCenterXChildPosition(RecyclerView recyclerView) {
		int childCount = recyclerView.getChildCount();
		if (childCount > 0) {
			for (int i = 0; i < childCount; i++) {
				View child = recyclerView.getChildAt(i);
				if (isChildInCenterX(recyclerView, child)) {
					return recyclerView.getChildAdapterPosition(child);
				}
			}
		}
		return childCount;
	}

	/**
	 * Get center child in Y Axes
	 */
	public static View getCenterYChild(RecyclerView recyclerView) {
		int childCount = recyclerView.getChildCount();
		if (childCount > 0) {
			for (int i = 0; i < childCount; i++) {
				View child = recyclerView.getChildAt(i);
				if (isChildInCenterY(recyclerView, child)) {
					return child;
				}
			}
		}
		return null;
	}

	/**
	 * Get position of center child in Y Axes
	 */
	public static int getCenterYChildPosition(RecyclerView recyclerView) {
		int childCount = recyclerView.getChildCount();
		if (childCount > 0) {
			for (int i = 0; i < childCount; i++) {
				View child = recyclerView.getChildAt(i);
				if (isChildInCenterY(recyclerView, child)) {
					return recyclerView.getChildAdapterPosition(child);
				}
			}
		}
		return childCount;
	}

	public static boolean isChildInCenterX(RecyclerView recyclerView, View view) {
		int childCount = recyclerView.getChildCount();
		int[] lvLocationOnScreen = new int[2];
		int[] vLocationOnScreen = new int[2];
		recyclerView.getLocationOnScreen(lvLocationOnScreen);
		int middleX = lvLocationOnScreen[0] + recyclerView.getWidth() / 2;
		if (childCount > 0) {
			view.getLocationOnScreen(vLocationOnScreen);
			if (vLocationOnScreen[0] <= middleX && vLocationOnScreen[0] + view.getWidth() >= middleX) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChildInCenterY(RecyclerView recyclerView, View view) {
		int childCount = recyclerView.getChildCount();
		int[] lvLocationOnScreen = new int[2];
		int[] vLocationOnScreen = new int[2];
		recyclerView.getLocationOnScreen(lvLocationOnScreen);
		int middleY = lvLocationOnScreen[1] + recyclerView.getHeight() / 2;
		if (childCount > 0) {
			view.getLocationOnScreen(vLocationOnScreen);
			if (vLocationOnScreen[1] <= middleY && vLocationOnScreen[1] + view.getHeight() >= middleY) {
				return true;
			}
		}
		return false;
	}


	public static boolean isTouchedView(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;  
	}  
	

}
