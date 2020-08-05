package com.zxp.mvpcuoqv.model;

import android.content.Context;

import com.zxp.data.ThirdLoginData;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.Host;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;

import com.zxp.frame.PrameHashMap;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.frame.util.RsaUtil;
import com.zxp.mvpcuoqv.Application1907;
import com.zxp.mvpcuoqv.R;

public class AccountModel implements IContractModel {
    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.getContext();
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] params) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                mManger.netWork(mManger.getService().getLoginVerify(mContext.getString(R.string.passport_openapi_user)+ FengUrl.LOGINVERIFY_URL,(String) params[0]), iContractPresenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManger.netWork(mManger.getService().loginByVerify(mContext.getString(R.string.passport_openapi_user)+FengUrl.LOGINBYVERIFY_URL1,new PrameHashMap().add("mobile",params[0]).add("code",params[1])),iContractPresenter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManger.netWork(mManger.getService().getHeaderInfo(mContext.getString(R.string.passport_api)+FengUrl.HEADERINFO_URL,new PrameHashMap().add("zuid",uid).add("uid",uid)),iContractPresenter,whichApi);
                break;
            case ApiConfig.REGISTER_PHONE:
                mManger.netWork(mManger.getService().checkVerifyCode(Host.PASSPORT_API+FengUrl.CHECKMOBILECODE,new PrameHashMap().add("mobile",params[0]).add("code",params[1])),iContractPresenter,whichApi);
                break;
            case ApiConfig.CHECK_PHONE_IS_USED:
                mManger.netWork(mManger.getService().checkPhoneIsUsed(Host.PASSPORT_API+FengUrl.CHECKMOBILEISUSE,params[0]),iContractPresenter,whichApi);
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                mManger.netWork(mManger.getService().sendRegisterVerify(Host.PASSPORT_API+FengUrl.SENDMOBILECODE,params[0]),iContractPresenter,whichApi);
                break;
            case ApiConfig.NET_CHECK_USERNAME:
                mManger.netWork(mManger.getService().checkName(Host.PASSPORT+FengUrl.USERNAMEISEXIST,params[0]),iContractPresenter,whichApi);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                PrameHashMap param = new PrameHashMap().add("username", params[0]).add("password", RsaUtil.encryptByPublic((String) params[1]))
                        .add("tel", params[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                mManger.netWork(mManger.getService().registerCompleteWithSubject(Host.PASSPORT_API+FengUrl.USERREGFORSIMPLE,param),iContractPresenter,whichApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
                PrameHashMap add = new PrameHashMap().add("ZLSessionID", "").add("seccode", "").add("loginName", params[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) params[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                mManger.netWork(NetManger.getService().loginByAccount(Host.PASSPORT_OPENAPI+FengUrl.USERLOGINNEWAUTH,add),iContractPresenter,whichApi);
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                PrameHashMap wxParams = new PrameHashMap().add("appid", ConstantKey.WX_APP_ID).add("secret", ConstantKey.WX_APP_SECRET).add("code", params[0]).add("grant_type", "authorization_code");
                mManger.netWork(NetManger.getService().getWechatToken(Host.WX_OAUTH+FengUrl.ACCESS_TOKEN,wxParams),iContractPresenter,whichApi);
                break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData data = (ThirdLoginData) params[0];
                PrameHashMap add1 = new PrameHashMap().add("openid", data.openid).add("type", data.type).add("url", "android");
                mManger.netWork(NetManger.getService().loginByWechat(Host.PASSPORT_API+FengUrl.THIRDLOGIN,add1),iContractPresenter,whichApi);
                break;
            case ApiConfig.BIND_ACCOUNT:
                String account = (String) params[0];
                String password = (String) params[1];
                ThirdLoginData thirdLoginData = (ThirdLoginData) params[2];
                PrameHashMap thirdDataParam = new PrameHashMap().add("username", account).add("password", RsaUtil.encryptByPublic(password))
                        .add("openid", thirdLoginData.openid).add("t_token", thirdLoginData.token)
                        .add("utime", thirdLoginData.utime).add("type", thirdLoginData.type)
                        .add("url", "android").add("state", 1);
                mManger.netWork(NetManger.getService().bindThirdAccount(Host.PASSPORT_API+FengUrl.NEWTHIRDBIND,thirdDataParam),iContractPresenter,whichApi);
                break;
        }

    }
}
