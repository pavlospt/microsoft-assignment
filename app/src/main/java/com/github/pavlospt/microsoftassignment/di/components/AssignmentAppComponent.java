package com.github.pavlospt.microsoftassignment.di.components;

import com.github.pavlospt.microsoftassignment.beer_list_feature.BeerListActivity;
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentAppModule;
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentStorIOModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AssignmentAppModule.class, AssignmentStorIOModule.class
})
public interface AssignmentAppComponent {
  void inject(BeerListActivity beerListActivity);
}