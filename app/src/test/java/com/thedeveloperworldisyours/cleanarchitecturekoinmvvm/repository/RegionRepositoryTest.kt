package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.repository

import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.PermissionChecker
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.RegionRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.AndroidPermissionChecker
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest() {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: AndroidPermissionChecker

    lateinit var repository: RegionRepository

    val DEFAULT_LON = "0"
    val DEFAULT_LAT = "0"

    @Before
    fun setup() {
        repository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `find Lat successful`() {
        runBlocking {
            val lat = "lat"
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                true
            )
            whenever(locationDataSource.findLat()).thenReturn(lat)

            val returnValue = repository.findLat()

            Assert.assertEquals(returnValue, lat)
        }

    }

    @Test
    fun `find default lat`() {
        runBlocking {
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                false
            )

            val region = repository.findLat()

            Assert.assertEquals(region, DEFAULT_LAT)
        }
    }

    @Test
    fun `find successful lon`() {
        runBlocking {
            val lon = "lon"
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                true
            )
            whenever(locationDataSource.findLon()).thenReturn(lon)

            val region = repository.findlon()

            Assert.assertEquals(region, lon)
        }
    }

    @Test
    fun `find default lon`(){
        runBlocking {
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                false
            )

            val region = repository.findlon()

            Assert.assertEquals(region, DEFAULT_LON)
        }
    }
}