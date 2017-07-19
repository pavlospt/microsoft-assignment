package com.github.pavlospt.microsoftassignment.di.modules

import android.content.Context
import com.github.pavlospt.microsoftassignment.AssignmentApp
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(AssignmentRetrofitModule::class))
class AssignmentAppModule(private val application: AssignmentApp) {

  @Provides
  fun provideApplicationContext(): Context {
    return application
  }
}
