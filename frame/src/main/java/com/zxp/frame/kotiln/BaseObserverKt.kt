package com.zxp.frame

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

 abstract class BaseObserverKt<T>() : Observer<T> {
    private lateinit var mDisposable: Disposable

    override fun onComplete() {

        addDispose()
    }

    override fun onSubscribe(d: Disposable) {

        mDisposable = d
    }

    override fun onNext(value: T) {
        onSuccess(value)
        addDispose()
    }

    override fun onError(e: Throwable) {
        onFailed(e)
        addDispose()
    }
    abstract fun onSuccess(value: T)
    abstract fun onFailed(e: Throwable)
    fun addDispose() {
        if (mDisposable != null && !mDisposable.isDisposed) mDisposable.dispose()
    }
}