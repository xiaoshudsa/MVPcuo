package com.zxp.mvpcuoqv.base;

import android.os.Bundle;
import android.util.Log;
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
import com.zxp.mvpcuoqv.Application1907;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;

import butterknife.ButterKnife;
import okio.Buffer;

public abstract class BaseActivity extends AppCompatActivity {

    public Application1907 mApplication1907;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication1907 = new Application1907();
        setContentView(getlayout());
        ButterKnife.bind(this);
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
    public void showLog(Object content) {
        Log.e("睚眦", content.toString());
    }
    public void showToast(Object content){
        if (content!=null) {
            Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
