package com.fans.becomebeaut.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RadioGroup;

import com.fans.becomebeaut.R;
import com.fans.becomebeaut.api.entity.PricesBean;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class PriceChooseLayout extends RadioGroup {
    public PriceChooseLayout(Context context) {
        super(context);
        init();
    }

    public PriceChooseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
//        setPadding();
    }

    public void setPriceList(final List<PricesBean> priceList) {
        post(new Runnable() {
            @Override
            public void run() {
                int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                int width = totalWidth / priceList.size();
                int height = getResources().getDimensionPixelOffset(R.dimen.w60);
                for (int i = 0; i < priceList.size(); i++) {
                    LayoutInflater.from(getContext()).inflate(R.layout.item_price, PriceChooseLayout.this);
                }
            }
        });


//        for(int i=0;)
//        EventFactory
//        int width=get
    }
}
