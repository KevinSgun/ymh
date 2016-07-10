package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.baidu.mapapi.common.Logger;

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.common.utils.ViewUtils;

/**
 * o
 * Created by lu on 2016/7/10.
 */
public class ChooseSpecLayout extends MutilRadioGroup {
    private List<SkuItem> stockItems;
    private OnSkuCheckChangedListener onSkuCheckChangedListener;
    private SkuItem currentSku;

    public interface OnSkuCheckChangedListener {
        void onSkuChanged(SkuItem item);
    }

    public ChooseSpecLayout(Context context) {
        super(context);
        init();
    }

    public ChooseSpecLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOnCheckedChangeListener(new MutilRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
                View v = findViewById(checkedId);
                int positon = checkedId;
                SkuItem item = stockItems.get(positon);
                if (item != currentSku) {
                    currentSku = item;
                    if (onSkuCheckChangedListener != null)
                        onSkuCheckChangedListener.onSkuChanged(item);
                }
                Logger.logI("tag", "check:" + checkedId);
            }
        });
    }

    public void setSkuItems(List<SkuItem> items) {
        if (items == null)
            return;
        stockItems = items;
        int rowNum = 5;
        int row = (items.size() + rowNum - 1) / rowNum;
        for (int i = 0; i < row; i++) {

            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setGravity(Gravity.CENTER_VERTICAL);
            LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.h120));
            rowLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.w30), 0, getResources().getDimensionPixelOffset(R.dimen.w30), 0);
            int childNum = Math.min(rowNum, items.size() - rowNum * i);
            for (int j = 0; j < childNum; j++) {
                int index = i * rowNum + j;
                RadioButton btn = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_spec, null);
                btn.setPadding(ViewUtils.getDimenPx(R.dimen.w20),0,ViewUtils.getDimenPx(R.dimen.w20),0);
                btn.setId(index);
                btn.setText(items.get(index).getName());
                LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewUtils.getDimenPx(R.dimen.w52));
                if (j != 0) {
                    childParams.setMargins(ViewUtils.getDimenPx(R.dimen.w25), 0, 0, 0);
                }
                rowLayout.addView(btn, childParams);
            }
            addView(rowLayout, params);
        }
    }

    public void setOnSkuCheckChangedListener(OnSkuCheckChangedListener onSkuCheckChangedListener) {
        this.onSkuCheckChangedListener = onSkuCheckChangedListener;
    }
}
