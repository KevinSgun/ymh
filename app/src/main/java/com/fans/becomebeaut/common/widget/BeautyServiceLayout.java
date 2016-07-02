package com.fans.becomebeaut.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.ServicesBean;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class BeautyServiceLayout extends RadioGroup {


    public BeautyServiceLayout(Context context) {
        super(context);
        init();
    }

    public BeautyServiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    public void setBeautyService(List<ServicesBean> servieceList) {
        int width = getResources().getDimensionPixelOffset(R.dimen.w160);
        int height = getResources().getDimensionPixelOffset(R.dimen.h90);
        for (int i = 0; i < servieceList.size(); i++) {
            View layout = LayoutInflater.from(getContext()).inflate(R.layout.item_beauty, null);
            addView(layout, new LayoutParams(width, height));
        }

    }
}
