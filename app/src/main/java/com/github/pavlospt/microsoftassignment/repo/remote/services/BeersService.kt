package com.github.pavlospt.microsoftassignment.repo.remote.services

import com.github.pavlospt.microsoftassignment.repo.remote.entities.BeerModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


interface BeersService {
  @GET("beers")
  fun getBeers(@Query("per_page") pageSize: Int): Observable<List<BeerModel>>
}