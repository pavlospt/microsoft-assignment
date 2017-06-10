package com.github.pavlospt.microsoftassignment.beer_list_feature

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.github.pavlospt.microsoftassignment.AssignmentApp
import com.github.pavlospt.microsoftassignment.R
import com.github.pavlospt.microsoftassignment.api.models.BeerModel
import com.github.pavlospt.microsoftassignment.beer_list_feature.adapter.BeersRecyclerAdapter
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.BeerListPresenter
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.BeerListView
import com.github.pavlospt.microsoftassignment.beer_list_feature.mvp.InfoMessageMode
import com.github.pavlospt.microsoftassignment.misc.extensions.hide
import com.github.pavlospt.microsoftassignment.misc.extensions.show
import com.github.pavlospt.microsoftassignment.persistence.storio.StorIOInteractor
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject


class BeerListActivity : AppCompatActivity(), BeerListView {

  private val beerSearch: EditText by lazy {
    val search = findViewById(R.id.activity_beer_list_search) as EditText
    search.clearFocus()
    search
  }

  private val infoMessage: TextView by lazy {
    val textView = findViewById(R.id.activity_beer_list_info_message) as TextView
    textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    textView
  }

  private val loadingIndicator: ProgressBar by lazy {
    findViewById(R.id.activity_beer_list_loading_progress) as ProgressBar
  }

  private val beerList: RecyclerView by lazy {
    findViewById(R.id.activity_beer_list_rv) as RecyclerView
  }

  private val presenter: BeerListPresenter by lazy {
    BeerListPresenter()
  }

  private val rvAdapter: BeersRecyclerAdapter by lazy {
    BeersRecyclerAdapter()
  }

  @Inject
  lateinit var storIOInteractor: StorIOInteractor

  private lateinit var searchSubscription: Subscription

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_beer_list)

    AssignmentApp.from(this).component.inject(this)

    presenter.init(this)
    presenter.assignStorIOInteractor(storIOInteractor)

    setUpRecyclerView()
    setUpSearch()

    presenter.loadBeers()
  }

  private fun setUpSearch() {
    this.searchSubscription = RxTextView.textChangeEvents(beerSearch)
        .skip(1)
        .debounce(400, MILLISECONDS)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Subscriber<TextViewTextChangeEvent>() {
          override fun onError(e: Throwable) {
          }

          override fun onNext(event: TextViewTextChangeEvent) {
            presenter.search(event.text().toString())
          }

          override fun onCompleted() {
          }
        })
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
    searchSubscription.unsubscribe()
  }

  override fun onPause() {
    super.onPause()
  }

  override fun brewingBeers() {
    loadingIndicator.show()
    beerList.hide()
  }

  override fun beersLoaded(beers: List<BeerModel>) {
    loadingIndicator.hide()
    rvAdapter.setBeers(beers)
    beerList.show()
  }

  override fun searchResults(beers: List<BeerModel>) {
    rvAdapter.setBeers(beers)
    beerList.show()
  }

  override fun showPersistedDataInfoMessage(infoMessageMode: InfoMessageMode) {
    loadingIndicator.hide()
    infoMessage.show()

    when (infoMessageMode) {
      InfoMessageMode.PERSISTED_DATA -> infoMessage.text = getString(
          R.string.persisted_results_info_message)
      InfoMessageMode.NO_PERSISTED_DATA_AVAILABLE -> infoMessage.text = getString(
          R.string.no_persisted_data_available)
    }
  }

  override fun hidePersistedDataInfoMessage() {
    infoMessage.hide()
  }

  private fun setUpRecyclerView() {
    rvAdapter.setOnClickListener {
      openBeerImage(it)
    }

    beerList.layoutManager = LinearLayoutManager(this)
    beerList.adapter = rvAdapter
  }

  private fun openBeerImage(beer: BeerModel) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(beer.imageUrl))
    startActivity(intent)
  }
}
