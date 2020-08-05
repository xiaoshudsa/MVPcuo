package com.zxp.mvpcuoqv.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxp.frame.ContractPersenter;

import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 任小龙 on 2020/6/4.
 */
public abstract class BaseMvpFragment<M extends IContractModel> extends BaseFragment  implements IContractView {
    private M mModel;
    public ContractPersenter mPresenter;
    private Unbinder mBind;
    private boolean  inif;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(setLayoutId(), container, false);
        mBind = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModel = setModel();
        mPresenter = new ContractPersenter(this, mModel);
        setUpView();
        if (!inif){
            setUpData();
            inif=true;
        }
    }

    public abstract M setModel();

    public abstract int setLayoutId();

    public abstract void setUpView();

    public abstract void setUpData();

    public abstract void netSuccess(int whichApi, Object[] pD);

    public void netFailed(int whichApi, Throwable pThrowable){}



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.clear();
        if (mBind != null)mBind.unbind();
    }

    @Override
    public void onData(int whichApi, Object[] pa) {
        netSuccess(whichApi,pa);
    }

    @Override
    public void error(int which, Throwable throwable) {
        showLog("net work error: "+which+"error content"+ throwable != null && !TextUtils.isEmpty(throwable.getMessage()) ? throwable.getMessage() : "不明错误类型");
        netFailed(which,throwable);
    }
}
