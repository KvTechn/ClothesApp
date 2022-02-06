package com.example.clothesapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val locationManager by lazy {
        application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val _tapRequestState: MutableStateFlow<Resource<WeatherAndCity>?> =
        MutableStateFlow(null)
    val tapRequestState: StateFlow<Resource<WeatherAndCity>?> = _tapRequestState

    @SuppressLint("MissingPermission")
    fun startSearch() {

        viewModelScope.launch {

            _tapRequestState.value = Resource.Loading()

            val lastPositionNetwork =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val lastPositionGPS =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val locationListener = LocationListener {}

            if (lastPositionNetwork == null) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0f,
                    locationListener
                )
            } else {
                try {
                    val point = lastPositionNetwork

                    val client = HttpClient(CIO) {
                        expectSuccess = false
                        install(JsonFeature) {
                            serializer = GsonSerializer()
                        }
                    }

                    val cityResponse: HttpResponse =
                        client.get("http://api.openweathermap.org/geo/1.0/reverse") {
                            contentType(ContentType.Application.Json)
                            parameter("lat", point.latitude)
                            parameter("lon", point.longitude)
                            parameter("limit", "1")
                            parameter("appid", "90139320c9572a3b78debc4d2e0ddc3f")
                        }

                    val data: HttpResponse =
                        client.get("https://api.openweathermap.org/data/2.5/weather") {
                            contentType(ContentType.Application.Json)
                            parameter("lat", point.latitude)
                            parameter("lon", point.longitude)
                            parameter("appid", "90139320c9572a3b78debc4d2e0ddc3f")
                            parameter("units", "metric")
                        }
                    val weatherAndCity =
                        WeatherAndCity(data.receive<CurrentWeather>(), cityResponse.receive<City>())
                    if (data.status.isSuccess()) {
                        val weather = data.receive<CurrentWeather>()
                        _tapRequestState.value = Resource.Success(weatherAndCity)
                        println(point.latitude.toString() + point.longitude)
                    } else {
                        _tapRequestState.value = Resource.Error("No internet")
                    }
                } catch (e: Exception){
                    _tapRequestState.value = Resource.Error("No internet")
                }
            }
        }
    }
}