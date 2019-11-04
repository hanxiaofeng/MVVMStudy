package com.camerax.databindingdemo

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.camerax.databindingdemo.databinding.ItemBinding

class NewViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun getBinding(): ItemBinding {
        return binding
    }
}