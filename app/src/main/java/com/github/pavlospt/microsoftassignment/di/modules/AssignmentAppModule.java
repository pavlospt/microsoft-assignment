package com.github.pavlospt.microsoftassignment.di.modules;

import android.content.Context;
import com.github.pavlospt.microsoftassignment.AssignmentApp;
import dagger.Module;
import dagger.Provides;

@Module(includes = { AssignmentDAOModule.class})
public class AssignmentAppModule {

  private AssignmentApp application;

  public AssignmentAppModule(AssignmentApp application) {
    this.application = application;
  }

  @Provides
  Context provideApplicationContext() {
    return application;
  }
}
