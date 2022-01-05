package com.example.clothesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.R
import com.example.clothesapp.data.data


class RecyclerViewAdapterRemove : RecyclerView.Adapter<RecyclerViewAdapterRemove.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBing(position: Int) {
            var currentClothes = data.currentListOfClothes[position]
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text =
                "Тип: ${currentClothes.type.typeName}"
            itemView.findViewById<TextView>(R.id.textViewNameItem).text = currentClothes.cloth.name
            itemView.findViewById<TextView>(R.id.textViewTypeItem).text = "Тип: ${currentClothes.type.typeName}"
            itemView.findViewById<TextView>(R.id.textViewColorItem).text = "Цвет: ${currentClothes.clothColor.colorToName.second}"

            itemView.findViewById<ImageView>(R.id.imageView2).setImageBitmap(currentClothes.photo)
            itemView.setOnClickListener {
                if (position in data.chosenItems){
                    itemView.alpha=1F
                    data.chosenItems.remove(position)
                }
                else{
                    itemView.alpha=0.5F
                    data.chosenItems.add(position)
                }
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