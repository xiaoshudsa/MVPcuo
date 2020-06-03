package com.zxp.mvpcuoqv.view;

import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.yiyatech.utils.newAdd.GlideUtil;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.zxp.data.BaseInfo;
import com.zxp.data.MainAdEntity;
import com.zxp.data.SpecialtyChooseEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.IContractModel;
import com.zxp.frame.LauchModel;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.frame.util.SystemUtils;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseSplashActivity;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseSplashActivity {

    private Disposable mSubscribe;

    private SpecialtyChooseEntity.DataBean mSelectedInfo;
    private BaseInfo<MainAdEntity> mInfo;

    @Override
    public IContractModel setModel() {
        return new LauchModel();
    }

    @Override
    public void setUpView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();
    }

    @Override
    public void setUpData() {
        mSelectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId="";
        if (mSelectedInfo !=null&&!TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())){
            FrameApplication.getFrameApplication().setSelectedInfo(mSelectedInfo);
            specialtyId = mSelectedInfo.getSpecialty_id();
        }
        Point realSize = SystemUtils.getRealSize(this);
        contractPersenter.getData(ApiConfig.ADVERT,specialtyId,realSize.x,realSize.y);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pa) {

        mInfo = (BaseInfo<MainAdEntity>) pa[0];
        /*MainAdEntity result = mInfo.result;*//*
        String info_url = result.getInfo_url();
        Log.i("netSuccess",mInfo+"");*/
        GlideUtil.loadImage(advertImage, mInfo.result.getInfo_url());
        timeView.setVisibility(View.VISIBLE);
        goTime();
    }
    private int preTime = 4;

    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(pLong -> {
                    if (preTime - pLong > 0) timeView.setText(preTime - pLong + "s");
                    else jump();
                });
    }
    private void jump() {
        mSubscribe.dispose();
        startActivity(new Intent(this,mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id()) ? FrameApplication.isLogin() ? HomeActivity.class : LoginActivity.class : SubjectActivity.class ));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }

    @OnClick({R.id.advert_image, R.id.time_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.advert_image:
                if (mInfo != null) {
//                    mInfo.result.getJump_url();
                }
                break;
            case R.id.time_view:
                jump();
                break;
        }
    }
}