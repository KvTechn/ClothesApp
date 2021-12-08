package com.example.clothesapp

import retrofit2.Call
import retrofit2.http.*

const val API_KEY = "ec930b8b959d1e969a3dc455d2f87b3f"

//http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=ec930b8b959d1e969a3dc455d2f87b3f

interface WeatherApi {
    @GET("weather")
    fun weatherUnit(
        @Query("q")  city : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("appid") api : String = API_KEY) : Call<WeathermanResponse>

    @GET("weather")
    fun weatherUnitByCoord(
        @Query("lat")  lat : String,
        @Query("lon")  lon : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("appid") api : String = API_KEY) : Call<WeathermanResponse>

    @POST("/v1.0/removebg")
    @Headers("X-Api-Key:")
    fun getWithout(
        @Body f : String,
        @Body image : ByteArray
    ):Call<String>

    @GET("forecast")
    fun forecastWeather(
        @Query("q") city: String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("cnt") quantity : Int = 30,
        @Query("appid") api : String = API_KEY): Call<ForecastWeather>

    @GET("forecast")
    fun forecastWeatherByCoord(
        @Query("lat")  lan : String,
        @Query("lon")  lon : String,
        @Query("lang") language : String = "ru",
        @Query("units") units : String = "metric",
        @Query("cnt") quantity : Int = 30,
        @Query("appid") api : String = API_KEY): Call<ForecastWeather>
}