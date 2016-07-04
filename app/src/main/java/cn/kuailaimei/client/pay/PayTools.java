package cn.kuailaimei.client.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

/**
 * */
public class PayTools {
	/** 支付宝支付方式*/
	public static final String ZFB_WAY = "1";
	/** 微信支付方式*/
	public static final String WX_WAY = "2";

	protected static final int ZFB_PAY_FLAG = 0x1010;
	private static PayTools mInstance;
	private Handler mHandler = new Handler(Looper.getMainLooper(),new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what == ZFB_PAY_FLAG){
				String result = (String) msg.obj;
				PayResult payResult = new PayResult(result);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				//				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
//				Logger.e("zfb_pay_result", "code="+resultStatus+";msg="+payResult.getMemo());
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					if(onPayResultListener !=null){
						onPayResultListener.paySuccessfulResult(ZFB_WAY, "支付成功!");
					}

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						if(onPayResultListener !=null){
							onPayResultListener.failedPayResult(ZFB_WAY, "支付结果确认中...");
						}

					} else if(TextUtils.equals(resultStatus, "6001")){
						if(onPayResultListener !=null){
							onPayResultListener.failedPayResult(ZFB_WAY, "支付已取消");
						}
					} else if(TextUtils.equals(resultStatus, "6002")){
						if(onPayResultListener !=null){
							onPayResultListener.failedPayResult(ZFB_WAY, "网络连接异常");
						}
					}else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						if(onPayResultListener !=null){
							onPayResultListener.failedPayResult(ZFB_WAY, "支付失败");
						}
					}
				}
			}
			return false;
		}
	});
	private OnPayResultListener onPayResultListener;
	protected ZFB zfb;
	private WX wx;
	private static Activity mContext;

	private PayTools(Activity context){
		mContext = context;
		if(zfb == null){
			zfb = new ZFB(context);
		}
		if(wx == null){
			wx = new WX(context);
		}
	}

	public static PayTools getInstance(Activity context){
		if(mContext!=null&&mContext.isFinishing()){
			mInstance = null;
			mContext = null;
		}
		if(mInstance == null||mContext==null){
			mInstance = new PayTools(context);
		}
		return mInstance;
	}

	/**
	 * 支付结果回调接口
	 *
	 */
	public interface OnPayResultListener {
		/**
		 * 返回支付成功结果的回调
		 * @param payType  支付的类型，如支付宝为0、微信为1
		 * @param message  返回的结果消息
		 */
		void paySuccessfulResult(String payType, String message);
		/**
		 * 返回支付失败结果的回调
		 * @param payType 支付的类型，如支付宝为0、微信为1
		 * @param message 返回的结果消息
		 */
		void failedPayResult(String payType, String message);
	}

	/** 设置支付结果回调监听*/
	public void setOnPayResultListener(OnPayResultListener onPayResultListener){
		synchronized (this) {
			if(this.onPayResultListener == null){
				this.onPayResultListener = onPayResultListener;
			}
		}
	}

	public OnPayResultListener getOnPayResultListener(){
		return onPayResultListener;
	}

	/** <pre>
	 * 使用支付宝支付
	 * @param sign 从服务器获得的签名数据
	 */
	public void payByZFB(final String sign){
		if(!TextUtils.isEmpty(sign)){
			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					String result = zfb.pay(sign);
					Message msg = new Message();
					msg.what = ZFB_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}

			};
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}else{
			if(onPayResultListener !=null){
				onPayResultListener.failedPayResult(ZFB_WAY, "支付失败");
			}
		}
	}

	/** <pre>
	 * 使用支付宝支付
	 * @param orderInfo 订单信息
	 * @deprecated 使用订单信息在本地签名调起支付的方式不安全，如非必要，不要使用
	 */
	public void paySignByZFBLocal(final String orderInfo){
		if(!TextUtils.isEmpty(orderInfo)){
			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					String result = zfb.payInLocal(orderInfo);
					Message msg = new Message();
					msg.what = ZFB_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}

			};
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}else{
			if(onPayResultListener !=null){
				onPayResultListener.failedPayResult(ZFB_WAY, "支付失败");
			}
		}
	}


	/**
	 * <pre>
	 * 调起微信支付
	 * @param APP_ID       微信签约的ID 
	 * @param MCH_ID	       微信收款账号 
	 * @param prepayId	       预支付ID微信
	 * @param packageValue 微信包名值
	 * @param nonceStr	       防重复随机值
	 * @param timeStamp    时间戳
	 */
	public void payByWX(String APP_ID, String MCH_ID, String prepayId, String packageValue,
						String nonceStr, String timeStamp, String sign){
		//		Constants.MCH_ID = MCH_ID;
		wx.sendPayReq(APP_ID, MCH_ID, prepayId, packageValue, nonceStr, timeStamp, sign);
	}

//	/**
//	 * <pre>
//	 * 使用微信支付
//	 * @param resp  支付所需的必要信息
//	 */
//	public void payByWx(PayResult resp){
//		if(resp!=null&&resp.getPrepay_id()!=null){
//			payByWX(resp.getWx_app_id(),resp.getWx_seller(), resp.getPrepay_id(),
//					resp.getPackageValue(), resp.getNoncestr(), resp.getTimestamp(),resp.getSign());
//		}else{
//			if(onPayResultListener !=null)
//				onPayResultListener.failedPayResult(PayTools.WX_WAY,"支付失败!");
//		}
//	}

	/**
	 * 获取支付宝sdk版本号
	 * @return
	 */
	public String getZfbVersion(){
		return zfb.getSDKVersion();
	}

	/** 释放资源*/
	public void release(){
		mInstance = null;
		onPayResultListener = null;
		mContext = null;
		wx = null;
		zfb = null;
	}
	
}
