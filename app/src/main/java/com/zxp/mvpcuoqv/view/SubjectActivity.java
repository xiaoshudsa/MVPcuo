package com.zxp.mvpcuoqv.view;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import com.zxp.data.BaseInfo;
import com.zxp.data.SpecialtyChooseEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.FrameApplication;
import com.zxp.mvpcuoqv.Application1907;
import com.zxp.mvpcuoqv.model.LauchModel;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.SubjectAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxp.mvpcuoqv.constants.JumpConstant.JUMP_KEY;
import static com.zxp.mvpcuoqv.constants.JumpConstant.SPLASH_TO_SUB;
import static com.zxp.mvpcuoqv.constants.JumpConstant.SUB_TO_LOGIN;

public class SubjectActivity extends BaseMvpActivity<LauchModel> {
    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.more_content)
    TextView moreContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SubjectAdapter mAdapter;
    private String mFrom;
    @Override
    protected int getlayout() {
        return R.layout.activity_subject;
    }

    @Override
    public LauchModel setModel() {
        return new LauchModel();
    }

    @Override
    public void setUpView() {
        mFrom = getIntent().getStringExtra(JUMP_KEY);
        titleContent.setText(getString(R.string.select_subject));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SubjectAdapter(mListData, this);
        recyclerView.setAdapter(mAdapter);
        moreContent.setText("完成");
        moreContent.setOnClickListener(v->{
            if (Application1907.getSelectedInfo() == null){
                showToast("请选择专业");
                return;
            }
            if (mFrom.equals(SPLASH_TO_SUB)){
                if (Application1907.isLogin()){
                    startActivity(new Intent(SubjectActivity.this,HomeActivity.class));
                } else {
                    startActivity(new Intent(SubjectActivity.this,LoginActivity.class).putExtra(JUMP_KEY,SUB_TO_LOGIN));
                }
            }
            finish();
        });
    }

    @Override
    public void setUpData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST));
            mAdapter.notifyDataSetChanged();
        } else
            contractPersenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) pD[0];
                mListData.addAll(info.result);
                mAdapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this,ConstantKey.SUBJECT_LIST,mListData);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this, ConstantKey.SUBJECT_SELECT, FrameApplication.getSelectedInfo());
    }

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        finish();
    }


}