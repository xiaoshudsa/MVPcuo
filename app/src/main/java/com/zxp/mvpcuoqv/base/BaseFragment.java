package com.zxp.mvpcuoqv.base;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zxp.frame.LoadTypeConfig;
import com.zxp.mvpcuoqv.interfaces.DataInterfaces;


public class BaseFragment extends Fragment {
    /**
     * recyclerview在整个项目中使用比较频繁，将公共代码进行抽取
     * @param pRecyclerView 要操作的recyclerview
     * @param pRefreshLayout 如果有刷新和加载更多的问题，所使用的smartRefreshLayout
     * @param pDataListener 刷新和加载的监听，如果实际使用中部涉及到刷新和加载更多，直接传null
     */
    public void initRecyclerView(RecyclerView pRecyclerView, SmartRefreshLayout pRefreshLayout, DataInterfaces pDataListener) {
        if (pRecyclerView != null) pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (pRefreshLayout != null && pDataListener != null) {
            pRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    pDataListener.dataType(LoadTypeConfig.MORE);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    pDataListener.dataType(LoadTypeConfig.REFRESH);
                }
            });

        }
    }

    public void showLog(Object content) {
        Log.e("睚眦", content.toString());
    }

    public void showToast(Object content) {
        Toast.makeText(getContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
}
