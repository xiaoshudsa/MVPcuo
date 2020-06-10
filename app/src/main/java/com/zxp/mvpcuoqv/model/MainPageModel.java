package com.zxp.mvpcuoqv.model;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.Host;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;
import com.zxp.frame.constants.Constants;

import java.lang.reflect.Method;

/**
 * Created by 任小龙 on 2020/6/4.
 */
public class MainPageModel implements IContractModel {
    private NetManger manager = NetManger.getInstance();
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi) {
            case ApiConfig.HOME_BEAN:
                PrameHashMap add = new PrameHashMap().add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("page", parms[1]).add("limit", Constants.LIMIT_NUM).add("new_banner", 1);
                manager.netWork(NetManger.getService().getCommonList(Host.EDU_OPENAPI+ FengUrl.HOMEBEAN_URL,add),iContractPresenter,whichApi,parms[0]);
                break;
            case ApiConfig.HOME_BANNER_BEAN:
                PrameHashMap live = new PrameHashMap().add("pro",FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("more_live","1").add("is_new",1).add("new_banner",1);
                manager.netWork(NetManger.getService().getBannerLive(Host.EDU_OPENAPI+FengUrl.HOMEBANNERBEAN_URL,live),iContractPresenter,whichApi,parms[0]);
                break;
        }

    }


}
