package com.example.clothesapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.R
import com.example.clothesapp.adapter.RecyclerViewAdapterRemove
import com.example.clothesapp.data.DataObject
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RemoveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_remove, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRemove)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = RecyclerViewAdapterRemove()

        view.findViewById<FloatingActionButton>(R.id.fabCancel).setOnClickListener {
            DataObject.currentFragment = R.id.imagesFragment
            view.findNavController().navigate(R.id.action_removeFragment_to_imagesFragment)
        }

        view.findViewById<FloatingActionButton>(R.id.fabDeleteChosen).setOnClickListener {
            if (DataObject.chosenItems.isNotEmpty()) {
                var ad = AlertDialog.Builder(requireContext())

                ad.setTitle("Вы уверены, что хотите удалить ${DataObject.chosenItems.size} элементов?")

                ad.setPositiveButton("Да") { p0, p1 ->
                    for (i in 0 until DataObject.chosenItems.size) {
                        if (DataObject.chosenItems[i] >= i)
                            DataObject.currentListOfClothes.removeAt(DataObject.chosenItems[i] - i)
                        else
                            DataObject.currentListOfClothes.removeAt(DataObject.chosenItems[i])
                    }
                    DataObject.chosenItems.clear()
//                    data.chosenItems.forEach {
//                        data.currentListOfClothes.removeAt(it)
//                    }
                    DataObject.currentFragment = R.id.imagesFragment
                    view.findNavController().navigate(R.id.action_removeFragment_to_imagesFragment)
                }
                ad.setNegativeButton("Нет") { p0, p1 -> }
                ad.create()
                ad.show()
            } else {
                DataObject.currentFragment = R.id.imagesFragment
                view.findNavController().navigate(R.id.action_removeFragment_to_imagesFragment)
            }
        }

        return view
    }
}