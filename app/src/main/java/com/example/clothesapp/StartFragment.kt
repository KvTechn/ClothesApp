package com.example.clothesapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RemoteViews
import android.widget.TextView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.clothesapp.adapter.RecyclerViewAdapterSet
import com.example.clothesapp.data.DataObject
import com.example.clothesapp.ktClasses.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest


class StartFragment : Fragment() {

    private val PERMISSION_CODE = 200
    private val locationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.start_fragment, container, false)

        val cloth1 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM1,
            CT.LIGHT_TOP,
            1,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )
        val cloth2 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM2,
            CT.LIGHT_DOWN,
            2,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )
        val cloth3 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM3,
            CT.WARM_TOP,
            3,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )
        val cloth4 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM4,
            CT.LIGHT_DOWN,
            4,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )
        val cloth5 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM5,
            CT.LIGHT_TOP,
            5,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )

        val cloth6 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM5,
            CT.LIGHT_DOWN,
            6,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )

        val cloth7 = Cloth(
            CN.PULLOVER,
            ClothesColor.CUSTOM5,
            CT.LIGHT_TOP,
            7,
            BitmapFactory.decodeResource(resources, R.drawable.hanger)
        )

        val mutClothes = ClothesMaster().choose(
            DataObject.currentListOfClothes,
            20
        )//ClothesMaster().choose(mutableListOf(cloth1, cloth2, cloth3, cloth4, cloth5, cloth6, cloth7), 20)
        Log.d("AAA", mutClothes.toString())
        DataObject.currentListOfClothes.forEach {
            Log.d("AAA", it.cloth.clothName)
            Log.d("AAA", it.clothColor.colorToName.second)
            Log.d("AAA", it.type.typeName)
            Log.d("AAA", it.id.toString())
        }
        /*mutableListOf<MutableList<Cloth>>(
            mutableListOf(
                Cloth(
                    CN.PULLOVER,
                    ClothesColor.BLACK,
                    CT.WARM_TOP,
                    0,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                ),
                Cloth(
                    CN.JEANS,
                    ClothesColor.BLUE,
                    CT.WARM_DOWN,
                    1,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                )
            ),
            mutableListOf(
                Cloth(
                    CN.PULLOVER,
                    ClothesColor.BLACK,
                    CT.WARM_TOP,
                    0,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                ),
                Cloth(
                    CN.PULLOVER,
                    ClothesColor.BLACK,
                    CT.WARM_TOP,
                    0,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                ),
                Cloth(
                    CN.PULLOVER,
                    ClothesColor.BLACK,
                    CT.WARM_TOP,
                    0,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                ),
                Cloth(
                    CN.PULLOVER,
                    ClothesColor.BLACK,
                    CT.WARM_TOP,
                    0,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                ),
                Cloth(
                    CN.JEANS,
                    ClothesColor.BLUE,
                    CT.WARM_DOWN,
                    1,
                    BitmapFactory.decodeResource(resources, R.drawable.hanger)
                )
            ),
        )*/


        val rv = view.findViewById<RecyclerView>(R.id.setRv)
        rv.adapter = RecyclerViewAdapterSet(mutClothes)
        rv.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.toMapButton).setOnClickListener {
            checkLocationPermissions()
        }
        val toMapButton = view.findViewById<Button>(R.id.toMapButton)
        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.tapRequestState.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        view.findViewById<TextView>(R.id.textViewLatLan).text = "Нет интернета"
                        toMapButton.visibility = View.VISIBLE
                        toMapButton.text = "Попробовать ещё раз"
                    }
                    is Resource.Loading -> {
                        toMapButton.visibility = View.GONE
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        toMapButton.visibility = View.VISIBLE
                        toMapButton.text = "Обновить локацию"
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        val url =
                            "https://openweathermap.org/img/wn/${it.data!!.currentWeather.weather[0].icon}@4x.png"
                        println(it.data!!.currentWeather.weather[0].icon)
                        println(url)
                        val loader = ImageLoader(requireContext())
                        val request = ImageRequest.Builder(requireContext())
                            .data(url)
                            .build()
                        val res = (loader.execute(request) as SuccessResult).drawable
                        DataObject.currentWeatherImage = res
                        DataObject.currentCity = it.data!!.city[0].name
                        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        (activity as MainActivity).supportActionBar?.setHomeAsUpIndicator(res)
                        if (it.data!!.city[0].local_names.ru != "") {
                            view.findViewById<TextView>(R.id.textViewLatLan).text =
                                "Ваш город: ${it.data!!.city[0].local_names.ru} ${it.data!!.currentWeather.main.temp}°C"
                        } else {
                            view.findViewById<TextView>(R.id.textViewLatLan).text =
                                "Ваш город: ${it.data!!.city[0].name} ${it.data!!.currentWeather.main.temp}°C"
                        }

                        val rv = view.findViewById<RecyclerView>(R.id.setRv)
//                        rv.adapter = RecyclerViewAdapterRemove()
//                        rv.layoutManager = GridLayoutManager(requireContext(), 2)
//                        viewModel.clearSearch()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            checkLocationPermissions()
        } else {
            Snackbar.make(requireView(), getString(R.string.no_geo_position), Snackbar.LENGTH_LONG)
                .show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkLocationPermissions() {
        if (
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    PERMISSION_CODE
                )
            } else {
                viewModel.startSearch()
            } else {
            Snackbar.make(requireView(), getString(R.string.gps_off), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}