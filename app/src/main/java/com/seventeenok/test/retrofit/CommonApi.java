package com.seventeenok.test.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CommonApi {
    @GET("wxarticle/chapters/json")
    Call<List<Article>> getArticle();

}
