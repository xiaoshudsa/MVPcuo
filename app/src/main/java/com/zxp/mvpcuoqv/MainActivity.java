package com.zxp.mvpcuoqv;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zxp.data.Databean;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.ContractPersenter;
import com.zxp.frame.IContractView;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.frame.PrameHashMap;
import com.zxp.mvpcuoqv.adapter.TestAdapter;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IContractView {

    private RecyclerView recy_view;
    private SmartRefreshLayout RefreshLayout;
    private int pageId = 0;
    private ContractPersenter contractPersenter;
    private Map<String, Object> prameHashMap;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        contractPersenter = new ContractPersenter(this);
        contractPersenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL,prameHashMap,pageId);
    }

    private void initView() {
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        RefreshLayout = (SmartRefreshLayout) findViewById(R.id.RefreshLayout);
        prameHashMap = new PrameHashMap().add("c","api").add("a","getList");
        RefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                pageId++;
                contractPersenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, prameHashMap,pageId);
            }

            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                pageId=0;
                contractPersenter.getData(ApiConfig.TEST_GET,LoadTypeConfig.MORE, prameHashMap,pageId);
            }
        });
        testAdapter = new TestAdapter(this);
        recy_view.setAdapter(testAdapter);
        recy_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onData(int whichApi, int loadType, Object[] pa) {
        switch (whichApi){
            case ApiConfig.TEST_GET:
                if (loadType==LoadTypeConfig.MORE){
                    RefreshLayout.finishLoadMore();
                } else if (loadType == LoadTypeConfig.REFRESH){
                    RefreshLayout.finishRefresh();
                    testAdapter.clear();
                }
                List<Databean.DatasBean> datas = ((Databean)pa[0]).getDatas();

                testAdapter.add(datas);
                break;
                }
        }


    @Override
    public void error(int which, Throwable throwable) {
        Toast.makeText(MainActivity.this, throwable.getMessage()!=null ? throwable.getMessage():"网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
