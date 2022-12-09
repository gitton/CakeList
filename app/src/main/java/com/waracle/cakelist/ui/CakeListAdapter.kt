package com.waracle.cakelist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.databinding.CakeItemBinding

class CakeListAdapter : RecyclerView.Adapter<CakeViewHolder>() {

    private var cakeItems : List<CakeItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val binding = CakeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CakeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        with(holder){
            bind(cakeItems[position])
        }
    }

    override fun getItemCount(): Int {
        return cakeItems.size
    }

    //TODO: Can use diff util and update the adapter for performance improvement
    fun setItems(list : List<CakeItem>)
    {
        this.cakeItems = list
        this.notifyDataSetChanged()
    }

}
