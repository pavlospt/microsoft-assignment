package com.github.pavlospt.microsoftassignment.di.components

import com.github.pavlospt.microsoftassignment.repo.remote.services.BeersService
import com.github.pavlospt.microsoftassignment.di.modules.ApiModule
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentRetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AssignmentRetrofitModule::class, ApiModule::class))
interface AssignmentRetrofitComponent {
  fun beersService(): BeersService
}
