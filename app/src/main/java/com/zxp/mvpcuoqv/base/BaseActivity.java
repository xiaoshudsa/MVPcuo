package com.zxp.mvpcuoqv.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zxp.frame.ApiConfig;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayout());

    }
    public void  initRecyclerView(RecyclerView pRectRecyclerView, SmartRefreshLayout pRefreshLayout, final DataInterfaces pdataInterfaces){
        if (pRectRecyclerView!=null)pRectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (pRefreshLayout!=null){
            pRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                    if (pdataInterfaces!=null)pdataInterfaces.dataType(LoadTypeConfig.REFRESH);
                }

                @Override
                public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                    if (pdataInterfaces!=null)pdataInterfaces.dataType(LoadTypeConfig.MORE);
                }
            });

        }
    }
    protected abstract int getlayout();
    public void showToast(Object content){
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }

}
