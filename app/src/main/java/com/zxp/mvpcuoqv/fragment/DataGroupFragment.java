package com.zxp.mvpcuoqv.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.BaseInfo;
import com.zxp.data.DataGroupListEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.FrameApplication;
import com.zxp.frame.IContractModel;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.DataGroupAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.interfaces.OnRecyclerItemClick;
import com.zxp.mvpcuoqv.model.DataModel;
import com.zxp.mvpcuoqv.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zxp.mvpcuoqv.adapter.DataGroupAdapter.FOCUS_TYPE;
import static com.zxp.mvpcuoqv.adapter.DataGroupAdapter.ITEM_TYPE;
import static com.zxp.mvpcuoqv.constants.JumpConstant.DATAGROUPFRAGMENT_TO_LOGIN;
import static com.zxp.mvpcuoqv.constants.JumpConstant.JUMP_KEY;

public class DataGroupFragment extends BaseMvpFragment implements OnRecyclerItemClick, DataInterfaces {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<DataGroupListEntity> mList=new ArrayList<>();
    private DataGroupAdapter mAdapter;
    @Override
    public IContractModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecyclerView(recyclerView, refreshLayout, this);
        mAdapter = new DataGroupAdapter(mList, getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClick(this);
    }
   private int page=1;
    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.NORMAL, page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.DATA_GROUP:
                BaseInfo<List<DataGroupListEntity>> listBaseInfo = (BaseInfo<List<DataGroupListEntity>>) pD[0];
                if (listBaseInfo.isSuccess()){
                    List<DataGroupListEntity> result = listBaseInfo.result;
                    int loadMode = (int) ((Object[]) pD[1])[0];
                    if (loadMode==LoadTypeConfig.REFRESH){
                        mList.clear();
                        refreshLayout.finishRefresh();
                    }else if (loadMode==LoadTypeConfig.MORE)refreshLayout.finishLoadMore();
                    mList.addAll(result);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo base = (BaseInfo) pD[0];
                int clickPos = (int)((Object[]) pD[1])[0];
                if (base.isSuccess()){
                    showToast("取消成功");
                    mList.get(clickPos).setIs_ftop(0);
                    mAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo baseSuc = (BaseInfo) pD[0];
                int clickJoinPos = (int)((Object[]) pD[1])[0];
                if (baseSuc.isSuccess()){
                    showToast("关注成功");
                    mList.get(clickJoinPos).setIs_ftop(1);
                    mAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int pos, Object[] pObjects) {
        if (pObjects != null && pObjects.length != 0){
            switch ((int)pObjects[0]){
                case ITEM_TYPE:

                    break;
                case FOCUS_TYPE:
                    boolean login = FrameApplication.isLogin();
                    if (login) {
                        DataGroupListEntity entity = mList.get(pos);
                        if (entity.isFocus()) {//已经关注，取消关注
                            mPresenter.getData(ApiConfig.CLICK_CANCEL_FOCUS, entity.getGid(), pos);//绿码
                        } else {//没有关注，点击关注
                            mPresenter.getData(ApiConfig.CLICK_TO_FOCUS, entity.getGid(), entity.getGroup_name(), pos);
                        }
                    }else {
                        startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY,DATAGROUPFRAGMENT_TO_LOGIN));
                    }
                    break;
            }
        }
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.REFRESH, 1);
        } else {
            page++;
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.MORE, page);
        }
    }
}
