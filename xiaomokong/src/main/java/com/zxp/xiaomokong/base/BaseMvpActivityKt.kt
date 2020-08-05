package com.zxp.xiaomokong.base

import android.os.Bundle
import android.os.PersistableBundle
import com.zxp.frame.kotiln.ContractPresenterKt
import com.zxp.frame.kotiln.IContractModelKt
import com.zxp.frame.kotiln.IContractViewKt

 abstract class BaseMvpActivityKt<M :IContractModelKt >: BaseActivity(),IContractViewKt {
   private lateinit var setModel: M
    lateinit var mPresenter: ContractPresenterKt<*, M>
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(setLayoutId())
         setModel = setModel()
         mPresenter = ContractPresenterKt(this, setModel)
         setUpView()
         setUpData()
     }
     abstract fun setModel(): M
     abstract fun setLayoutId(): Int
     abstract fun setUpView()
     abstract fun setUpData()
     abstract fun onNetSuccess(whichApi: Int, loadType: Int,backContent: Array<Any>)
     override fun onData(whichApi: Int, loadType: Int, backContent: Array<Any>) {

         onNetSuccess(whichApi, loadType,backContent)
     }

     override fun error(which: Int, throwable: Throwable) {

        showLog("${"net work error,error api : "}$which${"error content : "}${if (throwable!=null&&!throwable.message.isNullOrBlank())throwable.message else "no content"}")
     }
     override fun onDestroy() {
         super.onDestroy()
         mPresenter.clear()
     }

 }