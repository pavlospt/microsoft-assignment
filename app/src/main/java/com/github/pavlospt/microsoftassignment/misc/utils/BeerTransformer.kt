package com.github.pavlospt.microsoftassignment.misc.utils

import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModel

/*
* Utility class to help with the transformation of objects between network and SQLite models
* Equivalent to a DataMapper
* */
object BeerTransformer {

  fun transform(beers: List<BeerStorIOModel>): List<BeerModel>
      = beers.map {
    BeerModel(
        it.id,
        it.name,
        it.tagline,
        it.firstBrewed,
        it.description,
        it.imageUrl,
        it.brewerTips,
        it.contributedBy)
  }

  fun transformToStorIOBeer(beers: List<BeerModel>): List<BeerStorIOModel>
      = beers.map {
    BeerStorIOModel(
        it.id,
        it.name,
        it.tagline,
        it.brewerTips,
        it.description,
        it.firstBrewed,
        it.imageUrl,
        it.contributedBy)
  }

}