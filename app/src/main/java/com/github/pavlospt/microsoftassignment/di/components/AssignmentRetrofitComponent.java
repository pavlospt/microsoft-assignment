package com.github.pavlospt.microsoftassignment.di.components;

import com.github.pavlospt.microsoftassignment.api.service.BeersService;
import com.github.pavlospt.microsoftassignment.di.modules.ApiModule;
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentDAOModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AssignmentDAOModule.class, ApiModule.class
})
public interface AssignmentRetrofitComponent {
  BeersService beersService();
}
