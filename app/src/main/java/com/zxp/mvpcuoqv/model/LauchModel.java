package com.zxp.mvpcuoqv.model;

import android.text.TextUtils;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.BaseUrl;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;

public  class LauchModel implements IContractModel {
    NetManger instance = NetManger.getInstance();
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi){
            case ApiConfig.ADVERT:
                PrameHashMap map = new PrameHashMap().add("w",parms[1]).add("h",parms[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if(!TextUtils.isEmpty((String)parms[0]))map.add("specialty_id",parms[0]);
                instance.netWork(NetManger.getService().getAdvert(BaseUrl.ad_openapi+FengUrl.ADVERT_URL,map),iContractPresenter,whichApi);
                break;
            case ApiConfig.SUBJECT:
                instance.netWork(instance.getService().getSubjectList(FrameApplication.getFrameApplicationContext().getString(com.zxp.frame.R.string.edu_openapi)+FengUrl.SUBJECTLIST_URL), iContractPresenter, whichApi);
                break;
        }
        }
    }


