package com.github.pavlospt.microsoftassignment.persistence.storio

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult
import com.pushtorefresh.storio.sqlite.operations.put.PutResults
import rx.Observable


interface StorIOInteractor {
  fun getPersistedBeers(): Observable<List<BeerStorIOModel>>
  fun search(terms: List<String>): Observable<List<BeerStorIOModel>>
  fun saveBeers(beers: List<BeerStorIOModel>): Observable<PutResults<BeerStorIOModel>>
  fun clearBeers(): Observable<DeleteResult>
}