package com.github.pavlospt.microsoftassignment.di.modules

import com.github.pavlospt.microsoftassignment.repo.remote.services.BeersService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

import com.github.pavlospt.microsoftassignment.di.modules.ApiModule.Companion.BEERS_ENDPOINT

@Module(includes = arrayOf(ApiModule::class))
class AssignmentRetrofitModule {

  @Provides
  fun providesAssignmentRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(ApiModule.BEERS_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(okHttpClient)
        .build()
  }

  @Provides
  fun provideBeersService(retrofit: Retrofit): BeersService {
    return retrofit.create(BeersService::class.java)
  }
}
