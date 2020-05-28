package com.zxp.frame;

public interface IContractView<D> {
    void onData(int whichApi,int loadType,D... pa);

    void error(int which,Throwable throwable);
}
