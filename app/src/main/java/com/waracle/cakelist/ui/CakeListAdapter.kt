package com.waracle.cakelist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.databinding.CakeItemBinding

class CakeListAdapter(private val listener: Listener) : RecyclerView.Adapter<CakeViewHolder>(), CakeViewHolder.Listener {

    interface Listener{
        fun onClickCake(item : CakeItem)
    }

    private var cakeItems : List<CakeItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val binding = CakeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CakeViewHolder(binding,this)
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

    override fun onClick(position: Int) {
        listener.onClickCake(cakeItems[position])
    }

}
