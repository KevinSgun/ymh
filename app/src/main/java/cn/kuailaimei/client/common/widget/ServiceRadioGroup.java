package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.DesignerService;

/**
 * Created by lu on 2016/7/4.
 */
public class ServiceRadioGroup extends MutilRadioGroup {
    private List<DesignerService> services;
    private DesignerService currentServcie;
    private OnServiceCheckedListener onServiceCheckedListener;

    public interface OnServiceCheckedListener {
        void onServiceChecked(DesignerService service);
    }

    public ServiceRadioGroup(Context context) {
        super(context);
        init();
    }

    public ServiceRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
                View v = findViewById(checkedId);
                int positon = Integer.parseInt((String) v.getTag());
                DesignerService designerService = services.get(positon);
                if (designerService != currentServcie) {
                    currentServcie = designerService;
                    if (onServiceCheckedListener != null)
                        onServiceCheckedListener.onServiceChecked(currentServcie);
                }

            }
        });
    }

    public void setServiceItems(List<DesignerService> items) {
        if (items == null)
            return;
        services = items;
        for (int i = 0; i < items.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_service, this);
            RadioButton serviceChoice = (RadioButton) view.findViewById(R.id.service_choice);
            serviceChoice.setTag(String.valueOf(i));
        }
    }

    public void setOnServiceCheckedListener(OnServiceCheckedListener onServiceCheckedListener) {
        this.onServiceCheckedListener = onServiceCheckedListener;
    }
}
