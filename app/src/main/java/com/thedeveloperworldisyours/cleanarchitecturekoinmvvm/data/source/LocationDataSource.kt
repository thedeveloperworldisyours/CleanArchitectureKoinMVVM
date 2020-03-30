package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source

interface LocationDataSource {

    suspend fun findLat(): String?
    suspend fun findLon(): String?
}