package com.github.pavlospt.microsoftassignment.beer_list_feature.mvp

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.mvp.View


interface BeerListView : View {
  fun beersLoaded(beers: List<BeerModel>)
  fun brewingBeers()
  fun searchResults(beers: List<BeerModel>)
  fun showPersistedDataInfoMessage(infoMessageMode: InfoMessageMode)
  fun hidePersistedDataInfoMessage()
}