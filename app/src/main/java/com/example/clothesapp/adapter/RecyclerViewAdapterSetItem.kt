package com.example.clothesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.R
import com.example.clothesapp.data.DataObject
import com.example.clothesapp.ktClasses.Cloth

class RecyclerViewAdapterSetItem (private val currentClothes:MutableList<Cloth>) : RecyclerView.Adapter<RecyclerViewAdapterSetItem.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBing(position: Int, clothes: MutableList<Cloth>) {
            val iv = itemView.findViewById<ImageView>(R.id.imageViewSetItem)
            iv.setImageBitmap(clothes[position].photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.set_rv_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBing(position, currentClothes)
    }

    override fun getItemCount(): Int = currentClothes.size
}