package com.github.pavlospt.microsoftassignment;

import com.github.pavlospt.microsoftassignment.api.models.BeerModel;
import com.github.pavlospt.microsoftassignment.misc.utils.BeerTransformer;
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeerTransformerTest {

  @Test
  public void test_correct_results_after_transformation_to_storio_model() {
    List<BeerModel> beerModels = new ArrayList<>();
    beerModels.add(
        new BeerModel(1, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));

    List<BeerStorIOModel> transformedBeers = BeerTransformer.INSTANCE.transformToStorIOBeer(beerModels);

    BeerModel beerA = beerModels.get(0);
    BeerStorIOModel beerB = transformedBeers.get(0);

    Assert.assertEquals(beerA.getId(), beerB.getId());
    Assert.assertEquals(beerA.getName(), beerB.getName());
    Assert.assertEquals(beerA.getImageUrl(), beerB.getImageUrl());
    Assert.assertEquals(beerA.getBrewerTips(), beerB.getBrewerTips());
    Assert.assertEquals(beerA.getFirstBrewed(), beerB.getFirstBrewed());
    Assert.assertEquals(beerA.getDescription(), beerB.getDescription());
    Assert.assertEquals(beerA.getContributedBy(), beerB.getContributedBy());
    Assert.assertEquals(beerA.getTagline(), beerB.getTagline());
  }

  @Test
  public void test_correct_results_after_transformation_to_model() {
    List<BeerStorIOModel> beerStorIOModels = new ArrayList<>();
    beerStorIOModels.add(
        new BeerStorIOModel(1, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));

    List<BeerModel> transformedBeers = BeerTransformer.INSTANCE.transform(beerStorIOModels);

    BeerModel beerA = transformedBeers.get(0);
    BeerStorIOModel beerB = beerStorIOModels.get(0);

    Assert.assertEquals(beerA.getId(), beerB.getId());
    Assert.assertEquals(beerA.getName(), beerB.getName());
    Assert.assertEquals(beerA.getImageUrl(), beerB.getImageUrl());
    Assert.assertEquals(beerA.getBrewerTips(), beerB.getBrewerTips());
    Assert.assertEquals(beerA.getFirstBrewed(), beerB.getFirstBrewed());
    Assert.assertEquals(beerA.getDescription(), beerB.getDescription());
    Assert.assertEquals(beerA.getContributedBy(), beerB.getContributedBy());
    Assert.assertEquals(beerA.getTagline(), beerB.getTagline());
  }

}
