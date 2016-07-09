package cn.kuailaimei.client.common.ui;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.VerifyCodeRequest;
import cn.kuailaimei.client.common.utils.StringUtils;
import cn.kuailaimei.client.common.utils.ToastMaster;
import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

/**
 * 需要验证码的地方实在太多，因此抽出来
 *
 * @author lu
 *
 */
public abstract class ValidateActivity extends AppBarActivity implements View.OnClickListener {

	private static final int VALIDATE_CODE_LENGTH = Constants.VALIDATE_CODE_LENGTH;
	private static Handler handler = new Handler(Looper.getMainLooper());
	private static long lastGetValidateCodeTime = -1;
	private Button validateCodeButton;
	private int maxWaitSeconds = 60;

	protected abstract int getValidateCodeButtonId();

	protected abstract String getPhoneNumber();

	protected abstract String getValidateCodeType();

	@Override
	protected void onStart() {
		super.onStart();
		if (validateCodeButton == null) {
			validateCodeButton = (Button) findViewById(getValidateCodeButtonId());
			validateCodeButton.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == getValidateCodeButton().getId()) {
			if(ViewUtils.isFastDoubleClick()) return;
			getValidateCode();
		}
	}

	private Button getValidateCodeButton() {
		return validateCodeButton;
	}

	/**
	 * 验证用户 名是否有效
	 */
	protected boolean validateName(String name) {
		if (StringUtils.isPhoneNum(name)) {
			return true;
		} else {
			ToastMaster.longToast(R.string.enter_right_phone_num);
			return false;
		}
	}

	/**
	 * 验证密码
	 */
	protected boolean validatePassword(String password) {
		if (!StringUtils.passWordCheck(password)) {
			ToastMaster.longToast(R.string.incorrect_password_format);
			return false;
		}
		return true;

	}

	protected boolean validateCode(String code) {
		return code.length() == VALIDATE_CODE_LENGTH;
	}

	private void getValidateCode() {
		String phoneNum = getPhoneNumber();
		if (!validateName(phoneNum))
			return;
		if (lastGetValidateCodeTime != -1 && ((System.currentTimeMillis() / 1000) - lastGetValidateCodeTime) <= 60) {
			ToastMaster.shortToast("请求验证码过于频繁，请稍后再试");
			return;
		}
		VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
		verifyCodeRequest.setApkind(getValidateCodeType());
		verifyCodeRequest.setMobile(getPhoneNumber());
		Request request = new Request(verifyCodeRequest);
		request.sign();
		ApiFactory.getVerifyCode(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
			@Override
			protected void onNextInActive(ApiResponse apiResponse) {
				Basic basic = apiResponse.getBasic();
				if(basic.getStatus() == 1){
					getValidateCodeButton().setEnabled(false);
					startCountDown();
					lastGetValidateCodeTime = System.currentTimeMillis() / 1000;
				}
				ToastMaster.shortToast(basic.getMsg());
			}
		});
	}

	private void startCountDown() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (maxWaitSeconds == 0) {
					getValidateCodeButton().setText("重新获取");
					getValidateCodeButton().setEnabled(true);
					maxWaitSeconds = 60;
				} else {
					getValidateCodeButton().setText(String.format(getResources().getString(R.string.get_verify_second),maxWaitSeconds));
					if (!isFinishing()) {
						startCountDown();
					}
				}
				maxWaitSeconds--;
			}

		}, 1000);
	}
}
