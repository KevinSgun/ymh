package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.widget.ActionSheet;
import com.zitech.framework.widget.RemoteImageView;

import java.io.File;
import java.util.Calendar;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.api.request.UpdateProfileRequest;
import cn.kuailaimei.client.api.response.FilePathResponse;
import cn.kuailaimei.client.common.User;
import cn.kuailaimei.client.common.ui.PhotoPickingActivity;
import cn.kuailaimei.client.common.widget.CommonDialog;
import cn.kuailaimei.client.common.widget.ToolBarHelper;
import cn.kuailaimei.client.common.utils.DateUtil;
import cn.kuailaimei.client.common.utils.ToastMaster;
import ics.datepicker.ICSDatePickerDialog;

/**
 * Created by ymh on 2016/7/2 0002.
 */
public class ProfileInfoActivity extends PhotoPickingActivity implements View.OnClickListener, TextWatcher {
    private static final int REQUEST_FOR_CHOOSE_PHOTOS = 200;
    private LinearLayout chooseavatarlayout;
    private EditText inputnicknameet;
    private LinearLayout profilenicknamelayout;
    private TextView gendertv;
    private LinearLayout choosegenderlayout;
    private LinearLayout choosebirthdaylayout;
    private ICSDatePickerDialog datickerDialog;
    private TextView birthdaytv;
    private RemoteImageView avatariv;
    private boolean isChange;
    private String chooseGender;
    private String avatarUrl;
    private String nickName;
    private String birthday;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profile_info;
    }

    @Override
    protected void initView() {
        setTitle("用户资料");
        setRightText("保存");
        rightSaveStatusToggle();
        chooseavatarlayout = (LinearLayout) findViewById(R.id.choose_avatar_layout);
        inputnicknameet = (EditText) findViewById(R.id.input_nickname_et);
        profilenicknamelayout = (LinearLayout) findViewById(R.id.profile_nickname_layout);
        gendertv = (TextView) findViewById(R.id.gender_tv);
        choosegenderlayout = (LinearLayout) findViewById(R.id.choose_gender_layout);
        choosebirthdaylayout = (LinearLayout) findViewById(R.id.choose_birthday_layout);
        birthdaytv = (TextView) findViewById(R.id.birthday_tv);
        avatariv = (RemoteImageView) findViewById(R.id.avatar_iv);
        chooseavatarlayout.setOnClickListener(this);
        choosegenderlayout.setOnClickListener(this);
        choosebirthdaylayout.setOnClickListener(this);
        inputnicknameet.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
        User user = User.get();
        avatariv.setBitmapTransformation(new CropCircleTransformation(this));

        avatarUrl = user.getPortrait();
        nickName = user.getNickname();
        chooseGender = user.getSex();
        birthday = user.getBirthday();

        inputnicknameet.setText(nickName);
        avatariv.setImageUri(R.mipmap.ic_avatar,avatarUrl);
        gendertv.setText(user.getSexTxt());
        birthdaytv.setText(birthday);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_avatar_layout:
                requestTakePhoto("用户头像", EFFECT_TYPE_CUT);
                break;
            case R.id.choose_gender_layout:
                final ActionSheet sheet = ActionSheet.create(this);
                sheet.addButton("男", ActionSheet.WHITE_STYLE_BTN);
                sheet.addButton("女", ActionSheet.WHITE_STYLE_BTN);
                sheet.addCancelButton("取消");
                sheet.setMainTitle("选择性别");
                sheet.setOnButtonClickListener(new ActionSheet.OnButtonClickListener() {
                    @Override
                    public void OnClick(View clickedView, int which) {
                        isChange = true;
                        rightSaveStatusToggle();
                        if (which == 0) {
                            gendertv.setText("男");
                            chooseGender = "1";
                        }else {
                            gendertv.setText("女");
                            chooseGender = "2";
                        }
                        sheet.dismiss();

                    }
                });
                sheet.setCanceledOnTouchOutside(true);
                sheet.show();
                break;
            case R.id.choose_birthday_layout:
                if (datickerDialog == null) {
                    datickerDialog = new ICSDatePickerDialog(getContext());
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, 0);
                    datickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                    datickerDialog.setDatePickListener(new ICSDatePickerDialog.DatePickerListener() {
                        @Override
                        public void onPickDate(Calendar calendar) {
                            isChange = true;
                            rightSaveStatusToggle();
                            birthdaytv.setText(DateUtil.formart(calendar));
                        }
                    });

                }
                datickerDialog.show();
                break;
        }
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if(position == ToolBarHelper.ITEM_RIGHT&&isChange){
            nickName = inputnicknameet.getText().toString();
            birthday = birthdaytv.getText().toString();
            if(TextUtils.isEmpty(nickName)){
                ToastMaster.shortToast("请输入你的昵称");
                return;
            }
            UpdateProfileRequest profileRequest = new UpdateProfileRequest();
            profileRequest.setName(nickName);
            profileRequest.setSex(chooseGender);
            profileRequest.setBirthday(birthday);
            if(!TextUtils.isEmpty(avatarUrl))
                profileRequest.setPortrait(avatarUrl);
            Request request = new Request(profileRequest);
            request.sign();
            ApiFactory.updateProfile(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                @Override
                protected void onNextInActive(ApiResponse apiResponse) {
                    Basic basic = apiResponse.getBasic();
                    ToastMaster.shortToast(basic.getMsg());
                    if(basic.getStatus() == 1){
                        if(!TextUtils.isEmpty(avatarUrl))
                            User.get().storePortrait(avatarUrl);
                        if(!TextUtils.isEmpty(nickName))
                            User.get().storeNickname(nickName);
                        if(!TextUtils.isEmpty(chooseGender))
                            User.get().storeSex(chooseGender);
                        if(!TextUtils.isEmpty(birthday))
                            User.get().storeBirthday(birthday);
                        User.get().notifyChange();
                        finish();
                    }

                }
            });
        }
    }

    @Override
    protected void onPhotoCut(String picturePath, String cutPicturePath) {
        super.onPhotoCut(picturePath, cutPicturePath);

        File file = new File(cutPicturePath);
        ApiFactory.upload("1",file).subscribe(new ProgressSubscriber<FileUploadResponse<FilePathResponse>>(this) {
            @Override
            protected void onNextInActive(FileUploadResponse<FilePathResponse> apiResponse) {
                FilePathResponse reponse = apiResponse.getData();
                if(reponse.getImgSrc()!=null&&reponse.getImgSrc().size()>0){
                    isChange = true;
                    rightSaveStatusToggle();
                    avatarUrl = reponse.getImgSrc().get(0);
                    avatariv.setImageUri(avatarUrl);
                }
            }
        });

    }

    private void rightSaveStatusToggle() {
        if(isChange)
            setRightTextVisiable(View.VISIBLE);
        else
            setRightTextVisiable(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(isChange){
            CommonDialog dialog = new CommonDialog(this,"确定放弃这次修改吗");
            dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                @Override
                public void onClick(Dialog dialog) {
                    finish();
                }
            });
            dialog.show();
        }else{
            super.onBackPressed();
        }

    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,ProfileInfoActivity.class);
        act.startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(inputnicknameet.getText().length()>0&&!inputnicknameet.getText().toString().equals(User.get().getNickname())){
            isChange = true;
            rightSaveStatusToggle();
        }
    }
}
