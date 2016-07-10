package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lu on 2016/7/10.
 */
public class ViewAnimator extends FrameLayout {

    public ViewAnimator(Context context) {
        super(context);
    }

    public ViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChild(int whichChild) {
        for (int i = 0; i < getChildCount(); i++) {
            if (i != whichChild) {
                getChildAt(i).setVisibility(GONE);
            }else {
                getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
        invalidate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setDisplayedChild(0);
    }
}
