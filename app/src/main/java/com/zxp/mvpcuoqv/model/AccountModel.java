package com.zxp.mvpcuoqv.model;

import android.content.Context;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;
import com.zxp.mvpcuoqv.Application1907;
import com.zxp.mvpcuoqv.R;

public class AccountModel implements IContractModel {
    private NetManger mManger = NetManger.getInstance();
    private Context mContext = Application1907.getContext();
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String) parms[0]), iContractPresenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new PrameHashMap().add("mobile",parms[0]).add("code",parms[1])),iContractPresenter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                mManger.netWork(mManger.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(new PrameHashMap().add("zuid",uid).add("uid",uid)),iContractPresenter,whichApi);
                break;
        }

    }
}