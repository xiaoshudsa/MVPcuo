package com.zxp.frame.kotiln

import com.zxp.frame.LoadTypeConfig
import io.reactivex.disposables.Disposable

interface IContractPresenterKt  {
    fun getData(which :Int ,vararg  Pa :Any)
    fun goNetWork(which: Int,loadTypeConfig: Int,vararg  Pa:  Any)
    fun addObService(disposable: Disposable)
    fun onSuccess(whichApi : Int,loadType : Int,vararg backContent : Any)
    fun onFailed(whichApi: Int,throwable: Throwable)
}