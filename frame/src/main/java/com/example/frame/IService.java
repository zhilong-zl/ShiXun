package com.example.frame;

import com.example.data.TestInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IService {
    @GET(".")
    Observable<TestInfo> getTestData(@QueryMap Map<String, Object> params, @Query(("page_id")) int pageId);
}
