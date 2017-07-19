package com.github.pavlospt.microsoftassignment.repo.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.pavlospt.microsoftassignment.repo.local.storio.BeersTable

/*
* Our SQLiteOpenHelper class that handles DB creation
* */
class DatabaseOpenHelper(context: Context) : SQLiteOpenHelper(context,
    DB_NAME,
    null, 1) {

  override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
    sqLiteDatabase.execSQL(BeersTable.createBeersTable())
  }

  override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
    //No need for an implementation here
  }

  companion object {
    private val DB_NAME = "assignment_db"
  }
}