package com.zxp.mvpcuoqv.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.BaseInfo;
import com.zxp.data.VipListBean;
import com.zxp.data.VipLiveBean;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.VipAdapter;
import com.zxp.mvpcuoqv.adapter.VipGirdeAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.model.VipModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VIPFragment extends BaseMvpFragment<VipModel> implements DataInterfaces {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.RefreshLayout)
    SmartRefreshLayout RefreshLayout;
    private List<VipLiveBean.LiveBeanX.LiveBean> mLiveBeans=new ArrayList<>();
    private List<VipLiveBean.LunbotuBean> mLunbotuBeanList=new ArrayList<>();
    private List<VipListBean.ListBean> mListBeans=new ArrayList<>();
    private VipAdapter mVipAdapter;
    private VipGirdeAdapter mVipGirdeAdapter;

    @Override
    public VipModel setModel() {
        return new VipModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_v_i_p;
    }

    @Override
    public void setUpView() {
        initRecyclerView(recyclerView,RefreshLayout,this);
        mVipAdapter = new VipAdapter(mLiveBeans, mLunbotuBeanList, mListBeans, getContext());
        recyclerView.setAdapter(mVipAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    int page=1;
    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.VIP_LIVE, LoadTypeConfig.NORMAL);
        mPresenter.getData(ApiConfig.VIP_LIST,LoadTypeConfig.NORMAL,page);
    }
    private boolean vipList = false, banLive = false;
    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.VIP_LIVE:
                BaseInfo<VipLiveBean> vipLiveBean = (BaseInfo<VipLiveBean>) pD[0];
                if (vipLiveBean.isSuccess()) {
                    VipLiveBean result = vipLiveBean.result;
                    int loadMod = (int) ((Object[]) pD[1])[0];
                    if (loadMod==LoadTypeConfig.REFRESH){
                        mLiveBeans.clear();mLunbotuBeanList.clear();
                        RefreshLayout.finishRefresh();
                    }
                    if (result.getLive().getLive()!=null&&result.getLive().getLive().size()>0) mLiveBeans.addAll(result.getLive().getLive());
                    if (result.getLunbotu()!=null&&result.getLunbotu().size()>0)mLunbotuBeanList.addAll(result.getLunbotu());
                    banLive=true;
                    if (vipList){
                        mVipAdapter.notifyDataSetChanged();
                        banLive = false;
                    }
                }
                break;
                case ApiConfig.VIP_LIST:
                    BaseInfo<VipListBean> vipListBeanBaseInfo = (BaseInfo<VipListBean>) pD[0];
                    if (vipListBeanBaseInfo.isSuccess()){
                        int load = (int) ((Object[]) pD[1])[0];
                        if (load == LoadTypeConfig.REFRESH) {
                            mListBeans.clear();
                            RefreshLayout.finishRefresh();
                        }

                        mListBeans.addAll(vipListBeanBaseInfo.result.getList());
                        RefreshLayout.finishLoadMore();
                        mVipAdapter.notifyDataSetChanged();
                        if (load==LoadTypeConfig.MORE) {

                        }
                        vipList=true;
                        if (banLive){
                            mVipAdapter.notifyDataSetChanged();
                            vipList=false;
                        }
                    }
                    break;

        }
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            vipList = false;
            banLive = false;
            page=1;
            mPresenter.getData(ApiConfig.VIP_LIVE, LoadTypeConfig.REFRESH);
            mPresenter.getData(ApiConfig.VIP_LIST,LoadTypeConfig.REFRESH,page);
        } else {
            page++;
            mPresenter.getData(ApiConfig.VIP_LIST,LoadTypeConfig.MORE,page);
        }
    }
}
