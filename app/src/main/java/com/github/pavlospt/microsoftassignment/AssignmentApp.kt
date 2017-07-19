package com.github.pavlospt.microsoftassignment

import android.app.Application
import android.content.Context
import com.github.pavlospt.microsoftassignment.di.components.AssignmentAppComponent
import com.github.pavlospt.microsoftassignment.di.components.DaggerAssignmentAppComponent
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentAppModule
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentStorIOModule

class AssignmentApp : Application() {

  lateinit var component: AssignmentAppComponent
    private set

  override fun onCreate() {
    super.onCreate()
    component = DaggerAssignmentAppComponent.builder()
        .assignmentAppModule(AssignmentAppModule(this))
        .assignmentStorIOModule(AssignmentStorIOModule())
        .build()
  }

  companion object {

    fun from(context: Context): AssignmentApp {
      return context.applicationContext as AssignmentApp
    }
  }
}
