package com.zxp.mvpcuoqv.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.zxp.data.BaseInfo;
import com.zxp.data.LoginInfo;
import com.zxp.data.PersonHeader;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;
import com.zxp.mvpcuoqv.model.AccountModel;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginView.LoginViewCallBack{

    private Disposable mSubscribe;
    @BindView(R.id.login_view)
    LoginView loginView;
    private String phoneNum;
    @Override
    protected int getlayout() {
        return R.layout.activity_login;
    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public void setUpView() {
        loginView.setLoginViewCallBack(this);
    }

    @Override
    public void setUpData() {

    }

    @Override
    public void netSuccess(int whichApi, Object[] pa) {
        switch (whichApi){
            case ApiConfig.SEND_VERIFY:
                BaseInfo<String> stringBaseInfo = (BaseInfo<String>) pa[0];
                showToast(stringBaseInfo.result);
                goTime();
                break;
                case ApiConfig.VERIFY_LOGIN:
                    BaseInfo<LoginInfo> loginInfoBaseInfo = (BaseInfo<LoginInfo>) pa[0];
                    LoginInfo result = loginInfoBaseInfo.result;
                    result.login_name=phoneNum;
                    mApplication1907.setmLoginInfo(result);
                    contractPersenter.getData(ApiConfig.GET_HEADER_INFO);
                    case ApiConfig.GET_HEADER_INFO:
                        PersonHeader personHeader = ((BaseInfo<PersonHeader>) pa[0]).result;
                        mApplication1907.getLoginInfo().personHeader = personHeader;
                        SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication1907.getLoginInfo());
                        jump();
                        break;
        }
    }
    private void jump() {
        startActivity(new Intent(this,HomeActivity.class));
        this.finish();
    }
    private long time = 60l;
    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(goTime -> {
            loginView.getVerifyCode.setText(time - goTime + "s");
            if (time - goTime < 1) doPre();
        });
    }

    private void doPre() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
        loginView.getVerifyCode.setText("获取验证码");
    }
    @OnClick({R.id.iv_close, R.id.register_press, R.id.forgot_pwd, R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.register_press:
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                break;
        }
    }
    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        contractPersenter.getData(ApiConfig.SEND_VERIFY, phoneNum);
    }

    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        if (loginView.mCurrentLoginType == loginView.VERIFY_TYPE)
            contractPersenter.getData(ApiConfig.VERIFY_LOGIN, userName, pwd);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }
}