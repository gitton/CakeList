package com.waracle.cakelist.ui

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.databinding.CakeItemBinding

class CakeViewHolder(private val cakeItemBinding: CakeItemBinding) : RecyclerView.ViewHolder(cakeItemBinding.root){

    fun bind(item : CakeItem)
    {
        cakeItemBinding.tvCakeName.text = item.title
        Picasso.get().load(item.image).into(cakeItemBinding.ivCakeImage)
    }
}
