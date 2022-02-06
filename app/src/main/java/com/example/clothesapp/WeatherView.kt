package com.example.clothesapp

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.google.android.material.card.MaterialCardView

class WeatherView(context: Context?, attributeSet: AttributeSet) :
    MaterialCardView(context, attributeSet) {
    init {
        inflate(context, R.layout.weather_item, this)
    }

    fun setCity(cityName: String) {
        rootView.findViewById<TextView>(R.id.textViewCityName).text = "Город: ${cityName}"
    }

    fun setTemp(temp: String) {
        rootView.findViewById<TextView>(R.id.textViewCityName).text = "Температура: ${temp}"
    }

    fun loadImage(url: String) {
        rootView.findViewById<ImageView>(R.id.imageViewWeather).load(url)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}