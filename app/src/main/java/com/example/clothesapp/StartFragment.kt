package com.example.clothesapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import com.example.clothesapp.adapter.RecyclerViewAdapterSet
import com.example.clothesapp.data.DataObject
import com.example.clothesapp.ktClasses.CN
import com.example.clothesapp.ktClasses.CT
import com.example.clothesapp.ktClasses.Cloth
import com.example.clothesapp.ktClasses.ClothesColor
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

        val mutClothes = mutableListOf<MutableList<Cloth>>(
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
        )


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
                    }
                    is Resource.Loading -> {
                        toMapButton.visibility = View.GONE
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        toMapButton.visibility = View.VISIBLE
                        toMapButton.text = "Обновить локацию"
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE

                        view.findViewById<TextView>(R.id.textViewLatLan).text =
                            "Ваши координаты: ${it.data!!.latitude}, ${it.data.longitude}"

                        val rv = view.findViewById<RecyclerView>(R.id.setRv)
//                        rv.adapter = RecyclerViewAdapterRemove()
//                        rv.layoutManager = GridLayoutManager(requireContext(), 2)
                        viewModel.clearSearch()
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