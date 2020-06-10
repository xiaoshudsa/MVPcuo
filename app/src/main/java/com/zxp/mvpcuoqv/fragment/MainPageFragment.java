package com.zxp.mvpcuoqv.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.BannerLiveInfo;
import com.zxp.data.BaseInfo;
import com.zxp.data.IndexCommondEntity;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.HomeAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpFragment;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;
import com.zxp.mvpcuoqv.model.MainPageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends BaseMvpFragment<MainPageModel> implements DataInterfaces {


    @BindView(R.id.rect_shi)
    RecyclerView rectShi;
    @BindView(R.id.src_atop)
    SmartRefreshLayout srcAtop;
    private List<IndexCommondEntity> bottomList = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    private List<BannerLiveInfo.Live> liveData = new ArrayList<>();
    private HomeAdapter mAdapter;
    @Override
    public MainPageModel setModel() {
        return new MainPageModel();
    }
    @Override
    public int setLayoutId() {
        return R.layout.fragment_main_page;
    }
    int page=1;
    @Override
    public void setUpView() {
        initRecyclerView(rectShi, srcAtop, this);
        mAdapter = new HomeAdapter(bottomList, bannerData, liveData, getContext());
        rectShi.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rectShi.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.HOME_BEAN,LoadTypeConfig.NORMAL, page);
        mPresenter.getData(ApiConfig.HOME_BANNER_BEAN,LoadTypeConfig.NORMAL);
    }
    private boolean mainList = false, banLive = false;

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.HOME_BEAN:
                  int loadMode = (int) ((Object[]) pD[1])[0];
                /*int loadMode = (int) pD[1];*/
                BaseInfo<List<IndexCommondEntity>> baseInfo = (BaseInfo<List<IndexCommondEntity>>) pD[0];
                if (baseInfo.isSuccess()) {
                    if (loadMode == LoadTypeConfig.REFRESH) {
                        bottomList.clear();
                        srcAtop.finishRefresh();
                    }
                    bottomList.addAll(baseInfo.result);
                    if (loadMode == LoadTypeConfig.MORE) srcAtop.finishLoadMore();mAdapter.notifyDataSetChanged();

                    mainList = true;
                    if (banLive) {
                        mAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                } else showToast("列表加载错误");
                break;
            case ApiConfig.HOME_BANNER_BEAN:
                JsonObject jsonObject = (JsonObject) pD[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if (object.getString("errNo").equals("0")) {
                     int load = (int) ((Object[]) pD[1])[0];
                    /*    int load = (int)pD[1];*/
                        if (load == LoadTypeConfig.REFRESH) {
                            bannerData.clear();liveData.clear();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        //如果该字段是以boolean类型返回的，有两种处理方式 1：写两个实体类，一个live类型是Boolean，一个是list 2：当这个字段为Boolean类型时，将其干掉
                        if (live.equals("true") || live.equals("false")) {
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        Gson gson = new Gson();
                        BannerLiveInfo info = gson.fromJson(result, BannerLiveInfo.class);
                        for (BannerLiveInfo.Carousel data : info.Carousel) {
                            bannerData.add(data.thumb);
                        }
                        liveData = info.live;
                        banLive = true;
                        if (mainList) {
                            mAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if (mode == LoadTypeConfig.REFRESH) {
            mainList = false;
            banLive = false;
            page=1;
            mPresenter.getData(ApiConfig.HOME_BEAN, LoadTypeConfig.REFRESH, page);
            mPresenter.getData(ApiConfig.HOME_BANNER_BEAN, LoadTypeConfig.REFRESH);
        } else {
            page++;
            mPresenter.getData(ApiConfig.HOME_BEAN, LoadTypeConfig.MORE, page);
        }
        }
    }

