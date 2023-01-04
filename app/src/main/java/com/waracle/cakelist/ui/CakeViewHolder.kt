package com.waracle.cakelist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.databinding.CakeItemBinding

class CakeViewHolder(private val cakeItemBinding: CakeItemBinding, private val listener : Listener) : RecyclerView.ViewHolder(cakeItemBinding.root){

    interface Listener{
        fun onClick(position : Int)
    }

    init {
        //Listener is attached to view holder itemview
        cakeItemBinding.root.setOnClickListener() {
            listener.onClick(adapterPosition)
        }
    }


    fun bind(item : CakeItem)
    {
        cakeItemBinding.tvCakeName.text = item.title
        //TODO : Could add a loading icon when fetching images and any other image if unable to fetch
        Picasso.get().load(item.image).into(cakeItemBinding.ivCakeImage)
    }
}
