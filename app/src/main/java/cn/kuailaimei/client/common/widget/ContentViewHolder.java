package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import cn.kuailaimei.client.R;

public class ContentViewHolder extends ViewAnimator {

    public static final int LOADING = 0;
    public static final int RETRY = 1;
    public static final int NO_DATA = 2;
    public static final int CONTENT = 3;
    protected RippleButton retry;
    private boolean retryInNoData = false;
    protected TextView noData;
    protected TextView errorPromptView;
    protected ProgressBar progressBar;
    private static final int CHILD_SIZE = 4;
    private String noDataHint;

    public ContentViewHolder(Context context) {
        super(context);
        initView();
    }

    //    private ViewCo
    public ContentViewHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
    public void showNoData() {
        if (retryInNoData) {
            if (errorPromptView != null)
                errorPromptView.setText(noDataHint);
            setDisplayedChild(RETRY);
        } else {
            setDisplayedChild(NO_DATA);
        }
    }

    protected void initView() {
        View v = inflate(getContext(), R.layout.content_view_holder, this);
        retry = (RippleButton) v.findViewById(R.id.retry_btn);
        noData = (TextView) v.findViewById(R.id.no_data);
        errorPromptView = (TextView) findViewById(R.id.error_prompt_view);
        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
    }

    public void setContent(View content) {
        if (getChildCount() == CHILD_SIZE) {
            removeViewAt(CONTENT);
        }
        ViewGroup parent = (ViewGroup) content.getParent();
        ViewGroup.LayoutParams contentLayoutParams = content.getLayoutParams();
        int index = parent.indexOfChild(content);
        parent.removeView(content);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(content, CONTENT, params);
        parent.addView(this, index, contentLayoutParams);
    }

    protected View inflateLayout() {
        return inflate(getContext(), R.layout.content_view_holder, this);
    }

    public void showContent() {
        setDisplayedChild(CONTENT);
    }

    public void showLoading() {
        setDisplayedChild(LOADING);
    }

    public void showRetry() {
        setDisplayedChild(RETRY);
    }

    public void showEmpty() {
        setDisplayedChild(NO_DATA);
    }


    public void setRetryListener(final OnClickListener listener) {
        retry.setOnRippleCompleteListener(new OnRippleCompleteListener() {
            @Override
            public void onComplete(View view) {
                listener.onClick(view);
            }
        });
    }


    public void setDefaultEmptyImage(int resId) {
        noData.setBackgroundResource(resId);
    }

    public void setNoDataText(String noDataText) {
        noData.setText(noDataText);
    }

    public void setErrorPrompt(int resId) {
        errorPromptView.setText(resId);
    }
}
