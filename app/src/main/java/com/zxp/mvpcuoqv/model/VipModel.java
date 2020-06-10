package com.zxp.mvpcuoqv.model;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.Host;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;

public class VipModel implements IContractModel {
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi){
            case ApiConfig.VIP_LIVE:
                PrameHashMap pro = new PrameHashMap().add("pro",FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id());
                NetManger.getInstance().netWork(NetManger.getService().getVipLive(Host.EDU_OPENAPI+ FengUrl.GETVIPLIVE_URL,pro),iContractPresenter,whichApi,parms[0]);
                break;
            case ApiConfig.VIP_LIST:
                PrameHashMap add = new PrameHashMap().add("specialty_id",FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id()).add("page",parms[1]);
                NetManger.getInstance().netWork(NetManger.getService().getVipList(Host.EDU_OPENAPI+ FengUrl.GETVIPLIST_URL,add),iContractPresenter,whichApi,parms[0]);
                break;
        }
    }
}
