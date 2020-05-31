package com.zxp.frame;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import retrofit2.http.POST;

public class ContractPersenter<V extends IContractView,M extends IContractModel> implements IContractPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> testModel;
    private List<Disposable> mDisposableList;
    public ContractPersenter(V pView,M pModel) {
        this.mView = new SoftReference<>(pView);
        testModel = new SoftReference<>(pModel);
    }

    @Override
    public void getData(int which, Object... objects) {
        if (testModel!=null&&testModel.get()!=null)testModel.get().getData(this,which, objects);
    }

    @Override
    public void onData(int whichApi, int loadType, Object... pa) {
        if (mView != null && mView.get() != null)mView.get().onData(whichApi,loadType,pa);
    }

    @Override
    public void error(int which, Throwable throwable) {
        if (mView != null && mView.get() != null)mView.get().error(which,throwable);
    }
    @Override
    public void addObserver(Disposable pDisposable) {
        if (mDisposableList == null) return;
        mDisposableList.add(pDisposable);
    }
    public void clear(){
        for (Disposable dis:mDisposableList) {
            if (dis != null && !dis.isDisposed())dis.dispose();
        }
        if (mView != null){
            mView.clear();
            mView = null;
        }
        if (testModel != null){
            testModel.clear();
            testModel = null;
        }
    }


}
