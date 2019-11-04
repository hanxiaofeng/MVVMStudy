package com.camerax.databindingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.camerax.databindingdemo.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        Toast.makeText(this@RecyclerViewActivity,"改变数据了",Toast.LENGTH_SHORT).show()
        for (it in 0 until listData.size){
            listData[it].name = ObservableField(listData[it].name!!.get()+" :你好")
        }
        adapter.notifyDataSetChanged()
    }

    private lateinit var dataBinding: ActivityRecyclerViewBinding
    private lateinit var adapter: MyRecyclerAdapter

    var listData: MutableList<Book> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view)
        dataBinding.listener = this

        adapter = MyRecyclerAdapter(this, object : ItemClick {
            override fun itemClick(view: View, book: Book, position: Int) {
                Toast.makeText(this@RecyclerViewActivity,""+book.name!!.get()+"     $position",Toast.LENGTH_SHORT).show()
            }
        })
        dataBinding.recyclerView.adapter = adapter

        for (it in 1..30){
            val book = Book()
            book.name = ObservableField("书本$it")
            book.author = ObservableField("作者$it")
            listData.add(book)
        }

        adapter.setData(listData)

    }

}
