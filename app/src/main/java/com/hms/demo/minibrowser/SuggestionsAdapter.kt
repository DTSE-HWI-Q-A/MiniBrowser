package com.hms.demo.minibrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hms.demo.minibrowser.databinding.SuggestionBinding
import com.huawei.hms.searchkit.bean.SuggestObject

class SuggestionsAdapter:
    RecyclerView.Adapter<SuggestionsAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val binding:SuggestionBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:SuggestObject){
            binding.item=item
        }
    }

    var suggestions:ArrayList<SuggestObject> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=SuggestionBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(suggestions[position])
    }

    override fun getItemCount(): Int {
        return suggestions.size
    }
}