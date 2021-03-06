package com.example.clothesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.data.data


class ClothesRecyclerViewAdapter : RecyclerView.Adapter<ClothesRecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBing(position: Int) {
            var currentClothes = data.currentListOfClothes[position]
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text =
                "Тип: ${currentClothes.clothType.typeName}"
            itemView.findViewById<TextView>(R.id.textViewNameItem).text = currentClothes.clothName
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text = "Тип: ${currentClothes.clothType.typeName}"
            itemView.findViewById<TextView>(R.id.textViewColorItem).text = "Цвет: ${currentClothes.color.colorName}"
            itemView.findViewById<TextView>(R.id.textViewWarmthItem).text = "Теплота: ${currentClothes.name.clothWarmth.toString()}/10"

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
        holder.onBing(position)
    }

    override fun getItemCount(): Int = data.currentListOfClothes.size
}