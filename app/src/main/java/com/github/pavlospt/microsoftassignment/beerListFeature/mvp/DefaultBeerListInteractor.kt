package com.github.pavlospt.microsoftassignment.beerListFeature.mvp

import com.github.pavlospt.microsoftassignment.repo.remote.entities.BeerModel
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