package com.zxp.frame.kotiln

interface IContractModelKt {
    fun getData(iContractPresenterKt: IContractPresenterKt, which :Int,params : Array<Any>)
    fun getDataWithLoadType(iContractPresenterKt: IContractPresenterKt, which :Int, loadTypeConfig: Int,params : Array<Any>)
}