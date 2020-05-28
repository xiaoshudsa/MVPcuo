package com.zxp.frame;

public interface IContractPresenter<P> extends IContractView  {

    void getData(int which,P... ps);

}
