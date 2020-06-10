package com.zxp.frame;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManger {
    private NetManger() {
    }
    private static volatile NetManger sNetManger;
    private static IService  service;
    public static NetManger getInstance(){
        if (sNetManger==null){
            synchronized (NetManger.class){
                if (sNetManger==null){
                    sNetManger=new NetManger();
                    service=getService();
                }
            }
        }
        return sNetManger;
    }
public static IService getService(){

    return new Retrofit.Builder()
            .baseUrl(FrameApplication.getFrameApplicationContext().getString(com.zxp.frame.R.string.ad_openapi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//                .client(NetInterceptor.getNetInterceptor().getClientWithoutCache())
            .client(initClient())
            .build()
            .create(IService.class);
}
/*
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
                .client(initClient())
                .build()
                .create(IService.class);
    }*/
    private static OkHttpClient initClient(){
        return new OkHttpClient().newBuilder()
                .addInterceptor(new CommonHeadersInterceptor())
                .addInterceptor(new CommonParamsInterceptor())
                .addNetworkInterceptor(initLogInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .build();
    }
    private static Interceptor initLogInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
    public <T, O> void netWork(Observable<T> localTestInfo, final IContractPresenter pPresenter, final int whichApi, final O... o) {
        localTestInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override public void onSubscribe(Disposable d) {
                                   super.onSubscribe(d);
                        pPresenter.addObserver(d);
                               }
                               @Override
                    public void onSuccess(Object value) {
                        pPresenter.onData(whichApi, value, o);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        pPresenter.error(whichApi, e);
                    }

                }
                );
    }
}
