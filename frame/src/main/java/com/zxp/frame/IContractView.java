package com.zxp.frame;

public interface IContractView<D> {
    void onData(int whichApi,D... pa);

    void error(int which,Throwable throwable);
}
