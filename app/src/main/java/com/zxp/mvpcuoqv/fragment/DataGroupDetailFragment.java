package com.zxp.mvpcuoqv.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yiyatech.utils.newAdd.GlideUtil;
import com.zxp.data.BaseInfo;
import com.zxp.data.GroupDetailEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.frame.PrameHashMap;
import com.zxp.frame.constants.ConstantKey;
import com.zxp.frame.constants.Constants;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.DataGroupDetailBottomAdapter;
import com.zxp.mvpcuoqv.adapter.GroupDetailCenterTabAdapter;
import com.zxp.mvpcuoqv.adapter.GroupDetailPopAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.model.DataModel;
import com.zxp.mvpcuoqv.view.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import razerdp.design.GroupTabPopup;

public class DataGroupDetailFragment extends BaseMvpFragment<DataModel> implements DataInterfaces {

    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_member_num)
    TextView tvMemberNum;
    @BindView(R.id.tv_post_num)
    TextView tvPostNum;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.groupBack)
    ImageView groupBack;
    @BindView(R.id.groupTitle)
    TextView groupTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabRecycle)
    RecyclerView tabRecycle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private String mGid;

    private List<GroupDetailEntity.Tag> mTabListData = new ArrayList<>();
    private List<GroupDetailEntity.Thread> mBottomData = new ArrayList<>();
    private List<GroupDetailEntity.Tag.SelectsBean> mPopData = new ArrayList<>();
    private List<String> mContains = new ArrayList<>();
    private DataGroupDetailBottomAdapter mDataGroupDetailBottomAdapter;
    private GroupDetailCenterTabAdapter mGroupDetailCenterTabAdapter;
    private HomeActivity mActivity;
    private GroupTabPopup mGroupTabPopup;
    private GroupDetailPopAdapter mPopAdapter;

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_data_group_detail;
    }

    @Override
    public void setUpView() {
        mActivity = (HomeActivity) getActivity();
        if (getArguments() != null) {
            mGid = getArguments().getString(ConstantKey.GROU_TO_DETAIL_GID);
        }
        groupTitle.setVisibility(View.GONE);
        appBar.addOnOffsetChangedListener((pAppBarLayout, verticalOffset) -> {
            boolean space = Math.abs(verticalOffset) >= tvName.getBottom();
            groupTitle.setVisibility(space ? View.VISIBLE : View.GONE);
            toolbar.setBackgroundColor(space ? setColor(R.color.app_theme) : Color.TRANSPARENT);
        });
        initRecyclerView(recyclerView, refreshLayout, this);
        mDataGroupDetailBottomAdapter = new DataGroupDetailBottomAdapter(getContext(), mBottomData);
        recyclerView.setAdapter(mDataGroupDetailBottomAdapter);
        LinearLayoutManager ma = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        tabRecycle.setLayoutManager(ma);
        mGroupDetailCenterTabAdapter = new GroupDetailCenterTabAdapter(getContext(), mTabListData);
        tabRecycle.setAdapter(mGroupDetailCenterTabAdapter);
        mGroupDetailCenterTabAdapter.setOnRecyclerItemClick((pos, pObjects) -> {
            if (mTabListData.get(pos).getSelects() != null)
                clickCenterTab(pos);
            else showToast("该标签下没有选择条件");
        });
    }

    /**
     * 中间tab标签的点击逻辑
     *
     * @param pos
     */
    private int currentTabPos = -1;

    private void clickCenterTab(int pos) {
        currentTabPos = pos;
        GroupDetailEntity.Tag tag = mTabListData.get(pos);
        tag.setSelecting(!tag.isSelecting());
        if (mPopData.size() != 0) mPopData.clear();
        if (mContains.size() != 0)mContains.clear();
        mPopData.addAll(tag.getSelects());
        mContains.addAll(tag.getContainsName());
        mGroupDetailCenterTabAdapter.notifyItemChanged(pos);
        if (mGroupTabPopup == null) {
            mGroupTabPopup = new GroupTabPopup(getActivity());
            mGroupTabPopup.popRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mPopAdapter = new GroupDetailPopAdapter(getContext(), mPopData,mContains);
            mGroupTabPopup.popRecycle.setAdapter(mPopAdapter);
        }
        mPopAdapter.notifyDataSetChanged();
        mGroupTabPopup.showPopupWindow(tabRecycle);
        mGroupTabPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tag.setSelecting(!tag.isSelecting());
                mGroupDetailCenterTabAdapter.notifyItemChanged(pos);
            }
        });
        mPopAdapter.setOnRecyclerItemClick((pos1, pObjects) -> {
            popTabClick(pos1);
        });
    }

    /**
     * popupWindow中的标签点击逻辑
     *
     * @param pos
     */
    private int currentPopPos = -1;

    private void popTabClick(int pos) {
        currentPopPos = pos;
        GroupDetailEntity.Tag.SelectsBean selectsBean = mPopData.get(pos);
        tags = selectsBean.getUrl();
        getFooterData(LoadTypeConfig.REFRESH);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.GROUP_DETAIL, mGid);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.GROUP_DETAIL:
                BaseInfo<GroupDetailEntity> baseInfo = (BaseInfo<GroupDetailEntity>) pD[0];
                if (baseInfo.isSuccess()) {
                    GroupDetailEntity groupDetailEntity = baseInfo.result;
                    setDetailData(groupDetailEntity);
                    mBottomData.addAll(baseInfo.result.getThread_list());
                    mDataGroupDetailBottomAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA:
                String s = pD[0].toString();
                try {
                    JSONObject bigJson = new JSONObject(s);
                    int errNo = bigJson.getInt("errNo");
                    if (errNo == 0) {
                        JSONObject result = bigJson.getJSONObject("result");
                        String thread_list = result.getString("thread_list");
                        Gson gson = new Gson();
                        List<GroupDetailEntity.Thread> list = gson.fromJson(thread_list, new TypeToken<List<GroupDetailEntity.Thread>>() {
                        }.getType());
                        int loadType = (int) pD[1];
                        if (loadType == LoadTypeConfig.REFRESH) {
                            refreshLayout.finishRefresh();
                            mBottomData.clear();
                        } else if (loadType == LoadTypeConfig.MORE) {
                            refreshLayout.finishLoadMore();
                            if (list.size() < Constants.LIMIT_NUM)
                                refreshLayout.setNoMoreData(true);
                        }
                        mBottomData.addAll(list);
                        mDataGroupDetailBottomAdapter.notifyDataSetChanged();

                        GroupDetailEntity.Tag tag = mTabListData.get(currentTabPos);
                        List<String> containsName = tag.getContainsName();
                        String name = tag.getSelects().get(currentPopPos).getName();
                        //有一个默认选中的，第一次加载一成功加入选中集合，为防止以后重复添加，这里在点击时（已经完成第一次加载），将其设为0；
                        if (tag.getOn() == 1)tag.setOn(0);
                        if (containsName.contains(name)){
                            containsName.clear();
                        } else {
                            containsName.clear();
                            containsName.add(name);
                        }
                        tag.setContainsName(containsName);
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                mGroupTabPopup.dismiss();
                break;
        }
    }

    private int page = 1;
    private String tags = "";

    private void getFooterData(int pNormal) {
        PrameHashMap add = new PrameHashMap().add("gid", mGid).add("page", page).add("limit", Constants.LIMIT_NUM);
        if (!TextUtils.isEmpty(tags)) add.add("tagall", tags);
        mPresenter.getData(ApiConfig.GROUP_DETAIL_FOOTER_DATA, pNormal, add);
    }

    private void setDetailData(GroupDetailEntity groupInfo) {
        GroupDetailEntity.Group groupInner = groupInfo.getGroupinfo();
        tvName.setText(groupInner.getGroup_name());
        groupTitle.setText(groupInner.getGroup_name());
        tvMemberNum.setText("成员 " + groupInner.getMember_num() + " 人");
        tvPostNum.setText("资料 " + groupInner.getThread_num() + " 篇");
        tvFocus.setText(groupInner.getIs_add() == 1 ? "已关注" : "关注");
        GlideUtil.loadCornerImage(ivThumb, groupInner.getLogo(), null, 10);
        GlideUtil.loadBlurredBackground(groupInner.getLogo(), imageBack);
        mTabListData.addAll(groupInfo.getTag_arr());
        mGroupDetailCenterTabAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.groupBack)
    public void onViewClicked() {
//        mActivity.mProjectController.navigateUp();
        mActivity.navController.navigate(R.id.dataGroup_back_to_home);
    }

    @Override
    public void dataType(int mode) {
        page = mode == LoadTypeConfig.REFRESH ? 1 : page + 1;
        getFooterData(mode);
    }
}
