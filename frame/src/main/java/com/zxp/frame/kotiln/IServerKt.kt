package com.zxp.frame

import com.zxp.data.DemoKotlinInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IServerKt {
    @GET(".")
    fun testNetwork(@QueryMap params : MutableMap<String,Any> ):Observable<DemoKotlinInfo>
}