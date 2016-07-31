package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Sku;
import cn.kuailaimei.client.api.entity.SkuItem;
import cn.kuailaimei.client.api.entity.Stock;
import cn.kuailaimei.client.common.utils.ViewUtils;

/**
 * o
 * Created by lu on 2016/7/10.
 */
public class ChooseSpecLayout extends LinearLayout {
    private OnStockChoosedListener onStockChoosedListener;
    private Sku currentSku;
    private SparseArray<SkuItem> choosedSkus;
    private HashMap<String, Stock> stocks;

    public interface OnStockChoosedListener {
        void onStockChoosed(Stock item);
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
        setOrientation(VERTICAL);
        choosedSkus = new SparseArray<>();
        stocks = new HashMap<>();
//        setOnCheckedChangeListener(new MutilRadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
//                View v = findViewById(checkedId);
//                int positon = checkedId;
//                Sku item = stockItems.get(positon);
//                if (item != currentSku) {
//                    currentSku = item;
//                    if (onSkuCheckChangedListener != null)
//                        onSkuCheckChangedListener.onSkuChanged(item);
//                }
//                Logger.logI("tag", "check:" + checkedId);
//            }
//        });
    }

    public void setSkuItems(final List<Sku> skus, final List<Stock> stocks) {
        if (skus == null)
            return;
        for (int i = 0; i < stocks.size(); i++) {

            String code = stocks.get(i).getCode();
            if (TextUtils.isEmpty(code)) {
                return;
            }
            String[] skuKeys = code.split(",");
            Arrays.sort(skuKeys);
            StringBuilder buffer = new StringBuilder();
            for (int j = 0; j < skuKeys.length; j++) {
                buffer.append(skuKeys[j]);
                if (j != skuKeys.length - 1) {
                    buffer.append(",");
                }
            }
            this.stocks.put(buffer.toString(), stocks.get(i));
        }
        for (int i = 0; i < skus.size(); i++) {
            LinearLayout skuLayout = new LinearLayout(getContext());
            skuLayout.setOrientation(LinearLayout.VERTICAL);
            int padding = ViewUtils.getDimenPx(R.dimen.w30);
//            skuLayout.setPadding(padding, padding, padding, padding);
            TextView tv = new TextView(getContext());
            tv.setText(skus.get(i).getName());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ViewUtils.getDimenPx(R.dimen.w28));
            tv.setTextColor(getResources().getColor(R.color.textColorPrimary));
            tv.setPadding(padding, 0, padding, 0);
            skuLayout.addView(tv);
            SpecItemLayout specItemLayout = new SpecItemLayout(getContext());
            final int position = i;
            specItemLayout.setOnSkuCheckChangedListener(new SpecItemLayout.OnSkuCheckChangedListener() {
                @Override
                public void onSkuChanged(SkuItem item) {
                    choosedSkus.put(position, item);
                    if (choosedSkus.size() == skus.size()) {
                        String[] ids = new String[choosedSkus.size()];
                        for (int i = 0; i < choosedSkus.size(); i++) {
                            SkuItem skuItem = choosedSkus.valueAt(i);
                            ids[i] = skus.get(choosedSkus.keyAt(i)).getId() + ":" + skuItem.getId();
                        }
                        Arrays.sort(ids);
                        StringBuilder buffer = new StringBuilder();

                        for (int i = 0; i < ids.length; i++) {
                            buffer.append(ids[i]);
                            if (i != ids.length - 1) {
                                buffer.append(",");
                            }
                        }
                        Stock stock = ChooseSpecLayout.this.stocks.get(buffer.toString());
                        if (onStockChoosedListener != null) {
                            onStockChoosedListener.onStockChoosed(stock);
                        }

                    }
                }
            });
            specItemLayout.setSkuItems(skus.get(i).getItems());
            LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            skuLayout.addView(specItemLayout, params);
            addView(skuLayout, params);
        }
    }


    public void setOnStockChoosedListener(OnStockChoosedListener onStockChoosedListener) {
        this.onStockChoosedListener = onStockChoosedListener;
    }
}
