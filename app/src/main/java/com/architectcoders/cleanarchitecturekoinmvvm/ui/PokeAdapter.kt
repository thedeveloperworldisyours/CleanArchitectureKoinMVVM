package com.architectcoders.collection.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.cleanarchitecturekoinmvvm.R
import com.architectcoders.collection.model.PokeResult
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokeAdapter :
    RecyclerView.Adapter<PokeAdapter.ViewHolder>() {
    var pokemons: List<PokeResult> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.pokemonId == new.pokemonId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.pokemon_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = pokemons[position]
        holder.bind(movie)
        //holder.itemView.setOnClickListener { listener(movie) }
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pokemon: PokeResult) {
            itemView.title.text = pokemon.pokemonName
        }
    }
}