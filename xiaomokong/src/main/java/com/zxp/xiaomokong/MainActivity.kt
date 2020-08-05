package com.zxp.xiaomokong

import com.zxp.data.DemoData
import com.zxp.data.DemoKotlinInfo
import com.zxp.frame.ApiConfigKtx
import com.zxp.frame.LoadTypeConfig
import com.zxp.xiaomokong.adapter.DemoAdapter
import com.zxp.xiaomokong.base.BaseMvpActivityKt
import com.zxp.xiaomokong.interfacse.DataInterfacesKt
import com.zxp.xiaomokong.model.DemoModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivityKt<DemoModel>(), DataInterfacesKt {
    override fun setModel(): DemoModel = DemoModel()
    private var adapter: DemoAdapter? = null
    private var pageNum = 0
    var mListData: MutableList<DemoData> = mutableListOf()
    var map: MutableMap<String, Any>?=null;

    override fun setLayoutId(): Int = R.layout.activity_main


    override fun setUpView() {
        mPresenter.allowLoading(this)
        initRecyclerView(recyclerView, refreshLayout, this)
        adapter = recyclerView.let {
            var adapter1 = DemoAdapter(mListData, this)
            it.adapter = adapter1
            adapter1
        }
    }

    override fun setUpData() {
         map = mutableMapOf(Pair("c", "api"), Pair("a", "getList"), Pair("page_id", pageNum))
        mPresenter.goNetWork(ApiConfigKtx.SPLASH, LoadTypeConfig.NORMAL, map!!, 6)
    }

    override fun onNetSuccess(whichApi: Int, loadType: Int, backContent: Array<Any>) {
        when (whichApi) {
            ApiConfigKtx.SPLASH -> {
                if (loadType == LoadTypeConfig.MORE) refreshLayout.finishLoadMore()
                else if (loadType == LoadTypeConfig.REFRESH) {
                    refreshLayout.finishRefresh()
                    mListData.clear()
                }
                var info: DemoKotlinInfo = backContent[0] as DemoKotlinInfo
                mListData.addAll(info.datas)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun dataType(mode: Int) {
        if (mode == LoadTypeConfig.REFRESH) {
            pageNum = 0
            mPresenter.goNetWork(ApiConfigKtx.SPLASH, LoadTypeConfig.REFRESH, map!!, 6)
        } else if (mode == LoadTypeConfig.MORE) {
            pageNum += 1
            mPresenter.goNetWork(ApiConfigKtx.SPLASH, LoadTypeConfig.MORE, map!!, 6)
        }

    }

}
