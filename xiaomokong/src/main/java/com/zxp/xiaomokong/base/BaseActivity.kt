package com.zxp.xiaomokong.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zxp.frame.FrameApplication
import com.zxp.frame.LoadTypeConfig
import com.zxp.xiaomokong.interfacse.DataInterfacesKt

abstract class BaseActivity : AppCompatActivity() {
    lateinit var application: FrameApplication;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application= FrameApplication()
        Log.e("--华丽的类名--", this.javaClass.name)
    }

    fun initRecyclerView(pRectRecyclerView: RecyclerView, pRefreshLayout: SmartRefreshLayout, pdataInterfaces: DataInterfacesKt) {
        if (pRectRecyclerView != null) pRectRecyclerView.layoutManager = LinearLayoutManager(this)
        pRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pdataInterfaces.dataType(LoadTypeConfig.MORE)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {

                pdataInterfaces.dataType(LoadTypeConfig.REFRESH)
            }
        })
    }

    fun showLog(content: Any) {
        Log.e("睚眦", content.toString())
    }


    fun showToast(content: Any) {
        if (content != null) {
            Toast.makeText(applicationContext, content.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}