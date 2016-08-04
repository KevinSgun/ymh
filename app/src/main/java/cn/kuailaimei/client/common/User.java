package cn.kuailaimei.client.common;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.client.BeautApplication;
import cn.kuailaimei.client.api.response.UserInfoResponse;
import cn.kuailaimei.client.common.event.EventFactory;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class User {
    private static final String TOKEN = "token";
    private static final String MOBILE="mobile";
    private static final String NICKNAME="nickname";
    private static final String PORTRAIT="portrait";
    private static final String SEX="sex";
    private static final String BIRTHDAY="birthday";
    private static final String SIGNATURE="signature";
    private static final String PUSHID="pushId";
    private SP sp;
    private String mobile;
    private String nickname;
    private String portrait;
    private String token;
    private String sex;
    private String birthday;
    private String signature;//个性签名
    private String pushId;//极光推送ID

    public static User get() {
        return BeautApplication.getInstance().getUser();
    }
    public User() {
        super();
        sp = new SP("USER_DATA");
        token = sp.getString(TOKEN, "");
        mobile = sp.getString(MOBILE, "");
        portrait = sp.getString(PORTRAIT, "");
        nickname = sp.getString(NICKNAME,"");
        sex = sp.getString(SEX, "");
        birthday = sp.getString(BIRTHDAY,"");
        signature = sp.getString(SIGNATURE,"");
        pushId = sp.getString(PUSHID,"");
    }

    public void storeFromUserInfo(UserInfoResponse userInfo) {
        if(userInfo == null) return;
        storeNickname(userInfo.getName());
        storeToken(userInfo.getToken());
        storePhoneNumber(userInfo.getMobile());
        storeSex(userInfo.getSex());
        storePortrait(userInfo.getPortrait());
        storeBirthday(userInfo.getBirthday());
        storeSignature(userInfo.getSignature());
        storePushId(userInfo.getPushId());
        notifyChange();
    }

    public void storeBirthday(String birthday) {
        this.birthday = birthday;
        sp.putString(BIRTHDAY, birthday);
    }


    public void storePortrait(String portrait) {
        this.portrait = portrait;
        sp.putString(PORTRAIT, portrait);
    }


    public void storeSex(String sex) {
        this.sex = sex;
        sp.putString(SEX, sex);
    }

    public void storeToken(String token) {
        this.token = token;
        sp.putString(TOKEN, token);
    }

    public void storeNickname(String nickname) {
        if(!this.nickname.equals(nickname)){
            this.nickname = nickname;
            sp.putString(NICKNAME, nickname);
        }
    }

    public void storePhoneNumber(String phoneNumber) {
        this.mobile = phoneNumber;
        sp.putString(MOBILE, phoneNumber);
    }

    public void storeSignature(String signature) {
        this.signature = signature;
        sp.putString(SIGNATURE, signature);
    }

    public void storePushId(String pushId) {
        this.pushId = pushId;
        sp.putString(PUSHID, pushId);
    }

    public void notifyChange() {
        EventBus.getDefault().post(new EventFactory.UserDataChange());
    }
    public String getToken() {
        return token;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getSex() {
        return sex;
    }

    public String getSignature() {
        return signature;
    }

    public String getPushId(){
        return pushId;
    }


    public String getSexTxt() {
        if("2".equals(sex))
            return "女";
        else if("1".equals(sex))
            return "男";
        return "保密";
    }

    public String getBirthday(){
        return birthday;
    }

    public boolean isNotLogin(){
        return TextUtils.isEmpty(token);
    }

    public void clear() {
        //
        sp.remove(TOKEN);
        token = null;
        //
        sp.remove(MOBILE);
        mobile = "";
        //
        sp.remove(NICKNAME);
        nickname = "";
        //
        sp.remove(PORTRAIT);
        portrait = "";
        //
        sp.remove(SEX);
        sex = "";
        //
        sp.remove(BIRTHDAY);
        birthday = "";
        //
        sp.remove(PUSHID);
        pushId = "";
        //
        sp.remove(SIGNATURE);
        signature = "";
        notifyChange();

    }

    public boolean notLogin(){
        return TextUtils.isEmpty(token);
    }
}
