package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.main

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.common.CheckInternet
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.common.CheckLocation
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.common.PermissionRequester
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.R
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.detail.DetailActivity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.common.getImageFromString
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.common.startActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by currentScope.viewModel(this)

    private val coarsePermissionRequester =
        PermissionRequester(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    private lateinit var checkLocation: CheckLocation

    private lateinit var checkInternet: CheckInternet

    //private val adapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler.adapter = adapter
        checkLocation = CheckLocation(
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        )
        checkInternet = CheckInternet(
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )

        location.setOnClickListener {
            checkLocation()
        }
        viewModel.model.observe(this, Observer(::updateUi))

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                startActivity<DetailActivity> {
                    putExtra(
                        DetailActivity.WEATHER, it.timestamp
                    )
                }
            }
        })
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        locationProgressBar.visibility =
            if (model is MainViewModel.UiModel.Loading) VISIBLE else GONE

        when (model) {
            is MainViewModel.UiModel.Content -> updateData(model.weather)
            is MainViewModel.UiModel.ShowTurnOnLocation -> showTurnOnLocation()
            is MainViewModel.UiModel.ShowTurnOnPermission -> showTurnOnPermission()
            is MainViewModel.UiModel.ShowCanCheckYourInternet -> showCanCheckYourInternet()
            is MainViewModel.UiModel.RequestCheckLocation -> checkLocation()
            is MainViewModel.UiModel.RequestCheckInternet -> checkInternet()
            MainViewModel.UiModel.RequestLocationPermission -> checkPermission()
        }
    }

    private fun updateData(weather: Weather) {

        location_city.text = weather.city
        location_weather_imageView.setImageDrawable(
            getImageFromString(
                weather.main,
                this
            )
        )

        location_city.setOnClickListener {

            viewModel.onWeatherClicked(weather)
        }
    }

    private fun checkPermission() {
        coarsePermissionRequester.request {
            viewModel.onCoarsePermissionRequested(it)
        }
    }

    private fun checkLocation() {
        checkLocation.checkLocation {
            viewModel.checkLocation(it)
        }
    }

    private fun checkInternet() {
        checkInternet.isOnline {
            viewModel.checkInternet(it)
        }
    }

    private fun showCanCheckYourInternet() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.internet_issue),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

    private fun showTurnOnLocation() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.location_turn_on),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

    private fun showTurnOnPermission() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.location_turn_on_permission),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

}
