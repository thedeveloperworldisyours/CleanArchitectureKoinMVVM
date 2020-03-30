package com.architectcoders.cleanarchitecturekoinmvvm.data.source

interface LocationDataSource {

    suspend fun findLat(): String?
    suspend fun findLon(): String?
}