package com.architectcoders.cleanarchitecturekoinmvvm

import android.os.Bundle
import com.architectcoders.collection.model.PokeRepository
import com.architectcoders.collection.ui.PokeAdapter
import com.architectcoders.collection.ui.common.CoroutineScopeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {

    private val moviesRepository: PokeRepository by lazy { PokeRepository(this) }
    private val adapter = PokeAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter


        launch {

            adapter.pokemons = moviesRepository.findPokemon()
        }

    }
}
