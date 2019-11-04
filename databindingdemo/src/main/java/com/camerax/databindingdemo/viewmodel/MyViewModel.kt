package com.camerax.databindingdemo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModel(var address: String) : ViewModel() {

    val liveDataEvent: MutableLiveData<String> = MutableLiveData()

    val data: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()

        Log.e("ViewModel","调用了ViewModel的onCleared方法----我被杀了")
    }

    //如果需要传递参数，通过factory

    class WMFactory(private var address:String):ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyViewModel(address) as (T)
        }

    }
}