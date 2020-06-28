package com.zxp.mvpcuoqv;

import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yiyatech.utils.newAdd.FontUtil;
import com.zxp.frame.FrameApplication;

public class Application1907 extends FrameApplication {
    private static Application1907 mApplication1907;
    private RefWatcher mRefWatcher;
    public int getPro() {
        return pro;
    }

    public void setPro(int pro) {
        this.pro = pro;
    }

    public   int pro;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication1907=this;

        FontUtil.initTypeface(this,"PingFang Regular.ttf");
        mRefWatcher = setupLeakCanary();
    }
    public static Application1907 getApplication1907Instance(){
        return mApplication1907;
    }

    public static Context getContext(){
        return mApplication1907.getApplicationContext();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this) /*|| API_TYPE == 3*/) {
            return RefWatcher.DISABLED;
        }
        //调用install方法后，首先程序会检测是否安装对应该程序的监听附件程序，如果没有安装进行安装
        //其次方法内部自动实现了对activity内部生命周期的监听，包括activity内部的fragment
        return LeakCanary.install(this);
    }

    /**
     * 除了activity以外，其他对象如果监听自身是否存在内存泄漏，如要在该对象销毁的时候调用该方法，并通过watch方法开始监听。
     * RefWatcher refWatcher = Application1907.getRefWatcher(this);
     * refWatcher.watch(this);
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        Application1907 leakApplication = (Application1907) context.getApplicationContext();
        return leakApplication.mRefWatcher;
    }
}
