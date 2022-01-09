package com.example.clothesapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.clothesapp.MainActivity
import com.example.clothesapp.R
import com.example.clothesapp.data.DataObject
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddImageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_image, container, false)

        val fab = view.findViewById<FloatingActionButton>(R.id.fabOk)

        fab.setOnClickListener {
            DataObject.currentFragment = R.id.imagesFragment
            view.findNavController().navigate(R.id.action_changeImageFragment_to_imagesFragment)
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    2000
                )
            } else {
                (activity as MainActivity?)?.startGallery()
            }
        }

//        var clothes = getClothes()
//
//        view.findViewById<TextView>(R.id.textViewType).text = "Тип: ${clothes.clothType.name}"
//        view.findViewById<TextView>(R.id.textViewName).text = "Тип: ${clothes.clothName}"

        return view
    }

//    fun getClothes(): Clothes {
//        return Clothes(TShirt())
//    }
}