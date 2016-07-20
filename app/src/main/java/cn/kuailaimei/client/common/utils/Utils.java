package cn.kuailaimei.client.common.utils;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.utils.LogUtils;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.PushIdRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.SP;
import rx.functions.Action1;

/**
 * Created by lu on 2016/7/3.
 */
public class Utils extends com.zitech.framework.utils.Utils {

    public static String formarttDistance(int distance) {
        if (distance < 100) {
            return "<100m";
        } else if (distance < 1000) {
            return distance + "m";
        } else {
            float value = distance / 1000f;
            return String.format("%.2f", value) + "km";

        }
    }

    public static String formarttDistance(String distance) {
        int distanceInt = 0;
        try {
            distanceInt = Integer.parseInt(distance);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return formarttDistance(distanceInt);
    }

    public static String formartPrice(int price) {
        return "￥" + price;
    }

    public static void bindJGPushIdToService(final String registerId) {
        if(!SP.getDefaultSP().getBoolean(Constants.IS_BINDING_JPUSH_ID,false)) return;
        PushIdRequest pushRequest = new PushIdRequest();
        pushRequest.setPushId(registerId);
        Request request = new Request(pushRequest);
        request.sign();
        ApiFactory.uploadJPushId(request).subscribe(new Action1<ApiResponse>() {
            @Override
            public void call(ApiResponse apiResponse) {
                SP.getDefaultSP().putBoolean(Constants.IS_BINDING_JPUSH_ID,true);
                LogUtils.i("成功绑定JPushId = "+registerId+"到服务器");
            }
        });
    }
}
