package com.zxp.mvpcuoqv.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.zxp.frame.ContractPersenter;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractView;



public abstract class BaseMvpActivity<M extends IContractModel>extends BaseActivity implements IContractView {

    private M setModel;
    public ContractPersenter mContractPresenter;
    @NonNull
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel = setModel();
        mContractPresenter = new ContractPersenter(this,setModel);
        setUpView();
        setUpData();
    }
    public abstract M setModel();
    public abstract void setUpView();
    public abstract void setUpData();
    public abstract void netSuccess(int whichApi,Object[] pa);
    public void neterror(int which, Throwable throwable){

    }
    @Override
    public void onData(int whichApi, Object[] pa) {
                netSuccess(whichApi,pa);
    }

    @Override
    public void error(int which, Throwable throwable) {
        Log.i(which+"错误",throwable != null && !TextUtils.isEmpty(throwable.getMessage()) ? throwable.getMessage() : "不明");
        neterror(which,throwable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContractPresenter.clear();
    }
}
