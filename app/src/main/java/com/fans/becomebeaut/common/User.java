package com.fans.becomebeaut.common;

import android.text.TextUtils;

import com.fans.becomebeaut.BeautApplication;
import com.fans.becomebeaut.api.response.UserInfoResponse;
import com.fans.becomebeaut.common.event.EventFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class User {
    private static final String TOKEN = "token";
    private static final String MOBILE="mobile";
    private static final String NICKNAME="nickname";
    private static final String PORTRAIT="portrait";
    private static final String SEX="sex";
    private SP sp;
    private String mobile;
    private String nickname;
    private String portrait;
    private String token;
    private String sex;

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
    }

    public void storeFromUserInfo(UserInfoResponse userInfo) {
        if(userInfo == null) return;
        storeNickname(userInfo.getName());
        storeToken(userInfo.getToken());
        storePhoneNumber(userInfo.getMobile());
        storeSex(userInfo.getSex());
        storePortrait(userInfo.getPortrait());
        notifyChange();
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
        notifyChange();

    }

    public boolean notLogin(){
        return TextUtils.isEmpty(token);
    }
}
