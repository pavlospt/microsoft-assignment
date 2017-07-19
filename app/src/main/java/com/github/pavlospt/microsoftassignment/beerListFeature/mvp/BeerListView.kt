package com.github.pavlospt.microsoftassignment.beerListFeature.mvp

import com.github.pavlospt.microsoftassignment.repo.remote.entities.BeerModel
import com.github.pavlospt.microsoftassignment.mvp.MvpView


interface BeerListView : MvpView {
  fun beersLoaded(beers: List<BeerModel>)
  fun brewingBeers()
  fun searchResults(beers: List<BeerModel>)
  fun showPersistedDataInfoMessage(infoMessageMode: InfoMessageMode)
  fun hidePersistedDataInfoMessage()
}