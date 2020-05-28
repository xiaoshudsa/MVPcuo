package com.zxp.frame;

public interface IContractModel<T> {

    void getData(IContractPresenter iContractPresenter,int whichApi,T... parms);
}
