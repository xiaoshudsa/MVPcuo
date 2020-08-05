package com.zxp.mvpcuoqv.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.zxp.data.BaseInfo;
import com.zxp.data.ThirdLoginData;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;
import com.zxp.mvpcuoqv.model.AccountModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ThirdAccoutBindActivity extends BaseMvpActivity<AccountModel> {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    private ThirdLoginData mThirdData;

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }


    @Override
    public void setUpView() {
        mThirdData = getIntent().getParcelableExtra("thirdData");
    }

    @Override
    public void setUpData() {
        titleContent.setText("绑定账号");
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.BIND_ACCOUNT:
                BaseInfo baseInfo = (BaseInfo) pD[0];
                setResult(ConstantKey.BIND_BACK_LOGIN);
                finish();
                break;
        }
    }

    @OnClick({R.id.back_image, R.id.button_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.button_bind:
                if (TextUtils.isEmpty(account.getText().toString())) {
                    showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showToast("密码不能为空");
                    return;
                }
                mContractPresenter.getData(ApiConfig.BIND_ACCOUNT, account.getText().toString(), password.getText().toString(), mThirdData);
                break;
        }
    }

    @Override
    protected int getlayout() {
        return R.layout.activity_third_accout_bind;
    }
}
