package com.zxp.mvpcuoqv.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.BaseInfo;
import com.zxp.data.EssenceBean;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.IContractModel;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.DataGroupAdapter;
import com.zxp.mvpcuoqv.adapter.EssenceAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.model.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecentlyBestFragment extends BaseMvpFragment implements DataInterfaces {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.RefreshLayout)
    SmartRefreshLayout RefreshLayout;
    private EssenceAdapter mAdapter;
    private List<EssenceBean> mList=new ArrayList<>();
    @Override
    public IContractModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_v_i_p;
    }
     int page=1;
    @Override
    public void setUpView() {
        initRecyclerView(recyclerView, RefreshLayout, this);
        mAdapter = new EssenceAdapter(mList, getContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.DATAMODEL, LoadTypeConfig.NORMAL, page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.DATAMODEL:
                BaseInfo<List<EssenceBean>> listBaseInfo = (BaseInfo<List<EssenceBean>>) pD[0];
                if (listBaseInfo.isSuccess()){
                    List<EssenceBean> result = listBaseInfo.result;
                    int loadMode = (int) ((Object[]) pD[1])[0];
                    if (loadMode==LoadTypeConfig.REFRESH){
                        mList.clear();
                        RefreshLayout.finishRefresh();
                    }else if (loadMode==LoadTypeConfig.MORE)RefreshLayout.finishLoadMore();
                    mList.addAll(result);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    public void dataType(int mode) {
        switch (mode){
            case LoadTypeConfig.REFRESH:
                page=1;
                mPresenter.getData(ApiConfig.DATAMODEL, LoadTypeConfig.REFRESH, page);
                break;
                case LoadTypeConfig.MORE:
                    mPresenter.getData(ApiConfig.DATAMODEL, LoadTypeConfig.MORE, page++);
                    break;
        }
    }
}
