package com.zxp.frame;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManger {
    public NetManger() {
    }
    public static volatile NetManger sNetManger;

    public static NetManger getInstance(){
        if (sNetManger==null){
            synchronized (NetManger.class){
                if (sNetManger==null){
                    sNetManger=new NetManger();
                }
            }
        }
        return sNetManger;
    }
public <T> IService getService(T... t){
    String baseUrl = ServerAddressConfig.BASE_URL;
    if (t!=null&&t.length!=0){
        baseUrl=(String)t[0];
    }
    return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//                .client(NetInterceptor.getNetInterceptor().getClientWithoutCache())
            .client(new OkHttpClient())
            .build()
            .create(IService.class);
}
    public <T, O> void netWork(Observable<T> localTestInfo, final IContractPresenter pPresenter, final int whichApi, final int dataType, final O... o) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override public void onSubscribe(Disposable d) {
                                   super.onSubscribe(d);
                        pPresenter.addObserver(d);
                               }
                               @Override
                    public void onSuccess(Object value) {
                        pPresenter.onData(whichApi, dataType, value, o);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        pPresenter.error(whichApi, e);
                    }

                }
                );
    }
}
