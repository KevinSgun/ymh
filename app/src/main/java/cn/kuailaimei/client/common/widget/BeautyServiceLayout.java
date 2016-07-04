package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.ServicesBean;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class BeautyServiceLayout extends RadioGroup {

    private OnBeautyCheckChangeListener onBeautyCheckChangeListener;
    private List<ServicesBean> servieceList;
    private ServicesBean currentService;
    private RecyclerView storeList;

    public interface OnBeautyCheckChangeListener {
         void onBeautyCheckChanged(ServicesBean servicesBean);
    }

    public BeautyServiceLayout(Context context) {
        super(context);
        init();
//        MVCHelper
    }

    public BeautyServiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (onBeautyCheckChangeListener != null) {
                    ServicesBean servicesBean = servieceList.get(checkedId);
                    if (servicesBean != currentService) {
                        currentService = servicesBean;
                        onBeautyCheckChangeListener.onBeautyCheckChanged(servieceList.get(checkedId));
                    }
                }
            }
        });
    }

    public void setBeautyService(List<ServicesBean> servieceList) {
        if (servieceList == null)
            return;
        this.servieceList = servieceList;
        int width = getResources().getDimensionPixelOffset(R.dimen.w160);
        int height = getResources().getDimensionPixelOffset(R.dimen.h90);
        for (int i = 0; i < servieceList.size(); i++) {
            RadioButton child = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_beauty, null);
            child.setId(i);
            child.setText(servieceList.get(i).getName());
            addView(child, new LayoutParams(width, height));
        }
//        storeList
    }


    public void setOnBeautyCheckChangeListener(OnBeautyCheckChangeListener onBeautyCheckChangeListener) {
        this.onBeautyCheckChangeListener = onBeautyCheckChangeListener;
    }
}
