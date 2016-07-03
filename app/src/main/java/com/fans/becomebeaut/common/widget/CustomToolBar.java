package com.fans.becomebeaut.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fans.becomebeaut.R;

/**
 * Created by lu on 2016/6/14.
 */
public class CustomToolBar extends Toolbar {
    private Context mContext;
    private ImageView actionleftimg;
    private FrameLayout actionbarleft;
    private TextView actionbartitle;
    private TextView actionrighttxt;
    private ImageView actionrightimg;
    private LinearLayout actionbarright;
    private FrameLayout flbg;

    public CustomToolBar(Context context)
    {
        super(context);
        this.mContext = context;
        initView();

    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.incloud_toolbar, this);
        actionleftimg = (ImageView) view.findViewById(R.id.action_left_img);
        actionbarleft = (FrameLayout) view.findViewById(R.id.action_bar_left);
        actionbartitle = (TextView) view.findViewById(R.id.action_bar_title);
        actionrighttxt = (TextView) view.findViewById(R.id.action_right_txt);
        actionrightimg = (ImageView) view.findViewById(R.id.action_right_img);
        actionbarright = (LinearLayout) view.findViewById(R.id.action_bar_right);

    }

    public void setBtnLeft(int icon) {
        actionbarleft.setVisibility(View.VISIBLE);
        actionleftimg.setImageResource(icon);
    }

    public void setBtnLeftClick(OnClickListener listener) {
        actionbarleft.setOnClickListener(listener);
    }

    public void setBtnRightClick(OnClickListener listener) {
        actionbarright.setOnClickListener(listener);
    }

    /**
     * 设置背景颜色
     */
    public void setBgColor(int color)
    {
        flbg.setBackgroundColor(color);
    }

    /**
     * @param left  左边按钮显示状态
     */
    public void setLeftVisible(int left)
    {
        actionbarleft.setVisibility(left);
    }

    /**
     * @param right  右边按钮显示状态
     */
    public void setRightVisible(int right)
    {
        actionbarright.setVisibility(right);
    }

    public void setTitleColor(int color)
    {
        actionbartitle.setTextColor(color);
    }

    public void setTitleText(int txtStrId)
    {
        actionbartitle.setText(getResources().getString(txtStrId));
    }

    public void setTitleText(CharSequence txtStr)
    {
        actionbartitle.setText(txtStr);
    }

    public void setRightText(String txtStr)
    {
        actionbarright.setVisibility(VISIBLE);
        actionrighttxt.setText(txtStr);
    }

    public void setRightText(int txtStrId)
    {
        actionbarright.setVisibility(VISIBLE);
        actionrighttxt.setText(getResources().getString(txtStrId));
    }

    public void setBtnRight(int icon) {
        actionbarright.setVisibility(View.VISIBLE);
        actionrightimg.setVisibility(View.VISIBLE);
        actionrightimg.setImageResource(icon);
    }


    @SuppressLint("NewApi")
    public void destroyView()
    {
        if (actionbartitle != null)
        {
            actionbartitle.setText(null);
        }
        if (actionrighttxt != null)
        {
            actionrighttxt.setText(null);
        }
        if (actionbarleft != null)
        {
            actionbarleft.setBackground(null);
        }

        if (actionleftimg != null)
        {
            actionleftimg.setImageDrawable(null);
        }

        if (actionrightimg != null)
        {
            actionrightimg.setImageDrawable(null);
        }
    }
}
