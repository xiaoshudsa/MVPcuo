package com.zxp.frame;

import java.util.Map;

import retrofit2.http.POST;

public class ContractPersenter implements IContractPresenter {
    IContractView view;
    private final IContractModel testModel;

    public ContractPersenter(IContractView view) {
        this.view = view;
        testModel = new TestModel();
    }

    @Override
    public void getData(int which, Object... objects) {
        testModel.getData(this,which, objects);
    }

    @Override
    public void onData(int whichApi, int loadType, Object... pa) {
        view.onData(whichApi,loadType,pa);
    }

    @Override
    public void error(int which, Throwable throwable) {
        view.error(which,throwable);
    }
}
