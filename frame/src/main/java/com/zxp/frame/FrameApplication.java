package com.zxp.frame;


import android.content.Context;
import android.text.TextUtils;

import com.yiyatech.utils.UtilsApplication;
import com.zxp.data.Device;
import com.zxp.data.LoginInfo;
import com.zxp.data.SpecialtyChooseEntity;

public class FrameApplication extends UtilsApplication {
    private static FrameApplication application;
    private Device mDeviceInfo;
    private static LoginInfo mLoginInfo;
    private String cookie;
    private static SpecialtyChooseEntity.DataBean selectedInfo;

    public static SpecialtyChooseEntity.DataBean getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(SpecialtyChooseEntity.DataBean pSelectedInfo) {
        selectedInfo = pSelectedInfo;
    }

    public Device getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setmDeviceInfo(Device mDeviceInfo) {
        this.mDeviceInfo = mDeviceInfo;
    }

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public void setmLoginInfo(LoginInfo mLoginInfo) {
        this.mLoginInfo = mLoginInfo;
    }

    public String getCookie() {
        return cookie;
    }
    public static boolean isLogin(){
        return mLoginInfo != null && !TextUtils.isEmpty(mLoginInfo.getUid());
    }
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }
    public static FrameApplication getFrameApplication(){
        return application;
    }
    public static Context getFrameApplicationContext(){
        return application.getApplicationContext();
    }

}
