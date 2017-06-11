package com.github.pavlospt.microsoftassignment;

import com.github.pavlospt.microsoftassignment.api.models.BeerModel;
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.BeerListInteractor;
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.BeerListPresenter;
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.BeerListView;
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.InfoMessageMode;
import com.github.pavlospt.microsoftassignment.misc.utils.BeerTransformer;
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModel;
import com.github.pavlospt.microsoftassignment.persistence.storio.BeersTable;
import com.github.pavlospt.microsoftassignment.persistence.storio.StorIOInteractor;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeerListPresenterTest {

  private BeerListView mockView;
  private BeerListInteractor mockInteractor;
  private StorIOInteractor mockStorIOInteractor;

  @Before
  public void setUp() {
    mockView = mock(BeerListView.class);
    mockInteractor = mock(BeerListInteractor.class);
    mockStorIOInteractor = mock(StorIOInteractor.class);
  }

  @Test
  public void test_correct_loading_of_beers() {
    List<BeerModel> dummyBeers = dummyListOfBeers();
    List<BeerStorIOModel> dummyStorIOBeers =
        BeerTransformer.INSTANCE.transformToStorIOBeer(dummyBeers);

    Set<String> tables = new HashSet<>();
    tables.add(BeersTable.TABLE_NAME);

    Map<BeerStorIOModel, PutResult> putResults = new HashMap<>();
    putResults.put(dummyStorIOBeers.get(0), PutResult.newInsertResult(1, tables, "Foo"));

    when(mockInteractor.getBeers(50)).thenReturn(Observable.just(dummyBeers));
    when(mockStorIOInteractor.clearBeers()).thenReturn(
        Observable.just(DeleteResult.newInstance(10, tables, "Foo")));
    when(mockStorIOInteractor.saveBeers(dummyStorIOBeers.subList(0, 1))).thenReturn(
        Observable.just(PutResults.newInstance(putResults)));

    BeerListPresenter presenter = new BeerListPresenter(mockInteractor, Schedulers.immediate());
    presenter.init(mockView);
    presenter.assignStorIOInteractor(mockStorIOInteractor);
    presenter.loadBeers();

    InOrder viewVerifier = inOrder(mockView);

    viewVerifier.verify(mockView).brewingBeers();
    viewVerifier.verify(mockView).beersLoaded(dummyBeers);
    viewVerifier.verifyNoMoreInteractions();
  }

  @Test
  public void test_correct_loading_of_beers_from_search() {
    List<BeerModel> dummyBeers = dummyListOfBeers();
    List<BeerStorIOModel> dummyStorIOBeers =
        BeerTransformer.INSTANCE.transformToStorIOBeer(dummyBeers);

    when(mockStorIOInteractor.search(Arrays.asList("Foo", "Beer"))).thenReturn(Observable.just(dummyStorIOBeers));

    BeerListPresenter presenter = new BeerListPresenter(mockInteractor, Schedulers.immediate());
    presenter.init(mockView);
    presenter.assignStorIOInteractor(mockStorIOInteractor);
    presenter.search("Foo Beer");

    InOrder viewVerifier = inOrder(mockView);

    viewVerifier.verify(mockView).beersLoaded(dummyBeers);
    viewVerifier.verifyNoMoreInteractions();
  }

  @Test
  public void test_load_of_persisted_beers() {
    List<BeerModel> dummyBeers = dummyListOfBeers();
    List<BeerStorIOModel> dummyStorIOBeers =
        BeerTransformer.INSTANCE.transformToStorIOBeer(dummyBeers);

    List<BeerStorIOModel> persistedBeers = dummyStorIOBeers.subList(0, 5);

    when(mockInteractor.getBeers(50)).thenReturn(
        Observable.<List<BeerModel>>error(new BeerLoadingException()));
    when(mockStorIOInteractor.getPersistedBeers()).thenReturn(Observable.just(persistedBeers));

    BeerListPresenter presenter = new BeerListPresenter(mockInteractor, Schedulers.immediate());
    presenter.init(mockView);
    presenter.assignStorIOInteractor(mockStorIOInteractor);
    presenter.loadBeers();

    InOrder viewVerifier = inOrder(mockView);

    viewVerifier.verify(mockView).brewingBeers();
    viewVerifier.verify(mockView).showPersistedDataInfoMessage(InfoMessageMode.PERSISTED_DATA);
    viewVerifier.verify(mockView).beersLoaded(BeerTransformer.INSTANCE.transform(persistedBeers));
    viewVerifier.verifyNoMoreInteractions();
  }

  @Test
  public void test_load_of_empty_persisted_beers() {
    List<BeerModel> dummyBeers = dummyListOfBeers();
    List<BeerStorIOModel> dummyStorIOBeers =
        BeerTransformer.INSTANCE.transformToStorIOBeer(dummyBeers);

    when(mockInteractor.getBeers(50)).thenReturn(
        Observable.<List<BeerModel>>error(new BeerLoadingException()));
    when(mockStorIOInteractor.getPersistedBeers()).thenReturn(
        Observable.just(Collections.<BeerStorIOModel>emptyList()));

    BeerListPresenter presenter = new BeerListPresenter(mockInteractor, Schedulers.immediate());
    presenter.init(mockView);
    presenter.assignStorIOInteractor(mockStorIOInteractor);
    presenter.loadBeers();

    InOrder viewVerifier = inOrder(mockView);

    viewVerifier.verify(mockView).brewingBeers();
    viewVerifier.verify(mockView)
        .showPersistedDataInfoMessage(InfoMessageMode.NO_PERSISTED_DATA_AVAILABLE);
    viewVerifier.verifyNoMoreInteractions();
  }

  private class BeerLoadingException extends Exception {

  }

  private List<BeerModel> dummyListOfBeers() {
    List<BeerModel> beers = new ArrayList<>();
    beers.add(
        new BeerModel(1, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(2, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(3, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(4, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(5, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(6, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(7, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    beers.add(
        new BeerModel(8, "name", "tagline", "09/1980", "description", "imageUrl", "brewerTips",
            "contributedBy"));
    return beers;
  }
}
