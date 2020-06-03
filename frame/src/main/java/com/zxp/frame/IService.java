package com.zxp.frame;

import com.zxp.data.BaseInfo;
import com.zxp.data.Databean;
import com.zxp.data.LoginInfo;
import com.zxp.data.MainAdEntity;
import com.zxp.data.PersonHeader;
import com.zxp.data.SpecialtyChooseEntity;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 任小龙 on 2020/5/28.
 */
public interface IService {
    @GET(".")
    Observable<Databean> getTestData(@QueryMap Map<String, Object> params, @Query("page_id") int pageId);
    @GET("ad/getAd")
    Observable<BaseInfo<MainAdEntity>> getAdvert(@QueryMap Map<String,Object> pMap);

    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList();

    @GET("loginByMobileCode")
    Observable<BaseInfo<String>> getLoginVerify(@Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST("getUserHeaderForMobile")
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@FieldMap Map<String,Object> params);
}
