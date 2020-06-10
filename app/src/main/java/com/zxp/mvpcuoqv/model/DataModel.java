package com.zxp.mvpcuoqv.model;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.FengUrl;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.Host;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;
import com.zxp.frame.PrameHashMap;
import com.zxp.mvpcuoqv.R;

import java.lang.reflect.Method;

public class DataModel implements IContractModel {
    @Override
    public void getData(IContractPresenter iContractPresenter, int whichApi, Object[] parms) {
        switch (whichApi){
            case ApiConfig.DATA_GROUP:
                PrameHashMap add = new PrameHashMap().add("type", 1).add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid()).add("page", parms[1]);
                NetManger.getInstance().netWork(NetManger.getService().getGroupList(Host.BBS_OPENAPI+ FengUrl.GETGROUPLIST,add),iContractPresenter,whichApi,parms[0]);
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                PrameHashMap add1 = new PrameHashMap().add("group_id", parms[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                NetManger.getInstance().netWork(NetManger.getService().removeFocus(Host.BBS_API+FengUrl.REMOVEGROUP,add1),iContractPresenter,whichApi,parms[1]);
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                PrameHashMap add2 = new PrameHashMap().add("gid", parms[0]).add("group_name", parms[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                NetManger.getInstance().netWork(NetManger.getService().focus(Host.BBS_API+FengUrl.JOINGROUP,add2),iContractPresenter,whichApi,parms[2]);
                break;
        }
    }
}
