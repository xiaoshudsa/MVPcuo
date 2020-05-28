package com.zxp.frame;

import com.zxp.data.Databean;

import java.util.Map;
import java.util.jar.Manifest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestModel implements IContractModel {

    @Override
    public void getData(final IContractPresenter iContractPresenter, final int whichApi, Object[] parms) {
        final int loadType = (int) parms[0];
        Map parm = (Map) parms[1];
        int pageid = (int) parms[2];
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://static.owspace.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        build.create(IService.class)
                .getTestData(parm,pageid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Databean>() {
                    @Override
                    public void accept(Databean databean) throws Exception {
                    iContractPresenter.onData(whichApi,loadType,databean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iContractPresenter.error(whichApi,throwable);
                    }
                });
    }
}
