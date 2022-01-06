package com.example.clothesapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.clothesapp.ktClasses.Clothes
import com.example.clothesapp.R
import com.example.clothesapp.data.data
import com.example.clothesapp.data.data.allColors
import com.example.clothesapp.data.data.clothes
import com.example.clothesapp.ktClasses.Cloth
import com.example.clothesapp.ktClasses.ClothesColor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditClothesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_clothes, container, false)

        val position = arguments?.getInt("position")

        if (position != null) {
            view.findViewById<TextView>(R.id.textViewChangeName).text =
                data.currentListOfClothes[position].cloth.clothName
            view.findViewById<TextView>(R.id.textViewChangeType).text =
                "${data.currentListOfClothes[position].type.typeName}"
            view.findViewById<TextView>(R.id.textViewChangeColor).text =
                "${data.currentListOfClothes[position].clothColor.colorToName.second}"
            view.findViewById<TextView>(R.id.textViewChangeWarmth).text =
                "Теплота: 2/10"
            view.findViewById<ImageView>(R.id.imageViewChange)
                .setImageBitmap(data.currentListOfClothes[position].photo)
        }

        view.findViewById<FloatingActionButton>(R.id.fabEdit).setOnClickListener {
            var alertDialogChooseEdit = AlertDialog.Builder(view.context)
            alertDialogChooseEdit.setTitle("Что вы хотите изменить?")
            var typePos = 0
            alertDialogChooseEdit.setSingleChoiceItems(data.toChoose, typePos) { p0, p1 ->
                typePos = p1
            }
            alertDialogChooseEdit.setPositiveButton(
                "Ок"
            ) { p0, p1 ->
                when (typePos) {
                    0 -> {
                        var ad = AlertDialog.Builder(view.context)
                        ad.setTitle("Выберите предмет одежды")
                        var pos = 0
                        ad.setSingleChoiceItems(clothes, pos) { p0, p1 -> pos = p1 }
                        ad.setPositiveButton(
                            "Ок"
                        ) { p0, p1 ->
                            val currentCloth = data.currentListOfClothes[position!!]
                            val newCloth = Cloth(
                                data.nameToClothName[clothes[pos]]!!,
                                currentCloth.clothColor,
                                data.CNtoCT[data.nameToClothName[clothes[pos]]!!]!!,
                                0,
                                currentCloth.photo
                            )
                            data.currentListOfClothes[position] = newCloth
                            view.findViewById<TextView>(R.id.textViewChangeName).text =
                                newCloth.cloth.clothName
                            view.findViewById<TextView>(R.id.textViewChangeType).text =
                                "${newCloth.type.typeName}"
                            view.findViewById<TextView>(R.id.textViewChangeColor).text =
                                "${newCloth.clothColor.colorToName.second}"
                            view.findViewById<TextView>(R.id.textViewChangeWarmth).text =
                                "Теплота: 2/10"
                        }
                        ad.setNegativeButton(
                            "Отмена"
                        ) { p0, p1 -> }
                        ad.create()
                        ad.show()
                    }
                    1 -> {
                        var ad = AlertDialog.Builder(view.context)
                        ad.setTitle("Выберите цвет одежды")
                        var pos = 0
                        ad.setSingleChoiceItems(allColors, pos) { p0, p1 -> pos = p1 }
                        ad.setPositiveButton(
                            "Ок"
                        ) { p0, p1 ->
                            val currentCloth = data.currentListOfClothes[position!!]
                            val newCloth = Cloth(
                                data.nameToClothName[currentCloth.cloth.clothName]!!,
                                data.colorNameToColor[allColors[pos]]!!,
                                data.CNtoCT[data.nameToClothName[currentCloth.cloth.clothName]]!!,
                                0,
                                currentCloth.photo,
                            )
                            data.currentListOfClothes[position] = newCloth
                            view.findViewById<TextView>(R.id.textViewChangeColor).text =
                                "Цвет: ${newCloth.clothColor.colorToName.second}"
                        }
                        ad.setNegativeButton(
                            "Отмена"
                        ) { p0, p1 -> }
                        ad.create()
                        ad.show()
                    }
                }
            }
            alertDialogChooseEdit.setNegativeButton(
                "Отмена"
            ) { p0, p1 -> }
            alertDialogChooseEdit.create()
            alertDialogChooseEdit.show()
        }
        view.findViewById<FloatingActionButton>(R.id.fabChangeOk).setOnClickListener {
            data.currentFragment = R.id.imagesFragment
            view.findNavController().navigate(R.id.action_editClothesFragment_to_imagesFragment)
        }
        return view
    }
}