package com.camerax.databindingdemo

import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.camerax.databindingdemo.databinding.ItemBinding
import kotlinx.android.synthetic.main.activity_main.view.*

class MyRecyclerAdapter(context: Context) : RecyclerView.Adapter<NewViewHolder>() {

    var context:Context? = context

    var itemClick: ItemClick? = null

    var listData: MutableList<Book>? = null

    constructor(context: Context,itemClick: ItemClick) : this(context) {
        this.context = context
        this.itemClick = itemClick
    }

    fun setData(listData: MutableList<Book>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val binding: ItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item, parent, false)
        binding.itemClick = itemClick
        return NewViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return if (null == listData) 0 else listData!!.size
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val binding: ItemBinding = holder.getBinding()
        binding.position = ObservableInt( position)
        binding.book = listData!![position]
    }
}