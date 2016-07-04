package cn.kuailaimei.client.pay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import cn.kuailaimei.client.Constants;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WX {
	private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what == 1){
				if(nDialog!=null&&nDialog.isShowing()) nDialog.dismiss();
			}
			return false;
		}
	});
	private Activity mContext;
	private IWXAPI msgApi;
	private PayReq req;
	private ProgressDialog nDialog;
	
	protected WX(Activity context){
		init(context);	
	}
	
	private void init(Activity context) {
		mContext = context;
		msgApi = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID);
		msgApi.registerApp(Constants.APP_ID);
		req = new PayReq();
	}
	
	/**
	 * 使用服务器得到的prepay_id和最终签名
	*/
	private void nGenPayReq(String APP_ID, String MCH_ID, String prepayId, String packageValue,
							String nonceStr, String timeStamp, String sign){
		nDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);

		nDialog.setTitle("");
		nDialog.setMessage("正在加载...");
		nDialog.setCanceledOnTouchOutside(false);
		nDialog.show();
		mHandler.sendEmptyMessageDelayed(1, 2500);
		req.appId = Constants.APP_ID;
		req.partnerId = MCH_ID;
		req.prepayId = prepayId;
		req.nonceStr = nonceStr;
		req.timeStamp = timeStamp;
		req.packageValue = packageValue;
		req.sign = sign;
		req.extData	= "app data";

	}
	
	/** 发起微信支付,请确保req已生成，即已完成含prepayid的签名*/
	protected void sendPayReq(String APP_ID, String MCH_ID, String prepayId, String packageValue,
							  String nonceStr, String timeStamp, String sign) {
		nGenPayReq(APP_ID,MCH_ID,prepayId,packageValue,nonceStr,timeStamp,sign);

		msgApi.sendReq(req);
	}

}
