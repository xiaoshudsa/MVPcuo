package com.zxp.frame.kotiln

import android.app.Activity
import com.zxp.frame.LoadTypeConfig
import com.zxp.frame.LoadView
import com.zxp.frame.kotiln.IContractModelKt
import com.zxp.frame.kotiln.IContractPresenterKt
import com.zxp.frame.kotiln.IContractViewKt
import io.reactivex.disposables.Disposable
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.util.*

 class ContractPresenterKt<V : IContractViewKt, M : IContractModelKt> constructor(pView: V, pModel: M) : IContractPresenterKt {
    private val defaultLoadValue = -1

    private var arrayListOf : MutableList<Disposable> = mutableListOf()

    private var mView: WeakReference<V>? = WeakReference(pView)
    private var testModel : WeakReference<M>? = WeakReference(pModel)


    private var mInstance : LoadView? = null

    private var weakReference: WeakReference<Activity>? = null
    private fun setLoadView(){
        if (weakReference != null && weakReference!!.get() != null && weakReference!!.get() is Activity) {
            val activity = weakReference!!.get()
            if (!activity!!.isFinishing && mInstance == null) {
                mInstance = LoadView(activity, null)
            }
        }
    }

    fun allowLoading(pActivity: Activity) {
        weakReference = WeakReference(pActivity)
    }

    override fun getData(whichApi: Int, vararg params: Any) {
        goNetWork(whichApi,defaultLoadValue, params as Array<Any>)

    }


    override fun goNetWork(which: Int, loadTypeConfig: Int, vararg Pa: Any) {
        if (testModel != null && testModel!!.get() != null) testModel!!.get()!!.let {
            if (loadTypeConfig==-1){
                it.getData(this, which, Pa as Array<Any>)
            }else{
                it.getDataWithLoadType(this,which,loadTypeConfig, Pa as Array<Any>)
            }
        }
        if (loadTypeConfig != LoadTypeConfig.MORE && loadTypeConfig != LoadTypeConfig.REFRESH && mInstance != null && !mInstance!!.isShowing) {
            mInstance!!.show()
        }
        setLoadView()
    }

    override fun addObService(disposable: Disposable) {
        arrayListOf.add(disposable)
    }

    override fun onSuccess(whichApi: Int, loadType: Int, vararg backContent: Any) {
        if (mView?.get()!=null) mView?.get()!!.onData(whichApi,loadType, backContent as Array<Any>)
        if (mInstance!=null&&mInstance!!.isShowing) mInstance!!.dismiss()
    }

    override fun onFailed(whichApi: Int, throwable: Throwable) {
        if (mView!!.get() != null)mView!!.get()!!.error(whichApi,throwable)
        if (mInstance != null && mInstance!!.isShowing) mInstance!!.dismiss()
    }


    fun clear() {
        for (i in arrayListOf.indices) {
            if (arrayListOf[i] != null && !arrayListOf[i].isDisposed) arrayListOf[i].dispose()
        }
        if (mView?.get() != null){
            mView!!.clear()
            mView = null
        }
        if (testModel?.get() != null){
            testModel!!.clear()
            testModel = null
        }
        if (mInstance != null && mInstance!!.isShowing){
            mInstance!!.dismiss()
        }
        if (weakReference != null && weakReference!!.get() != null){
            weakReference!!.clear()
            weakReference = null
        }
    }
}


