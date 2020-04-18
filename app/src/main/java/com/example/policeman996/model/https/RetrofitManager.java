package com.example.policeman996.model.https;

import com.example.policeman996.Constants;
import com.orhanobut.hawk.Hawk;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

  private static RetrofitManager RetrofitManager;
  private AppApis mAppApis;
  private static Retrofit mRetrofit;

  private RetrofitManager() {
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new Interceptor() {
          @Override
          public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                .header("token", Hawk.get(Constants.TOKEN, ""))
                .header("Content-Type", "application/json; charset=UTF-8").header("Accept", "*/*")
                .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
          }
        })
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .build();

    mRetrofit = new Retrofit.Builder()
        .client(client)
        .baseUrl(AppApis.HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    mAppApis = mRetrofit.create(AppApis.class);
  }

  public static RetrofitManager getInstance() {
    if (RetrofitManager == null) {
      RetrofitManager = new RetrofitManager();
    }
    return RetrofitManager;
  }

  public AppApis getAppApis() {
    return mAppApis;
  }
}