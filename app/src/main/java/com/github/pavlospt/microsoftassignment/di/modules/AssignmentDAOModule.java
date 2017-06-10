package com.github.pavlospt.microsoftassignment.di.modules;

import com.github.pavlospt.microsoftassignment.api.dao.BeersDAO;
import com.github.pavlospt.microsoftassignment.api.service.BeersService;
import dagger.Module;
import dagger.Provides;

@Module(includes = { AssignmentRetrofitModule.class })
public class AssignmentDAOModule {
  @Provides
  BeersService provideBeersService(BeersDAO beersDAO) {
    return new BeersService(beersDAO);
  }
}
