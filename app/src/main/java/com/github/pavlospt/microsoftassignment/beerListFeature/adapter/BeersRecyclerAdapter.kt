package com.github.pavlospt.microsoftassignment.beerListFeature.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.pavlospt.microsoftassignment.R
import com.github.pavlospt.microsoftassignment.repo.remote.entities.BeerModel
import com.github.pavlospt.microsoftassignment.beerListFeature.adapter.BeersRecyclerAdapter.BeerViewHolder

/*
* Beers Recycler Adapter
* */
class BeersRecyclerAdapter : RecyclerView.Adapter<BeerViewHolder>() {

  private val beerItems = mutableListOf<BeerModel>()
  private var listener: (beerModel: BeerModel) -> Unit? = {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder
      = BeerViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false))

  override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
    holder.bind(beerItems[position])
  }

  override fun getItemCount(): Int = beerItems.size

  fun setBeers(beers: List<BeerModel>) {
    beerItems.clear()
    beerItems.addAll(beers)
    notifyDataSetChanged()
  }

  fun setOnClickListener(listener: (beerModel: BeerModel) -> Unit) {
    this.listener = listener
  }

  inner class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val beerImage: ImageView by lazy {
      itemView.findViewById(R.id.item_beer_image) as ImageView
    }

    private val beerName: TextView by lazy {
      itemView.findViewById(R.id.item_beer_name) as TextView
    }

    private val beerDescription: TextView by lazy {
      itemView.findViewById(R.id.item_beer_description) as TextView
    }

    // Bind data of BeerModel to UI elements
    fun bind(beer: BeerModel) {
      bindClick(beer)
      beerName.text = beer.name
      beerDescription.text = beer.description
      Glide
          .with(itemView.context)
          .load(beer.imageUrl)
          .error(ContextCompat.getDrawable(itemView.context, R.drawable.ic_beer))
          .into(beerImage)
    }

    // Bind click listener which triggers beer image url open in Browser
    private fun bindClick(beer: BeerModel) {
      itemView.setOnClickListener {
        listener(beer)
      }
    }
  }
}