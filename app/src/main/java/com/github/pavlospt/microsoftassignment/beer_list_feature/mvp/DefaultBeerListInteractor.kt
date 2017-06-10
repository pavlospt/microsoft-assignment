package com.github.pavlospt.microsoftassignment.beer_list_feature.mvp

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.di.components.AssignmentRetrofitComponent
import com.github.pavlospt.microsoftassignment.di.components.DaggerAssignmentRetrofitComponent
import rx.Observable


class DefaultBeerListInteractor : BeerListInteractor {

  private val retrofitComponent: AssignmentRetrofitComponent by lazy {
    DaggerAssignmentRetrofitComponent.create()
  }

  override fun getBeers(pageSize: Int): Observable<List<BeerModel>> {
    return retrofitComponent.beersService().getBeers(pageSize)
  }
}