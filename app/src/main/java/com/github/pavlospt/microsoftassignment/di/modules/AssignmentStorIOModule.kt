package com.github.pavlospt.microsoftassignment.di.modules

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.github.pavlospt.microsoftassignment.repo.local.db.DatabaseOpenHelper
import com.github.pavlospt.microsoftassignment.repo.local.storio.BeerStorIOModel
import com.github.pavlospt.microsoftassignment.repo.local.interactor.impl.DefaultStorIOInteractor
import com.github.pavlospt.microsoftassignment.repo.local.interactor.StorIOInteractor
import com.github.pavlospt.microsoftassignment.repo.local.storio.BeerStorIOModelSQLiteTypeMapping
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AssignmentStorIOModule {

  @Provides
  @Singleton
  fun provideStorIOSQLite(sqLiteOpenHelper: SQLiteOpenHelper): StorIOSQLite {
    return DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(sqLiteOpenHelper)
        .addTypeMapping(BeerStorIOModel::class.java, BeerStorIOModelSQLiteTypeMapping())
        .build()
  }

  @Provides
  @Singleton
  fun provideSQLiteOpenHelper(context: Context): SQLiteOpenHelper {
    return DatabaseOpenHelper(context)
  }

  @Singleton
  @Provides
  fun provideStorIOInteractor(storIOSQLite: StorIOSQLite): StorIOInteractor {
    return DefaultStorIOInteractor(storIOSQLite)
  }
}
