package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.PricesBean;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class PriceChooseLayout extends RadioGroup {
    private List<PricesBean> priceList;
    private PricesBean currentChecked;
    private OnPriceCheckChangedListener onPriceCheckChangedListener;

    public interface OnPriceCheckChangedListener {
        void onPriceChecked(PricesBean pricesBean);
    }

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
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                PricesBean pricesBean = priceList.get(checkedId);
                if (currentChecked != pricesBean) {
                    currentChecked = pricesBean;
                    if (onPriceCheckChangedListener != null) {
                        onPriceCheckChangedListener.onPriceChecked(pricesBean);
                    }
                }

            }
        });
//        setPadding();
    }

    public void setPriceList(final List<PricesBean> priceList) {
        if (priceList == null)
            return;
        this.priceList = priceList;
        post(new Runnable() {
            @Override
            public void run() {
                int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                int width = totalWidth / priceList.size();
                int height = getResources().getDimensionPixelOffset(R.dimen.w60);
                for (int i = 0; i < priceList.size(); i++) {
                    RadioButton child = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_price, null);
                    child.setId(i);
                    if (i == 0) {
                        child.setBackgroundResource(R.drawable.sl_price_left);
                    } else if (i == priceList.size() - 1) {
                        child.setBackgroundResource(R.drawable.sl_price_right);
                    }
                    child.setText(priceList.get(i).getName());
                    addView(child, new LayoutParams(width, height));
                }
            }
        });


//        for(int i=0;)
//        EventFactory
//        int width=get
    }

    public void setOnPriceCheckChangedListener(OnPriceCheckChangedListener onPriceCheckChangedListener) {
        this.onPriceCheckChangedListener = onPriceCheckChangedListener;
    }

}
