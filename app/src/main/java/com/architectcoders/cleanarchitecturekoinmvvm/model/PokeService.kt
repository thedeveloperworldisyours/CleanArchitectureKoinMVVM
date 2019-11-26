package com.architectcoders.collection.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface PokeService {

    companion object{
        const val GET_VALUE = "pokemon_stats.json"
    }

    @GET(GET_VALUE)
    fun listPokeAsync(
        @Header("X-RapidAPI-Host") host:String,
        @Header("X-RapidAPI-Key") key:String
        ): Deferred<List<PokeResult>>
}