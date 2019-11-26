package com.architectcoders.collection.model

import android.app.Activity
import com.architectcoders.cleanarchitecturekoinmvvm.R

class PokeRepository(activity: Activity) {

    private val apiHost = activity.getString(R.string.api_host)
    private val apiKey = activity.getString(R.string.api_key)

    suspend fun findPokemon() =
        PokeDb.service
            .listPokeAsync(
                apiHost,
                apiKey
            )
            .await()
}