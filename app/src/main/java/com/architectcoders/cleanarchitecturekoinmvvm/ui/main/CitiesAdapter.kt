package com.architectcoders.cleanarchitecturekoinmvvm.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.cleanarchitecturekoinmvvm.R
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather
import com.architectcoders.cleanarchitecturekoinmvvm.ui.common.basicDiffUtil
import com.architectcoders.cleanarchitecturekoinmvvm.ui.common.inflate
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesAdapter :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    var cities: List<Weather> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.city_item, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityName = cities[position]
        holder.bind(cityName)
        //holder.itemView.setOnClickListener { listener(movie) }
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cityWeather: Weather) {
            itemView.city_title.text = cityWeather.main
        }
    }
}