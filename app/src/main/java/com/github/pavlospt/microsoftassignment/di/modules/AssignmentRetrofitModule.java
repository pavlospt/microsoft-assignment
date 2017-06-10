package com.github.pavlospt.microsoftassignment.di.modules;

import com.github.pavlospt.microsoftassignment.api.dao.BeersDAO;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.github.pavlospt.microsoftassignment.di.modules.ApiModule.BEERS_ENDPOINT;

@Module(includes = ApiModule.class)
public class AssignmentRetrofitModule {

  @Provides
  Retrofit providesAssignmentRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(BEERS_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(okHttpClient)
        .build();
  }

  @Provides
  BeersDAO provideBeersDAO(Retrofit retrofit) {
    return retrofit.create(BeersDAO.class);
  }
}
