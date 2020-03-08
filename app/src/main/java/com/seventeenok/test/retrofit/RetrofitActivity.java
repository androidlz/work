package com.seventeenok.test.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;

//import com.google.gson.Gson;
import com.seventeenok.test.R;

import java.io.IOException;
import java.util.List;

public class RetrofitActivity extends AppCompatActivity {
//    Gson gson =new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")

                .build();
        CommonApi commonApi=retrofit.create(CommonApi.class);
        commonApi.getArticle().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
//                System.out.println("chenggong"+gson.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        });

    }
}
