package com.zxp.mvpcuoqv.view;

import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxp.data.Databean;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.IContractModel;
import com.zxp.frame.IContractView;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.model.TestModel;
import com.zxp.frame.PrameHashMap;
import com.zxp.mvpcuoqv.R;
import com.zxp.mvpcuoqv.adapter.TestAdapter;
import com.zxp.mvpcuoqv.base.BaseMvpActivity;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;

import java.util.List;
import java.util.Map;



public class MainActivity extends BaseMvpActivity implements IContractView {

/*    @BindView(R.id.recy_view)
    RecyclerView recyView;
    @BindView(R.id.RefreshLayout)
    SmartRefreshLayout RefreshLayout;*/
    private int pageId = 0;
    private Map<String, Object> prameHashMap;
    private TestAdapter testAdapter;
    private SmartRefreshLayout RefreshLayout;
    private RecyclerView recy_view;


    @Override
    protected int getlayout() {
        return R.layout.activity_main;
    }

    @Override
    public IContractModel setModel() {
        return new TestModel();
    }

    @Override
    public void setUpView() {
        prameHashMap = new PrameHashMap().add("c", "api").add("a", "getList");
        recy_view = findViewById(R.id.recy_view);
        RefreshLayout = findViewById(R.id.RefreshLayout);
        initRecyclerView(recy_view,RefreshLayout,  new DataInterfaces() {
            @Override
            public void dataType(int mode) {
                if (mode==LoadTypeConfig.REFRESH){
                    pageId++;
                    contractPersenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, prameHashMap, pageId);

                }else {
                    pageId = 0;
                    contractPersenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE, prameHashMap, pageId);
                }
            }
        });

        testAdapter = new TestAdapter(this);
        recy_view.setAdapter(testAdapter);

    }

    @Override
    public void setUpData() {

        contractPersenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL, prameHashMap, pageId);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pa) {
        switch (whichApi) {
            case ApiConfig.TEST_GET:
                if ((int)pa[0] == LoadTypeConfig.MORE) {
                    RefreshLayout.finishLoadMore();
                } else if ((int)pa[0] == LoadTypeConfig.REFRESH) {
                    RefreshLayout.finishRefresh();
                    testAdapter.clear();
                }
                List<Databean.DatasBean> datas = ((Databean) pa[0]).getDatas();
                testAdapter.add(datas);
                break;
        }
    }


    @Override
    public void onData(int whichApi, Object[] pa) {

    }

    @Override
    public void error(int which, Throwable throwable) {
        Toast.makeText(MainActivity.this, throwable.getMessage() != null ? throwable.getMessage() : "网络请求发生错误", Toast.LENGTH_SHORT).show();
    }

}
