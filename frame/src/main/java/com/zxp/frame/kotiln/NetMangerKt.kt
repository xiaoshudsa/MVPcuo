package com.zxp.frame.kotiln

import com.zxp.frame.BaseObserver
import com.zxp.frame.BaseObserverKt
import com.zxp.frame.IServerKt
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetMangerKt private constructor() {
    companion object {
        @JvmStatic
        val instance: NetMangerKt by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NetMangerKt() }

        @JvmStatic
        val IService: IServerKt = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).baseUrl("http://static.owspace.com/")
                .client(OkHttpClient()).build().create(IServerKt::class.java)
    }


    fun <T,O> netWork(localTestInfo: Observable<T>, iContractPresenter: IContractPresenterKt, whichApi: Int,dataType:Int , vararg o:O
    ) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (/*object : BaseObserver() {
                    override fun onSubscribe(d: Disposable?) {
                        super.onSubscribe(d)
                        iContractPresenter.addObService(d!!)
                    }
                    override fun onSuccess(value: Any?) {
                        if (value != null) {
                            iContractPresenter.onSuccess(whichApi,dataType,value,o)
                        }
                    }

                    override fun onFailed(e: Throwable?) {
                        if (e != null) {
                            iContractPresenter.onFailed(whichApi,e)
                        }
                    }
                }*/
                object :BaseObserverKt<T>(){
                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        iContractPresenter.addObService(d!!)
                    }
                    override fun onSuccess(value: T) {
                        if (value != null) {
                            iContractPresenter.onSuccess(whichApi,dataType,value,o)
                        }
                    }

                    override fun onFailed(e: Throwable) {
                        if (e != null) {
                            iContractPresenter.onFailed(whichApi,e)
                        }
                    }
                }
                )


        /*object : BaseObserver() {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                if (d != null) {
                    iContractPresenter.addObService(d)
                }
            }
            override fun onSuccess(value: Any) {


                iContractPresenter.onSuccess(whichApi,dataType,value!!,o)

            }

            override fun onFailed(e: Throwable) {


                iContractPresenter.onFailed(whichApi, e)

            }
        }*/

    }

}