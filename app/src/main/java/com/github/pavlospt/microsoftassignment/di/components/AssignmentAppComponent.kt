package com.github.pavlospt.microsoftassignment.di.components

import com.github.pavlospt.microsoftassignment.beerListFeature.BeerListActivity
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentAppModule
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentStorIOModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AssignmentAppModule::class, AssignmentStorIOModule::class))
interface AssignmentAppComponent {
  fun inject(beerListActivity: BeerListActivity)
}