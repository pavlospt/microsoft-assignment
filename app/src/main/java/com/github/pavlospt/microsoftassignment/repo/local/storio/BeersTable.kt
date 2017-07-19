package com.github.pavlospt.microsoftassignment.repo.local.storio

import com.pushtorefresh.storio.sqlite.queries.DeleteQuery
import com.pushtorefresh.storio.sqlite.queries.Query


class BeersTable {

  companion object {
    const val TABLE_NAME = "beers_table"

    const val COLUMN_ID = "_id"

    const val COLUMN_REMOTE_ID = "id"

    const val COLUMN_NAME = "name"

    const val COLUMN_TAGLINE = "tagline"

    const val COLUMN_FIRST_BREWED = "first_brewed"

    const val COLUMN_DESCRIPTION = "description"

    const val COLUMN_IMAGE_URL = "image_url"

    const val COLUMN_BREWER_TIPS = "brewer_tips"

    const val COLUMN_CONTRIBUTED_BY = "contributed_by"

    @JvmField
    val GET_ALL_BEERS_QUERY = Query.builder().table(
        TABLE_NAME).build()

    @JvmField
    val DELETE_ALL_BEERS_QUERY = DeleteQuery.builder().table(
        TABLE_NAME).build()

    @JvmStatic
    fun createBeersTable(): String {
      return "CREATE TABLE ${TABLE_NAME}(${COLUMN_ID} INTEGER NOT NULL PRIMARY KEY, " +
          "${COLUMN_REMOTE_ID} TEXT NOT NULL, " +
          "${COLUMN_NAME} TEXT NOT NULL, " +
          "${COLUMN_TAGLINE} TEXT NOT NULL, " +
          "${COLUMN_FIRST_BREWED} TEXT NOT NULL, " +
          "${COLUMN_DESCRIPTION} TEXT NOT NULL, " +
          "${COLUMN_IMAGE_URL} TEXT NOT NULL, " +
          "${COLUMN_BREWER_TIPS} TEXT NOT NULL, " +
          "${COLUMN_CONTRIBUTED_BY} TEXT NOT NULL" +
          ");"
    }
  }
}