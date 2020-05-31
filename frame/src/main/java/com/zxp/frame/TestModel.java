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
    NetManger mManger = NetManger.getInstance();
    @Override
    public void getData(final IContractPresenter iContractPresenter, final int whichApi, Object[] parms) {

        switch (whichApi) {
            case ApiConfig.TEST_GET:
                final int loadType = (int) parms[0];
                Map param = (Map) parms[1];
                int pageId = (int) parms[2];
                mManger.netWork(mManger.getService().getTestData(param, pageId), iContractPresenter, whichApi, loadType);
                break;
            case ApiConfig.ADVERT:

                break;
        }
    }
}
