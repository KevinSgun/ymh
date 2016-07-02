package com.zitech.framework.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;


public class ActionSheetHelper {

	public static ActionSheet createDialog(Context context, View view) {
		ActionSheet dialog = ActionSheet.create(context);
		dialog.setActionContentView(view, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	public static ActionSheetDialog createActionDialog(Context context, View view) {

		final ActionSheetDialog dialog = new ActionSheetDialog(context);
		dialog.getWindow().setWindowAnimations(com.zitech.framework.R.style.MenuDialogAnimation);
		dialog.setActionContentView(view, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
}
