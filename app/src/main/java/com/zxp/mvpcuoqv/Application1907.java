package com.zxp.mvpcuoqv;

import android.content.Context;
import com.zxp.frame.FrameApplication;

public class Application1907 extends FrameApplication {
    private static Application1907 mApplication1907;

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
    }
    public static Application1907 getApplication1907Instance(){
        return mApplication1907;
    }

    public static Context getContext(){
        return mApplication1907.getApplicationContext();
    }
}
