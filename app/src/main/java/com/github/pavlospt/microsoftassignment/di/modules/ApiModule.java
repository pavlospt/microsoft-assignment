package com.github.pavlospt.microsoftassignment.di.modules;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApiModule {

  public static final String BEERS_ENDPOINT = "https://api.punkapi.com/v2/";

  @Provides
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder().addInterceptor(
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build();
  }

  @Singleton
  @Provides
  Gson provideGson() {
    return new Gson();
  }
}
