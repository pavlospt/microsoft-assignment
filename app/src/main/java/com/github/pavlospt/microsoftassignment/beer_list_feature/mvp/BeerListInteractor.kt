package com.github.pavlospt.microsoftassignment.beer_list_feature.mvp

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import rx.Observable


interface BeerListInteractor {
  fun getBeers(pageSize: Int): Observable<List<BeerModel>>
}