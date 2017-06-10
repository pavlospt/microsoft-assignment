package com.github.pavlospt.microsoftassignment.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import com.github.pavlospt.microsoftassignment.persistence.DatabaseOpenHelper;
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModel;
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModelSQLiteTypeMapping;
import com.github.pavlospt.microsoftassignment.persistence.storio.DefaultStorIOInteractor;
import com.github.pavlospt.microsoftassignment.persistence.storio.StorIOInteractor;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AssignmentStorIOModule {

  @Provides
  @NonNull
  @Singleton
  public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
    return DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(sqLiteOpenHelper)
        .addTypeMapping(BeerStorIOModel.class, new BeerStorIOModelSQLiteTypeMapping())
        .build();
  }

  @Provides
  @NonNull
  @Singleton
  public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull Context context) {
    return new DatabaseOpenHelper(context);
  }

  @Singleton
  @Provides
  public StorIOInteractor provideStorIOInteractor(StorIOSQLite storIOSQLite) {
    return new DefaultStorIOInteractor(storIOSQLite);
  }
}
