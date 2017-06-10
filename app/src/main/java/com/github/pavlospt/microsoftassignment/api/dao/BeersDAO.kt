package com.github.pavlospt.microsoftassignment.api.dao

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


interface BeersDAO {
  @GET("beers")
  fun getBeers(@Query("per_page") pageSize: Int): Observable<List<BeerModel>>
}