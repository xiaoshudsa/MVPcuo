package com.zxp.frame;

import com.google.gson.JsonObject;
import com.zxp.data.BaseInfo;
import com.zxp.data.CourseDetailInfo;
import com.zxp.data.CourseListInfo;
import com.zxp.data.DataGroupListEntity;
import com.zxp.data.Databean;
import com.zxp.data.EssenceBean;
import com.zxp.data.GroupDetailEntity;
import com.zxp.data.IndexCommondEntity;
import com.zxp.data.LessonComment;
import com.zxp.data.LoginInfo;
import com.zxp.data.MainAdEntity;
import com.zxp.data.PersonHeader;
import com.zxp.data.SpecialtyChooseEntity;
import com.zxp.data.VipListBean;
import com.zxp.data.VipLiveBean;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by 任小龙 on 2020/5/28.
 */
public interface IService {
    @GET(".")
    Observable<Databean> getTestData(@QueryMap Map<String, Object> params, @Query("page_id") int pageId);
    @GET
    Observable<BaseInfo<MainAdEntity>> getAdvert(@Url String url,@QueryMap Map<String, Object> pMap);

    @GET
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList(@Url String url);

    @GET
    Observable<BaseInfo<String>> getLoginVerify( @Url String url,@Query("mobile") String mobile);

    @GET
    Observable<BaseInfo<LoginInfo>> loginByVerify(@Url String url,@QueryMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@Url String url,@FieldMap Map<String,Object> params);
    @GET
    Observable<BaseInfo<List<IndexCommondEntity>>> getCommonList(@Url String url, @QueryMap Map<String,Object> params);

    @GET
    Observable<JsonObject> getBannerLive(@Url String url, @QueryMap Map<String,Object> params);
    @GET
    Observable<BaseInfo<CourseListInfo>> getCourseChildData(@Url String url, @QueryMap Map<String,Object> params);
    @GET
    Observable<BaseInfo<VipLiveBean>> getVipLive(@Url String url,@QueryMap Map<String,Object> params);
    @GET
    Observable<BaseInfo<VipListBean>> getVipList(@Url String url,@QueryMap Map<String,Object> params);
    @GET
    Observable<BaseInfo<List<DataGroupListEntity>>> getGroupList(@Url String url, @QueryMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@Url String url, @FieldMap Map<String,Object> params);
    @POST
    @FormUrlEncoded
    Observable<BaseInfo> focus(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkVerifyCode(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkPhoneIsUsed(@Url String url, @Field("mobile")Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> sendRegisterVerify(@Url String url, @Field("mobile")Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkName(@Url String url, @Field("username")Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> registerCompleteWithSubject(@Url String url, @FieldMap Map<String,Object> params);
    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByAccount(@Url String url, @FieldMap Map<String,Object> params);
    @GET
    Observable<JsonObject> getWechatToken(@Url String url, @QueryMap Map<String,Object> parmas);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByWechat(@Url String url, @FieldMap Map<String,Object> params);
    @POST
    @FormUrlEncoded
    Observable<BaseInfo> bindThirdAccount(@Url String url, @FieldMap Map<String,Object> params);

    @GET
    Observable<BaseInfo<GroupDetailEntity>> getGroupDetail(@Url String url, @Query("gid") Object object);

    @GET
    Observable<JsonObject> getGroupDetailFooterData(@Url String url, @QueryMap Map<String,Object> parmas);
    @POST
    @FormUrlEncoded
    Observable<BaseInfo<CourseDetailInfo>> getCourseDetail(@Url String url, @FieldMap Map<String, Object> pMap);

    @GET
    Observable<BaseInfo<List<LessonComment>>> getCommentList(@Url String url,@QueryMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<List<EssenceBean>>> getRecentlyBest(@Url String url, @FieldMap Map<String, Object> pMap);
}
