package com.zxp.frame;

import com.zxp.data.Databean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 任小龙 on 2020/5/28.
 */
public interface IService {
    @GET(".")
    Observable<Databean> getTestData(@QueryMap Map<String, Object> params, @Query("page_id") int pageId);
}
