package com.github.pavlospt.microsoftassignment.persistence.storio

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
  override fun search(term: String): Observable<List<BeerStorIOModel>> {
    val escapedTerm = "%$term%"

    return storIOSQLite
        .get()
        .listOfObjects(BeerStorIOModel::class.java)
        .withQuery(Query.builder().table(BeersTable.TABLE_NAME)
            .where(
                BeersTable.COLUMN_NAME + " LIKE ?" +
                    " OR " +
                    BeersTable.COLUMN_BREWER_TIPS + " LIKE ?" +
                    " OR " +
                    BeersTable.COLUMN_CONTRIBUTED_BY + " LIKE ?" +
                    " OR " +
                    BeersTable.COLUMN_DESCRIPTION + " LIKE ?" +
                    " OR " +
                    BeersTable.COLUMN_FIRST_BREWED + " LIKE ?" +
                    " OR " +
                    BeersTable.COLUMN_TAGLINE + " LIKE ?"
            )
            //StorIO requires args to match the number of '?' defined above
            .whereArgs(escapedTerm, escapedTerm, escapedTerm, escapedTerm, escapedTerm,
                escapedTerm)
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