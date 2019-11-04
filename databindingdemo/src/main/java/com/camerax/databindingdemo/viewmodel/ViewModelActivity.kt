package com.camerax.databindingdemo.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.camerax.databindingdemo.R
import com.camerax.databindingdemo.databinding.ActivityMainBinding
import com.camerax.databindingdemo.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityViewModelBinding

    private lateinit var myViewModel:MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_model)
        dataBinding.viewmodelActivity = this

        myViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)

        Log.e("ViewModel","onCreate = > "+myViewModel.hashCode())

        val liveData:MutableLiveData<String> = myViewModel.liveDataEvent
        liveData.observe(this, Observer {
            Log.e("liveData"," = 数据发生改变 = ")
            dataBinding.name = it
        })

        //liveData配合ViewModel使用，不用observe监听，自动绑定
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = myViewModel

        NetWorkLiveData.getInstance(this).observe(this, Observer {

            if(null == it){
                Toast.makeText(this,"网络不可用",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"网络可用",Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun clickBtn(view:View){

        Log.e("ViewModel","传递的地址：address = "+myViewModel.address)

        myViewModel.liveDataEvent.value = "刘德华"

        myViewModel.data.value = "LiveData配合ViewModel使用"
    }

    override fun onStart() {
        super.onStart()

        val myViewModel:MyViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)
        Log.e("ViewModel","onStart = > "+myViewModel.hashCode())
    }

    override fun onResume() {
        super.onResume()

        val myViewModel:MyViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)
        Log.e("ViewModel","onResume = > "+myViewModel.hashCode())
    }

    override fun onPause() {
        super.onPause()

        val myViewModel:MyViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)
        Log.e("ViewModel","onPause = > "+myViewModel.hashCode())
    }

    override fun onStop() {
        super.onStop()

        val myViewModel:MyViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)
        Log.e("ViewModel","onStop = > "+myViewModel.hashCode())
    }

    override fun onDestroy() {
        super.onDestroy()

        val myViewModel:MyViewModel = ViewModelProviders.of(this,MyViewModel.WMFactory("江苏")).get(MyViewModel::class.java)
        Log.e("ViewModel","onDestroy = > "+myViewModel.hashCode())
    }
}
