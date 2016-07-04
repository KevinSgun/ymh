package cn.kuailaimei.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.pay.PayTools;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WX_WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
//		Logger.d(TAG, String.format("微信支付结果%s", String.valueOf(resp.errCode)));

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage(String.format("微信支付结果%s", String.valueOf(resp.errCode)));
//			builder.show();
//		}
		PayTools.OnPayResultListener onPayResultListener = PayTools.getInstance(this).getOnPayResultListener();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
				if (onPayResultListener != null)
					onPayResultListener.paySuccessfulResult(PayTools.WX_WAY, "支付成功!");
			} else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
				if (onPayResultListener != null)
					onPayResultListener.failedPayResult(PayTools.WX_WAY, "支付已取消!");
			} else {
				if (onPayResultListener != null)
					onPayResultListener.failedPayResult(PayTools.WX_WAY, "支付失败!");
			}
//			finish();

		}
//		else {
			finish();
//		}

	}
}