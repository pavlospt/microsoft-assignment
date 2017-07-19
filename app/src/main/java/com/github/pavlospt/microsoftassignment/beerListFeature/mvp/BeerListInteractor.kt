package com.github.pavlospt.microsoftassignment.beerListFeature.mvp

import com.github.pavlospt.microsoftassignment.repo.remote.entities.BeerModel
import rx.Observable


interface BeerListInteractor {
  fun getBeers(pageSize: Int): Observable<List<BeerModel>>
}