package com.example.clothesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesapp.R
import com.example.clothesapp.data.DataObject
import com.example.clothesapp.ktClasses.Cloth

class RecyclerViewAdapterSet(private val currentClothes:MutableList<MutableList<Cloth>>) : RecyclerView.Adapter<RecyclerViewAdapterSet.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBing(position: Int, clothes: MutableList<MutableList<Cloth>>) {
            val rv = itemView.findViewById<RecyclerView>(R.id.rvSetItem)
            rv.adapter = RecyclerViewAdapterSetItem(clothes[position])
            val layoutManager = LinearLayoutManager(itemView.context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            rv.layoutManager = layoutManager
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
            LayoutInflater.from(parent.context).inflate(R.layout.set_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBing(position, currentClothes)
    }

    override fun getItemCount(): Int = currentClothes.size
}