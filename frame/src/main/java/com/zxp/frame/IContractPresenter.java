package com.zxp.frame;

import io.reactivex.disposables.Disposable;

public interface IContractPresenter<P> extends IContractView  {

    void getData(int which,P... ps);
    void addObserver(Disposable pDisposable);
}
