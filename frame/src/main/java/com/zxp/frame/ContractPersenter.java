package com.zxp.frame;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import retrofit2.http.POST;

public class ContractPersenter<V extends IContractView,M extends IContractModel> implements IContractPresenter {
    private SoftReference<V> mView;
    private SoftReference<M> testModel;
    private WeakReference<Activity> mActivityWeakReference;
    private List<Disposable> mDisposableList;
    private LoadView mInstance;
    public ContractPersenter(V pView,M pModel) {
        this.mView = new SoftReference<>(pView);
        testModel = new SoftReference<>(pModel);
        mDisposableList=new ArrayList<>();
    }
    public void allowLoading(Activity pActivity) {
        mActivityWeakReference = new WeakReference<>(pActivity);
    }
    @Override
    public void getData(int which, Object... pObjects) {
        if (mActivityWeakReference!=null&&mActivityWeakReference.get()!=null&&mActivityWeakReference.get()instanceof Activity){
            Activity activity = mActivityWeakReference.get();
            if (!activity.isFinishing()&&mInstance==null){
                mInstance=new LoadView(activity,null);
            }
            int load = -1;
            if (pObjects != null && pObjects.length != 0 && pObjects[0] instanceof Integer){
                load = (int) pObjects[0];
            }
            if (load != LoadTypeConfig.MORE && load != LoadTypeConfig.REFRESH && mInstance != null && !mInstance.isShowing()){
                mInstance.show();
            }
        }
        if (testModel!=null&&testModel.get()!=null)testModel.get().getData(this,which, pObjects);
    }

    @Override
    public void onData(int whichApi, Object[] pa) {
        if (mView != null && mView.get() != null)mView.get().onData(whichApi,pa);
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
