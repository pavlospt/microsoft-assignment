package com.github.pavlospt.microsoftassignment.persistence.storio

import com.github.pavlospt.microsoftassignment.misc.utils.DBUtils
import com.github.pavlospt.microsoftassignment.persistence.storio.BeersTable.Companion.GET_ALL_BEERS_QUERY
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult
import com.pushtorefresh.storio.sqlite.operations.put.PutResults
import com.pushtorefresh.storio.sqlite.queries.Query
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

/*
* Main Interactor class for SQLite operations
* */
class DefaultStorIOInteractor @Inject constructor(
    private val storIOSQLite: StorIOSQLite) : StorIOInteractor {

  override fun getPersistedBeers(): Observable<List<BeerStorIOModel>> {
    return storIOSQLite
        .get()
        .listOfObjects(BeerStorIOModel::class.java)
        .withQuery(GET_ALL_BEERS_QUERY)
        .prepare()
        .asRxObservable()
        .subscribeOn(Schedulers.io())
  }

  /*
   * Execute a search for the term contained in
   * "name||brewer_tips||contributed_by||first_brewed||tagline",
   * against the persisted data
   * */
  override fun search(terms: List<String>): Observable<List<BeerStorIOModel>> {
    val searchableColumns = mutableListOf<String>(
        BeersTable.COLUMN_NAME,
        BeersTable.COLUMN_BREWER_TIPS,
        BeersTable.COLUMN_CONTRIBUTED_BY,
        BeersTable.COLUMN_DESCRIPTION,
        BeersTable.COLUMN_FIRST_BREWED,
        BeersTable.COLUMN_TAGLINE
    )

    val query = Query.builder().table(BeersTable.TABLE_NAME)
        .where(DBUtils.generateWhereStatement(searchableColumns, terms.size))
        .whereArgs(DBUtils.generateWrappedTermsList(terms, searchableColumns.size))
        .build()

    println(query.toString())

    return storIOSQLite
        .get()
        .listOfObjects(BeerStorIOModel::class.java)
        .withQuery(Query.builder().table(BeersTable.TABLE_NAME)
            .where(DBUtils.generateWhereStatement(searchableColumns, terms.size))
            .whereArgs(DBUtils.generateWrappedTermsList(terms, searchableColumns.size))
            .build()
        )
        .prepare()
        .asRxObservable()
        .subscribeOn(Schedulers.io())
  }

  /*
  * Save beers loaded from network into SQLite
  * */
  override fun saveBeers(beers: List<BeerStorIOModel>): Observable<PutResults<BeerStorIOModel>> {
    return storIOSQLite
        .put()
        .objects(beers)
        .prepare()
        .asRxObservable()
        .subscribeOn(Schedulers.io())
  }

  /*
  * Clear beers table before saving new ones
  * */
  override fun clearBeers(): Observable<DeleteResult> {
    return storIOSQLite
        .delete()
        .byQuery(BeersTable.DELETE_ALL_BEERS_QUERY)
        .prepare()
        .asRxObservable()
        .subscribeOn(Schedulers.io())
  }
}