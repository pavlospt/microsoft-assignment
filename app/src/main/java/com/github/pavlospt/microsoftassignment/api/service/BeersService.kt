package com.github.pavlospt.microsoftassignment.api.service

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.api.dao.BeersDAO
import rx.Observable


class BeersService(private val beersDAO: BeersDAO) {
  fun getBeers(pageSize: Int): Observable<List<BeerModel>> {
    val response = beersDAO.getBeers(pageSize).share()
    return response
  }
}