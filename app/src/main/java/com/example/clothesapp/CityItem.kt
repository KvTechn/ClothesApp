package com.example.clothesapp

data class CityItem(
    val country: String = "",
    val lat: Double = 0.0,
    val local_names: LocalNames = LocalNames(),
    val lon: Double = 0.0,
    val name: String = ""
)