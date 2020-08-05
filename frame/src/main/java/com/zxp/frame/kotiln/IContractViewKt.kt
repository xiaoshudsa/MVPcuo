package com.zxp.frame.kotiln

interface IContractViewKt  {
    fun onData(whichApi : Int,loadType : Int,backContent : Array<Any>)
    fun error(which: Int,throwable: Throwable)
}