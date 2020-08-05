package com.zxp.xiaomokong.model

import com.zxp.frame.ApiConfig
import com.zxp.frame.ApiConfigKtx
import com.zxp.frame.IServerKt
import com.zxp.frame.kotiln.IContractModelKt
import com.zxp.frame.kotiln.IContractPresenterKt
import com.zxp.frame.kotiln.NetMangerKt

class DemoModel :IContractModelKt{
    var manager : NetMangerKt = NetMangerKt.instance
    var serviceInfo:IServerKt =NetMangerKt.IService
    override fun getData(iContractPresenterKt: IContractPresenterKt, which: Int, params: Array<Any>) {
        TODO("Not yet implemented")
    }

    override fun getDataWithLoadType(iContractPresenterKt: IContractPresenterKt, which: Int, loadTypeConfig: Int, params: Array<Any>) {
        when(which){
            ApiConfigKtx.SPLASH->{
                val mutableMap = params[0] as MutableMap<String, Any>
                manager.netWork(serviceInfo.testNetwork(mutableMap),iContractPresenterKt,which,loadTypeConfig,params)
            }
        }
    }
}