package com.zxp.frame;

import android.text.TextUtils;

import com.zxp.frame.util.PrameHashMap;

public  class LauchModel implements IContractModel {
    NetManger instance = NetManger.getInstance();
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi){
            case ApiConfig.ADVERT:
                PrameHashMap map = new PrameHashMap().add("w",parms[1]).add("h",parms[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if(!TextUtils.isEmpty((String)parms[0]))map.add("specialty_id",parms[0]);
                instance.netWork(instance.getService(FrameApplication.getFrameApplicationContext().getString(R.string.ad_openapi)).getAdvert(map),iContractPresenter,whichApi);
                break;
            case ApiConfig.SUBJECT:
                instance.netWork(instance.getService(FrameApplication.getFrameApplicationContext().getString(R.string.edu_openapi)).getSubjectList(), iContractPresenter, whichApi);
                break;
        }
        }
    }


