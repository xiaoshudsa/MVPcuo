package com.zxp.mvpcuoqv.model;

import com.zxp.frame.ApiConfig;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractPresenter;
import com.zxp.frame.NetManger;

import java.util.Map;

public  class TestModel implements IContractModel {
    NetManger mManger = NetManger.getInstance();

    @Override
    public void getData(final IContractPresenter iContractPresenter, final int whichApi, Object[] parms) {

        switch (whichApi) {
            case ApiConfig.TEST_GET:
                final int loadType = (int) parms[0];
                Map param = (Map) parms[1];
                int pageId = (int) parms[2];
                mManger.netWork(mManger.getService().getTestData(param, pageId), iContractPresenter, whichApi, loadType);
                break;
        }
    }
}