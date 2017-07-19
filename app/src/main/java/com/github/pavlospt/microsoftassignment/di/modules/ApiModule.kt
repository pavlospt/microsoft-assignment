package com.github.pavlospt.microsoftassignment.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class ApiModule {

  @Provides
  internal fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
  }

  @Singleton
  @Provides
  internal fun provideGson(): Gson {
    return Gson()
  }

  companion object {
    val BEERS_ENDPOINT = "https://api.punkapi.com/v2/"
  }
}
