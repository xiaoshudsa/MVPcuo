package com.zxp.mvpcuoqv.view;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhulong.eduvideo.wxapi.WXEntryActivity;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.zxp.data.BaseInfo;
import com.zxp.data.LoginInfo;
import com.zxp.data.PersonHeader;
import com.zxp.data.ThirdLoginData;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;
import com.zxp.mvpcuoqv.model.AccountModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.zxp.mvpcuoqv.constants.JumpConstant.JUMP_KEY;
import static com.zxp.mvpcuoqv.constants.JumpConstant.SPLASH_TO_LOGIN;
import static com.zxp.mvpcuoqv.constants.JumpConstant.SUB_TO_LOGIN;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginView.LoginViewCallBack {

    private Disposable mSubscribe;
    @BindView(R.id.login_view)
    LoginView loginView;
    private String phoneNum;
    private String mFromType;
    private ThirdLoginData mThirdData;
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
        mFromType = getIntent().getStringExtra(JUMP_KEY);
        loginView.setLoginViewCallBack(this);
    }

    @Override
    public void setUpData() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantKey.BIND_BACK_LOGIN){
            contractPersenter.getData(ApiConfig.POST_WE_CHAT_LOGIN_INFO, mThirdData);
        }
    }
    @Override
    public void netSuccess(int whichApi, Object[] pa) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY:
                BaseInfo<String> stringBaseInfo = (BaseInfo<String>) pa[0];
                if (stringBaseInfo.isSuccess()) {
                    showToast(stringBaseInfo.result);
                    goTime();
                } else showToast("验证码发送太频繁，请稍后重试");
                break;
            case ApiConfig.VERIFY_LOGIN:
                BaseInfo<LoginInfo> loginInfoBaseInfo = (BaseInfo<LoginInfo>) pa[0];
                Log.i("Tag", loginInfoBaseInfo.errNo + "");
                LoginInfo result = loginInfoBaseInfo.result;
                result.login_name = phoneNum;
                mApplication1907.setmLoginInfo(result);
                contractPersenter.getData(ApiConfig.GET_HEADER_INFO);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                BaseInfo<LoginInfo> baseInfo = (BaseInfo<LoginInfo>) pa[0];
                if(baseInfo.isSuccess()) {
                    LoginInfo loginInfo = baseInfo.result;
                    if (!TextUtils.isEmpty(phoneNum)) loginInfo.login_name = phoneNum;
                    mApplication1907.setmLoginInfo(loginInfo);
                    contractPersenter.getData(ApiConfig.GET_HEADER_INFO);
                }else if (baseInfo.errNo == 1300){
                Intent intent = new Intent(this, ThirdAccoutBindActivity.class);
                startActivityForResult(intent.putExtra("thirdData",mThirdData),ConstantKey.LOGIN_TO_BIND);
            } else {
                showToast(baseInfo.msg);
            }
                break;
            case ApiConfig.GET_HEADER_INFO:
                PersonHeader personHeader = ((BaseInfo<PersonHeader>) pa[0]).result;
                mApplication1907.getLoginInfo().personHeader = personHeader;
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication1907.getLoginInfo());
                jump();
                break;
            case ApiConfig.GET_WE_CHAT_TOKEN:
                JSONObject allJson = null;
                try {
                    allJson = new JSONObject(pa[0].toString());
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                mThirdData = new ThirdLoginData(3);
                mThirdData.setOpenid(allJson.optString("openid"));
                mThirdData.token = allJson.optString("access_token");
                mThirdData.refreshToken = allJson.optString("refresh_token");
                mThirdData.utime = allJson.optLong("expires_in") * 1000;
                mThirdData.unionid = allJson.optString("unionid");
                contractPersenter.getData(ApiConfig.POST_WE_CHAT_LOGIN_INFO, mThirdData);
                break;
        }
    }

    private void jump() {
        if (mFromType.equals(SPLASH_TO_LOGIN) || mFromType.equals(SUB_TO_LOGIN))
            startActivity(new Intent(this, HomeActivity.class));
        finish();
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
                if (!TextUtils.isEmpty(mFromType) && (mFromType.equals(SUB_TO_LOGIN) || mFromType.equals(SPLASH_TO_LOGIN))) {
                    startActivity(new Intent(this, HomeActivity.class));
                }
                finish();
                break;
            case R.id.register_press:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                doWechatLogin();
                break;
        }
    }

    private void doWechatLogin() {
        WXEntryActivity.setOnWeChatLoginResultListener(it -> {
            int errorCode = it.getIntExtra("errorCode", 0);
            String normalCode = it.getStringExtra("normalCode");
            switch (errorCode) {
                case 0:
                    showLog("用户已同意微信登录");
                    contractPersenter.getData(ApiConfig.GET_WE_CHAT_TOKEN, normalCode);
                    break;
                case -4:
                    showToast("用户拒绝授权");
                    break;
                case -2:
                    showToast("用户取消");
                    break;

            }
        });
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, null);
        weChatApi.registerApp(ConstantKey.WX_APP_ID);
        if (weChatApi.isWXAppInstalled()) {
            doWeChatLogin();
        } else showToast("请先安装微信");
    }

    private void doWeChatLogin() {
        SendAuth.Req request = new SendAuth.Req();
//        snsapi_base 和snsapi_userinfo  静态获取和同意后获取
        request.scope = "snsapi_userinfo";
        request.state = "com.zhulong.eduvideo";
        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, ConstantKey.WX_APP_ID);
        weChatApi.sendReq(request);
    }

    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        contractPersenter.getData(ApiConfig.SEND_VERIFY, phoneNum);
    }

    @Override
    public void loginPress(int type, String userName, String pwd) {
        doPre();
        Log.i("Tag", userName + pwd);
        if (loginView.mCurrentLoginType == loginView.VERIFY_TYPE)
            contractPersenter.getData(ApiConfig.VERIFY_LOGIN, userName, pwd);
        else contractPersenter.getData(ApiConfig.ACCOUNT_LOGIN, userName, pwd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }
}