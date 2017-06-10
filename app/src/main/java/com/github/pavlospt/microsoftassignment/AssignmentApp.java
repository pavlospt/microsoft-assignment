package com.github.pavlospt.microsoftassignment;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.github.pavlospt.microsoftassignment.di.components.AssignmentAppComponent;
import com.github.pavlospt.microsoftassignment.di.components.DaggerAssignmentAppComponent;
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentAppModule;
import com.github.pavlospt.microsoftassignment.di.modules.AssignmentStorIOModule;

public class AssignmentApp extends Application {

  private AssignmentAppComponent assignmentAppComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    assignmentAppComponent = DaggerAssignmentAppComponent.builder()
        .assignmentAppModule(new AssignmentAppModule(this))
        .assignmentStorIOModule(new AssignmentStorIOModule())
        .build();
  }

  public AssignmentAppComponent getComponent() {
    return assignmentAppComponent;
  }

  public static AssignmentApp from(@NonNull Context context) {
    return (AssignmentApp) context.getApplicationContext();
  }
}
