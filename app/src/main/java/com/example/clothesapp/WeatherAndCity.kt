package com.example.clothesapp

data class WeatherAndCity(
    val currentWeather: CurrentWeather = CurrentWeather(),
    val city: City = City()
)