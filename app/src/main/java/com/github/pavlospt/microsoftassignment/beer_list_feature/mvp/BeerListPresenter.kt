package com.github.pavlospt.microsoftassignment.beer_list_feature.mvp

import android.util.Log
import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.InfoMessageMode.NO_PERSISTED_DATA_AVAILABLE
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.InfoMessageMode.PERSISTED_DATA
import com.github.pavlospt.microsoftassignment.misc.extensions.disposeIn
import com.github.pavlospt.microsoftassignment.misc.utils.BeerTransformer
import com.github.pavlospt.microsoftassignment.mvp.Presenter
import com.github.pavlospt.microsoftassignment.persistence.storio.BeerStorIOModel
import com.github.pavlospt.microsoftassignment.persistence.storio.StorIOInteractor
import com.pushtorefresh.storio.sqlite.operations.put.PutResults
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.lang.ref.WeakReference

/*
* BeerListPresenter is the class responsible to
* handle user interactions with our main screen
* */
class BeerListPresenter(
    private val interactor: BeerListInteractor = DefaultBeerListInteractor(),
    private val scheduler: Scheduler = AndroidSchedulers.mainThread()
) : Presenter<BeerListView> {

  private val TAG = "BeerListPresenter"
  private val BEERS_PAGE_SIZE = 50
  private lateinit var viewRef: WeakReference<BeerListView>
  private lateinit var storIOInteractor: StorIOInteractor
  private var disposeBag: CompositeSubscription = CompositeSubscription()

  // Init View's WeakReference
  override fun init(view: BeerListView) {
    viewRef = WeakReference(view)
  }

  // Destroy CompositeSubscription and View's WeakReference to avoid memory leaks
  override fun destroy() {
    viewRef.clear()
    disposeBag.unsubscribe()
  }

  // Assign the StorIOInteractor to be used by this presenter
  fun assignStorIOInteractor(storIOInteractor: StorIOInteractor) {
    this.storIOInteractor = storIOInteractor
  }

  // Load beers from the API
  fun loadBeers() {
    viewRef.get()?.brewingBeers()

    interactor
        .getBeers(BEERS_PAGE_SIZE)
        .observeOn(scheduler)
        .subscribe(object : Subscriber<List<BeerModel>>() {
          override fun onNext(response: List<BeerModel>) {
            viewRef.get()?.beersLoaded(response)
            persistBeers(response)
          }

          override fun onError(e: Throwable) {
            loadPersistedBeers()
          }

          override fun onCompleted() {
          }
        })
        .disposeIn(disposeBag)
  }

  // Trigger search through StorIOInteractor
  fun search(term: String) {
    storIOInteractor
        .search(term)
        .observeOn(scheduler)
        .subscribe(object : Subscriber<List<BeerStorIOModel>>() {
          override fun onNext(searchResults: List<BeerStorIOModel>) {
            viewRef.get()?.beersLoaded(BeerTransformer.transform(searchResults))
          }

          override fun onError(e: Throwable) {
          }

          override fun onCompleted() {
          }
        })
        .disposeIn(disposeBag)
  }

  // Save beers loaded from Network
  private fun persistBeers(beers: List<BeerModel>) {
    storIOInteractor
        .clearBeers()
        .flatMap { storIOInteractor.saveBeers(BeerTransformer.transformToStorIOBeer(beers)) }
        .observeOn(scheduler)
        .subscribe(object : Subscriber<PutResults<BeerStorIOModel>>() {
          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
          }

          override fun onNext(t: PutResults<BeerStorIOModel>?) {
          }

        })
        .disposeIn(disposeBag)
  }

  // Load persisted beers
  private fun loadPersistedBeers() {
    storIOInteractor
        .getPersistedBeers()
        .observeOn(scheduler)
        .subscribe(object : Subscriber<List<BeerStorIOModel>>() {
          override fun onError(e: Throwable) {

          }

          override fun onNext(persistedBeers: List<BeerStorIOModel>) {
            handlePersistedBeers(persistedBeers)
          }

          override fun onCompleted() {
          }

        })
        .disposeIn(disposeBag)
  }

  // Present a proper info message to the user regarding our persisted data
  // instead of just showing an empty screen
  private fun handlePersistedBeers(persistedBeers: List<BeerStorIOModel>) {
    if (persistedBeers.isEmpty()) {
      viewRef.get()?.showPersistedDataInfoMessage(NO_PERSISTED_DATA_AVAILABLE)
    } else {
      viewRef.get()?.showPersistedDataInfoMessage(PERSISTED_DATA)
      viewRef.get()?.beersLoaded(BeerTransformer.transform(persistedBeers))
    }
  }
}