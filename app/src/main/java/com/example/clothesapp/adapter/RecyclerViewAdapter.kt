package com.example.clothesapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.R
import com.example.clothesapp.data.data
import com.example.clothesapp.ktClasses.Cloth


class ClothesRecyclerViewAdapter(private val currentClothes:MutableList<Cloth>) : RecyclerView.Adapter<ClothesRecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBing(position: Int, clothes: MutableList<Cloth>) {
            val currentClothes = clothes[position]
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text =
                "Тип: ${currentClothes.type.typeName}"
            itemView.findViewById<TextView>(R.id.textViewNameItem).text = currentClothes.cloth.clothName
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text = "Тип: ${currentClothes.type.typeName}"
            itemView.findViewById<TextView>(R.id.textViewColorItem).text = "Цвет: ${currentClothes.clothColor.colorToName.second}"

            itemView.findViewById<ImageView>(R.id.imageView2).setImageBitmap(currentClothes.photo)
            itemView.setOnClickListener {
                var bundle = Bundle()
                bundle.putInt("position", position)
                data.currentFragment = R.id.editClothesFragment
                itemView.findNavController().navigate(R.id.action_imagesFragment_to_editClothesFragment, bundle)
            }
//            itemView.findViewById<FloatingActionButton>(R.id.floatingActionButton3).setOnClickListener {
//                val intent = Intent()
//                intent.type = "image/*"
//                intent.action = Intent.ACTION_GET_CONTENT
//                val myFragment: Fragment = ChangeImageFragment()
//                itemView.findNavController().navigate(R.id.action_imagesFragment_to_changeImageFragment)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cloth_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBing(position, currentClothes)
    }

    override fun getItemCount(): Int = data.currentListOfClothes.size
}